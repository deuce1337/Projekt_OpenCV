package com.example.kuba.projekt_opencv;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Arrays;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImgProcess
{
    private int A, B, C, D, E, F, G, H, I;
    private String Message;

    void setMessage(String message)
    {
        message = Message;
    }

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
                    double pixel[] = image.get(i, 0); //(X, Y) = W/H
                    Log.d("Pixel value", Arrays.toString(pixel));

                    if (pixel[i] % 2 == 0)
                    {
                        
                    }
                }
            }
        }
        else
        {
            Log.d("Debug", "File not found!");
        }
    }

    void encodeMsg()
    {
        for (int i = 0; i < Message.length(); i++)
        {
            char ch = Message.charAt(i);

            switch(ch)
            {
                case 'a': A = 0; B = 0; C = 0; D = 0; E = 0; F = 0; G = 0; H = 0; I = 1;
                    break;
                case 'b': A = 0; B = 0; C = 0; D = 0; E = 0; F = 0; G = 0; H = 1; I = 0;
                    break;
                case 'c': A = 0; B = 0; C = 0; D = 0; E = 0; F = 0; G = 0; H = 1; I = 1;
                    break;
            }
        }
    }
}