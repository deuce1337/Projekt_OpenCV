package com.example.kuba.projekt_opencv;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

public class ImgProcess
{
    void loadImg(String fName)
    {
        File fPath = Environment.getExternalStorageDirectory();
        File file = new File(fPath, fName);
        Mat mat = Imgcodecs.imread(file.getAbsolutePath());
        if(file.exists())
        {
            Log.i("height", mat.height() + "");
            Log.i("width", mat.width() + "");
        }
        else
        {
            Log.d("Error", "File not found!");
        }
    }
}