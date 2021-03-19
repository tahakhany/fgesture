package com.taha.fgesture;

import android.accessibilityservice.FingerprintGestureController;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MainActivity extends AppCompatActivity {

    private final int FINGERPRINT_GESTURE_SWIPE_RIGHT = FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_RIGHT;
    private final int FINGERPRINT_GESTURE_SWIPE_LEFT = FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_LEFT;
    private final int FINGERPRINT_GESTURE_SWIPE_UP = FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_UP;
    private final int FINGERPRINT_GESTURE_SWIPE_DOWN = FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_DOWN;

    private static final String TAG = "fgestureTAG";
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isCreated;
            boolean isRegistered;
            boolean isFingerprintGestureControllerConnected;
            boolean isGestureDetectionAvailable;
            int detectedGesture;
            Log.d(TAG, "onReceive: Received");
            isCreated = intent.getBooleanExtra("isCreated", false);
            isRegistered = intent.getBooleanExtra("isRegistered", false);
            isFingerprintGestureControllerConnected = intent.getBooleanExtra("isFingerprintGestureControllerConnected", false);
            isGestureDetectionAvailable = intent.getBooleanExtra("isGestureDetectionAvailable", false);
            detectedGesture = intent.getIntExtra("detectedGesture", 0);
            TextView isCreatedTextView = findViewById(R.id.serviceTextView);
            TextView isRegisteredTextView = findViewById(R.id.ServiceRegisteredTextView);
            TextView isFingerprintGestureControllerConnectedTextView = findViewById(R.id.FingerprintGestureControllerTextView);
            TextView isGestureDetectionAvailableTextView = findViewById(R.id.GestureDetectionAvailableTextView);
            SetColor(isCreatedTextView, isCreated);
            SetColor(isRegisteredTextView, isRegistered);
            SetColor(isFingerprintGestureControllerConnectedTextView, isFingerprintGestureControllerConnected);
            SetColor(isGestureDetectionAvailableTextView, isGestureDetectionAvailable);
            SetGestureDirection(detectedGesture);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "start");
        TextView startTextView = findViewById(R.id.startTextView);
        startTextView.setTextColor(getColor(R.color.green));


        Intent intent = new Intent(this, MyAccessibilityService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("gestureService"));
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        // Unregister since the activity is not visible
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    public void SetColor(TextView textView, boolean stat) {
        if (stat == true) {
            textView.setTextColor(getColor(R.color.green));
        } else {
            textView.setTextColor(getColor(R.color.red));
        }
    }

    public void SetGestureDirection(int gestureDirection) {
        TextView gestureDirectionTextView = findViewById(R.id.gestureDirectionTextView);
        switch (gestureDirection) {
            case FINGERPRINT_GESTURE_SWIPE_RIGHT:
                gestureDirectionTextView.setText("FINGERPRINT_GESTURE_SWIPE_RIGHT");
                break;
            case FINGERPRINT_GESTURE_SWIPE_LEFT:
                gestureDirectionTextView.setText("FINGERPRINT_GESTURE_SWIPE_LEFT");
                break;
            case FINGERPRINT_GESTURE_SWIPE_UP:
                gestureDirectionTextView.setText("FINGERPRINT_GESTURE_SWIPE_UP");
                break;
            case FINGERPRINT_GESTURE_SWIPE_DOWN:
                gestureDirectionTextView.setText("FINGERPRINT_GESTURE_SWIPE_DOWN");
                break;
            default:
                gestureDirectionTextView.setText("No gesture detected.");
        }
    }
}

