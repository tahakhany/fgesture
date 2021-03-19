package com.taha.fgesture;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.FingerprintGestureController;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyAccessibilityService extends AccessibilityService {

    AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();

    private boolean isCreated = false;
    private boolean isRegistered = false;
    private boolean isFingerprintGestureControllerConnected = false;
    private boolean isGestureDetectionAvailable = false;
    private int detectedGesture;
    private String TAG = "fgestureTAG";
    private FingerprintGestureController mGestureController;
    private FingerprintGestureController
            .FingerprintGestureCallback mFingerprintGestureCallback;
    private boolean mIsGestureDetectionAvailable;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: created");
        isCreated = true;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {

        serviceInfo.eventTypes = AccessibilityServiceInfo.CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES;
        serviceInfo.flags = AccessibilityServiceInfo.DEFAULT;
        serviceInfo.flags = AccessibilityServiceInfo.FLAG_REQUEST_FINGERPRINT_GESTURES;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        //serviceInfo.notificationTimeout=100;
        this.setServiceInfo(serviceInfo);
        //Log.d(TAG, "onCreate: connected");
        mGestureController = getFingerprintGestureController();
        if (mGestureController != null) {
            Log.d(TAG, "onServiceConnected: " + mGestureController);
            isFingerprintGestureControllerConnected = true;
        }

        mIsGestureDetectionAvailable =
                mGestureController.isGestureDetectionAvailable();
        Log.d(TAG, "onServiceConnected: " + mGestureController.isGestureDetectionAvailable());
        isGestureDetectionAvailable = mGestureController.isGestureDetectionAvailable();


        mFingerprintGestureCallback =
                new FingerprintGestureController.FingerprintGestureCallback() {
                    @Override
                    public void onGestureDetected(int gesture) {
                        detectedGesture = gesture;
                        switch (gesture) {
                            case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_DOWN:
                                Log.d(TAG, "onGestureDetected: " + gesture);
                                break;
                            case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_LEFT:
                                Log.d(TAG, "onGestureDetected: " + gesture);
                                break;
                            case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_RIGHT:
                                Log.d(TAG, "onGestureDetected: " + gesture);
                                break;
                            case FingerprintGestureController.FINGERPRINT_GESTURE_SWIPE_UP:
                                Log.d(TAG, "onGestureDetected: " + gesture);
                                break;
                            default:
                                Log.e(TAG,
                                        "Error: Unknown gesture type detected!");
                                break;

                        }
                    }

                    @Override
                    public void onGestureDetectionAvailabilityChanged(boolean available) {
                        //mIsGestureDetectionAvailable = available;
                        Log.d(TAG, "onGestureDetectionAvailabilityChanged: " + available);
                    }
                };

        try {
            mGestureController.registerFingerprintGestureCallback(
                    mFingerprintGestureCallback, null);
            Log.d(TAG, "onServiceConnected: Registered");
            isRegistered = true;
        } catch (Exception e) {
            Log.d(TAG, "onServiceConnected: " + e.toString());
        }
        sendMessage();
        if (mFingerprintGestureCallback != null) {
            Log.d(TAG, "onServiceConnected: registered");
            mGestureController.registerFingerprintGestureCallback(
                    mFingerprintGestureCallback, null);
        }
        if (mFingerprintGestureCallback != null
                || !mIsGestureDetectionAvailable) {
            return;
        }

    }

    private void sendMessage() {
        Log.d(TAG, "sendMessage: start");
        Intent intent = new Intent("gestureService");

        intent.putExtra("gestureDirection", detectedGesture);
        intent.putExtra("isCreated", isCreated);
        intent.putExtra("isRegistered", isRegistered);
        intent.putExtra("isFingerprintGestureControllerConnected", isFingerprintGestureControllerConnected);
        intent.putExtra("isGestureDetectionAvailable", isGestureDetectionAvailable);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Log.d(TAG, "sendMessage: end");
    }
}