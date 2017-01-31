package com.example.owner.floatingwidget;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 */
public class MainActivity extends AppCompatActivity
{
    private Button mStartBtn, mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = (Button) findViewById(R.id.startService);
        mStopBtn = (Button) findViewById(R.id.stopService);

        checkDrawOverlayPermission();

        mStartBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                startService(new Intent(getApplication(), ChatHeadService.class));
                startService(new Intent(getApplication(), MyAccessibilityService.class));
            }
        });

        //This stop button doesn't work (the bastard)
        mStopBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                stopService(new Intent(getApplication(), ChatHeadService.class));
                stopService(new Intent(getApplication(), MyAccessibilityService.class));
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });
    }

    public final static int REQUEST_CODE = 5463;

    public void checkDrawOverlayPermission()
    {
        //If API is greater than 23 (M)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            /** check if we already  have permission to draw over other apps */
            if (!Settings.canDrawOverlays(getApplicationContext()))
            {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //If API is greater than 23 (M)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        /** check if received result code
         is equal our requested code for draw permission  */
            if (requestCode == REQUEST_CODE)
            {
                if (Settings.canDrawOverlays(this))
                {
                    // continue here - permission was granted
                }
            }
    }
}
