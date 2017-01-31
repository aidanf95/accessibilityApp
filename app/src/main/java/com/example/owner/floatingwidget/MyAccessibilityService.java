package com.example.owner.floatingwidget;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;

/**
 * Created by God on 30/01/2017.
 */
public class MyAccessibilityService extends AccessibilityService
{
    private WindowManager mWindowManager;
    private ImageView mBackBtn; //home,recent;
    private GestureDetector mGestureDetector;
    WindowManager.LayoutParams params;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mBackBtn = new ImageView(this);
//        home = new ImageView(this);
        mBackBtn.setImageResource(R.mipmap.ic_launcher);
//        home.setImageResource(R.mipmap.ic_home);

        mGestureDetector = new GestureDetector(this, new SingleTapConfirmed());

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        mBackBtn.setOnTouchListener(new View.OnTouchListener()
        {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //if clicked
                //TODO: Add doubleTap, TripleTap & LongPress Gestures for other functionality.
                if (mGestureDetector.onTouchEvent(event))
                {
                    backButtonPressed();
                    return true;
                }
                else //else dragged
                {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            mWindowManager.updateViewLayout(mBackBtn, params);
                            return true;
                    }
                    return false;
                }
            }
        });

        mWindowManager.addView(mBackBtn, params);
    }

    private void backButtonPressed()
    {
        try
        {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            Log.e("BACK PRESSED", "BACK PRESSED");
        } catch (Exception e)
        {
            Log.e("BACK PRESSED", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void homeButtonPressed()
    {
        try
        {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            Log.e("HOME PRESSED", "HOME PRESSED");
        } catch (Exception e)
        {
            Log.e("HOME PRESSED", "ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void recentButtonPressed()
    {
        try
        {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)
    {
    }

    @Override
    public void onInterrupt()
    {
    }

    @Override
    protected void onServiceConnected()
    {
        super.onServiceConnected();
        Log.d("TAG", "onServiceConnected");
    }

    /**
     * Class for Gestures
     */
    private class SingleTapConfirmed extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onSingleTapUp(MotionEvent event)
        {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event)
        {
            return true;
        }
    }
}