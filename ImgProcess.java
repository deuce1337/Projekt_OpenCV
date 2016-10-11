package com.example.kuba.projekt_opencv;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.Arrays;

public class ImgProcess
{
    void loadImg(String fName)
    {
        File file = new File(Environment.getExternalStorageDirectory(), fName);
        Mat image = Imgcodecs.imread(file.getAbsolutePath());

        if(file.exists())
        {
            Log.i("Image height", image.height() + "");
            Log.i("Image width", image.width() + "");

            if(image.channels() == 3) //BGR
            {
                for (int i = 0; i < 10; i++)
                {
                    double pixel[] = image.get(i, i);
                    Log.d("Pixel value", Arrays.toString(pixel));
                }
            }
        }
        else
        {
            Log.d("Debug", "File not found!");
        }
    }
}