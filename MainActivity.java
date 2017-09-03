package com.example.kuba.projekt_opencv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity
{
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.d("Debug", "OpenCV loaded successfully");
                } break;
                default:
                {
                    Log.e("Error", "Failed to load OpenCV");
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bDecode = (Button) findViewById(R.id.bDecode);
        Button bEncode = (Button) findViewById(R.id.bEncode);

        final ImgProcess imgProcess = new ImgProcess();

        if (!OpenCVLoader.initDebug())
        {
            Log.w("Warning", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        }

        bDecode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imgProcess.loadImg("img.png");
                imgProcess.decodeMsg();
            }
        });

        bEncode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imgProcess.loadImg("img.png");
                imgProcess.setMessage("abcd");
                imgProcess.encodeMsg();
                imgProcess.saveImage();
            }
        });
    }
}