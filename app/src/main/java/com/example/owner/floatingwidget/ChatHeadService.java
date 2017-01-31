package com.example.owner.floatingwidget;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

/**
 * Created by God on 29/01/2017.
 * NOT BEING USED AT THE MOMENT (OLD CLASS)
 */
public class ChatHeadService extends Service
{
    private WindowManager mWindowManager;
    private ImageView mChatHead;
    WindowManager.LayoutParams params;

    private GestureDetector mGestureDetector;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mChatHead = new ImageView(this);
        mChatHead.setImageResource(R.mipmap.ic_launcher);

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

        mChatHead.setOnTouchListener(new View.OnTouchListener()
        {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //if clicked
                if(mGestureDetector.onTouchEvent(event))
                {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
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
                            mWindowManager.updateViewLayout(mChatHead, params);
                            return true;
                    }
                    return false;
                }
            }
        });

        mWindowManager.addView(mChatHead, params);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(mChatHead != null)
        {
            mWindowManager.removeView(mChatHead);
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

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
