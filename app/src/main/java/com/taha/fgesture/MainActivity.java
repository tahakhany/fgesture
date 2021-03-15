package com.taha.fgesture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "fgestureTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "start");
        TextView textView = findViewById(R.id.textvVew);
        /*AccessibilityService accessibilityService = new AccessibilityService() {
            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {
                Log.d(TAG, "onAccessibilityEvent: "+ event);
            }

            @Override
            public void onInterrupt() {
                Log.d(TAG, "onInterrupt: ");
            }

            private FingerprintGestureController mGestureController;
            private FingerprintGestureController
                    .FingerprintGestureCallback mFingerprintGestureCallback;
            private boolean mIsGestureDetectionAvailable;

            @Override
            public void onCreate() {
                mGestureController = getFingerprintGestureController();
                mIsGestureDetectionAvailable =
                        mGestureController.isGestureDetectionAvailable();
                Log.d(TAG, "onCreate: "+mIsGestureDetectionAvailable);
            }

            @Override
            protected void onServiceConnected() {
                if (mFingerprintGestureCallback != null
                        || !mIsGestureDetectionAvailable) {
                    return;
                }else{
                    Log.d(TAG, "onServiceConnected: "+mIsGestureDetectionAvailable);
                }
                mFingerprintGestureCallback =
                        new FingerprintGestureController.FingerprintGestureCallback() {
                            @Override
                            public void onGestureDetected(int gesture) {
                                switch (gesture) {
                                    case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_DOWN:
                                        Log.d(TAG, "onGestureDetected: "+gesture);
                                        break;
                                    case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_LEFT:
                                        Log.d(TAG, "onGestureDetected: "+gesture);
                                        break;
                                    case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_RIGHT:
                                        Log.d(TAG, "onGestureDetected: "+gesture);
                                        break;
                                    case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_UP:
                                        Log.d(TAG, "onGestureDetected: "+gesture);
                                        break;
                                    default:
                                        Log.e(TAG,
                                                "Error: Unknown gesture type detected!");
                                        break;
                                }
                            }

                            @Override
                            public void onGestureDetectionAvailabilityChanged(boolean available) {
                                mIsGestureDetectionAvailable = available;
                            }
                        };

                if (mFingerprintGestureCallback != null) {
                    mGestureController.registerFingerprintGestureCallback(
                            mFingerprintGestureCallback, null);
                }
            }

        };*/

        Intent intent = new Intent(this, MyAccessibilityService.class);

    }
}
