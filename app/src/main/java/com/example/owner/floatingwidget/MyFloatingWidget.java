package com.example.owner.floatingwidget;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Owner on 01/02/2017.
 * Work on this class maybe.
 */
public class MyFloatingWidget extends ImageView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
{
    private IActionListener mListener;

    public MyFloatingWidget(Context context, IActionListener listener)
    {
        super(context);
        mListener = listener;

        setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return false;
            }
        });
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        Log.e("SINGLE TAP UP", "SINGLE TAP UP");
        mListener.onBackPressed();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        Log.e("LONG PRESS", "LONG PRESS");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        Log.e("DOUBLE TAP", "DOUBLE TAP");
        mListener.onHomePressed();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e)
    {
        Log.e("DOUBLE TAP EVENT", "DOUBLE TAP EVENT");
        return false;
    }
}
