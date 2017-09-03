package com.example.kuba.projekt_opencv;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImgProcess
{
    private String Message;
    private Mat image;
    private Mat img;

    void setMessage(String message)
    {
        Message = message;
    }

    void loadImg(String fName)
    {
        File file = new File(Environment.getExternalStorageDirectory(), fName);
        image = Imgcodecs.imread(file.getAbsolutePath());
        img = image.clone();

        if(file.exists())
        {
            Log.i("Image loaded", "H: " + image.height() + " W: " + image.width());
        }
        else
        {
            Log.e("Error", "File not found!");
        }
    }

    void encodeMsg()
    {
        String msgLBin = Integer.toBinaryString(Message.length());
        StringBuilder SB = new StringBuilder(msgLBin);
        char msgLBinArr[] = new char[9];
        Log.v("Message", Message);
        int f = 0;

        for (int i = 0; i < SB.length(); i++)
        {
            if (SB.length() < 9)
            {
                SB.insert(0, "0");
            }
        }
        Log.v("Message length binary", String.valueOf(SB));

        for (int i = 0; i < SB.length(); i++)
        {
            msgLBinArr[i] = SB.charAt(i);
        }
        Log.v("Binary length array", Arrays.toString(msgLBinArr));

        for (int i = 0; i < 3; i++)
        {
            double pixel[] = image.get(i, 0); //(X, Y) = W/H
            double pixel2[] = pixel.clone();
            Log.d("Pixel table", Arrays.toString(pixel));

            for (int j = 0; j < 3; j++)
            {
                if (msgLBinArr[f] == '0')
                {
                    Log.d("F count 0", String.valueOf(f));
                    if (pixel[j] % 2 == 1.0)
                    {
                        if (pixel[j] == 0.0)
                        {
                            pixel2[j] = pixel[j] + 1.0;
                            Log.d("variant 1 for 0", Arrays.toString(pixel2));
                        }
                        else
                        {
                            pixel2[j] = pixel[j] - 1.0;
                            Log.d("variant 2 for 0", Arrays.toString(pixel2));
                        }
                    }
                    else
                    {
                        pixel2[j] = pixel[j];
                        Log.d("Default for 0", Arrays.toString(pixel2));
                    }
                }
                if (msgLBinArr[f] == '1')
                {
                    Log.d("F count 1", String.valueOf(f));
                    if (pixel[j] % 2 == 0.0)
                    {
                        if (pixel[j] == 0.0)
                        {
                            pixel2[j] = pixel[j] + 1.0;
                            Log.d("variant 1 for 1", Arrays.toString(pixel2));
                        }
                        else
                        {
                            pixel2[j] = pixel[j] - 1.0;
                            Log.d("variant 2 for 1", Arrays.toString(pixel2));
                        }
                    }
                    else
                    {
                        pixel2[j] = pixel[j];
                        Log.d("Default for 1", Arrays.toString(pixel2));
                    }
                }
                f++;
            }
            Log.d("Pixel2 table", Arrays.toString(pixel2) + " run " + i);
            img.put(i, 0, pixel2);
//            double pixel3[] = img.get(i, 0);
//            Log.d("Pixel3 table", Arrays.toString(pixel3) + " run " + i);
        }
    }

    void saveImage() //Zapis do pliku
    {
        File file = new File(Environment.getExternalStorageDirectory(), "img_mod.png");
        Imgcodecs.imwrite(file.getAbsolutePath(), img);
        Log.i("Image", "saved");
    }

    void decodeMsg() //Odczyt wiadomości
    {
        StringBuilder msgVal = new StringBuilder();
        StringBuilder msgLen = new StringBuilder();

        if(img.channels() == 3) //BGR
        {
            for (int i = 0; i < 3; i++) //Odczyt długości
            {
                double pixel[] = img.get(i, 0); //(X, Y) = W/H
                Log.v("Pixel value", Arrays.toString(pixel));

                for (int j = 0; j < 3; j++)
                {
                    if (pixel[j] % 2 == 0)
                    {
                        Log.v("Field", String.valueOf(j));
                        Log.v("Byte value", "0");
                        msgLen.append(String.valueOf(0));
                    }
                    else
                    {
                        Log.v("Field", String.valueOf(j));
                        Log.v("Byte value", "1");
                        msgLen.append(String.valueOf(1));
                    }
                }
            }

            Log.v("Binary length value", String.valueOf(msgLen));
            String msgL = String.valueOf(msgLen);
            int msgLength = Integer.parseInt(msgL, 2);
            Log.v("Message length", String.valueOf(msgLength));

            for (int i = 3; i < (5 + 4) * 3; i++) //Odczyt treści
            {
                double pixel[] = img.get(i, 0); //(X, Y) = W/H
                Log.v("Pixel value", Arrays.toString(pixel));

                for (int j = 0; j < 3; j++)
                {
                    if (pixel[j] % 2 == 0)
                    {
                        Log.v("Field", String.valueOf(j));
                        Log.v("Byte value", "0");
                        msgVal.append(String.valueOf(0));
                    }
                    else
                    {
                        Log.v("Field", String.valueOf(j));
                        Log.v("Byte value", "1");
                        msgVal.append(String.valueOf(1));
                    }
                }
            }

            Log.v("Binary char value", String.valueOf(msgVal));

            List<String> CV = new ArrayList<String>();

            for (int i = 0; i < msgVal.length(); i = i + 9) //Podzielenie wartości i wczytanie ich do tablicy
            {
                int endIndex = Math.min(i + 9, msgVal.length());
                CV.add(msgVal.substring(i, endIndex));
            }

            String charVal[] = CV.toArray(new String[CV.size()]);
            Log.v("Binary char array", Arrays.toString(charVal));
        }
    }
}