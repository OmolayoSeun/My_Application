package com.example.myapplication;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;/*
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    Button read;
    TextView output;
    StringBuilder text = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.output);
        read = findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkPermission()) {
                            File sdcard = Environment.getExternalStorageDirectory();
                            PdfReader reader = null;
                            try {
                                reader = new PdfReader(sdcard.getAbsolutePath() + "/Download/Omolayo Seun second semester.pdf");
                                int numOfPages = reader.getNumberOfPages();

                                for(int i = 1; i <= numOfPages; i++) {

                                    text.append(PdfTextExtractor.getTextFromPage(reader, i));

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            reader.close();

                            output.setText(text);
                            }
                        } else {
                            requestPermission(); // Code for permission
                        }
                    } else {
                        File sdcard = Environment.getExternalStorageDirectory();
                        /*File dir = new File(sdcard.getAbsolutePath() + "/text/");
                        if(dir.exists()) {
                            File file = new File(dir, "sample.txt");
                            FileOutputStream os = null;

                            try {
                                BufferedReader br = new BufferedReader(new FileReader(file));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    text.append(line);
                                    text.append('\n');
                                }
                                br.close();
                            } catch (IOException e) {
                                //You'll need to add proper error handling here
                            }*/
                        PdfReader reader = null;
                        try {
                             reader = new PdfReader(sdcard.getAbsolutePath() + "/Download/Omolayo Seun second semester.pdf");
                            int numOfPages = reader.getNumberOfPages();

                            for(int i = 1; i <= numOfPages; i++) {

                                    text.append(PdfTextExtractor.getTextFromPage(reader, i));

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                            reader.close();

                            output.setText(text);
                }
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to read files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}