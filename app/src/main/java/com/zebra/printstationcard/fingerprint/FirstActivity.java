package com.zebra.printstationcard.fingerprint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zebra.printstationcard.MainActivity;
import com.zebra.printstationcard.util.UIHelper;


import com.zebra.printstationcard.R;

import java.io.File;


public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "";
    Button btnSignup;
    Button btnPrintCard;
    Button btnIdentify;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_main);

        UIHelper.setLogoOnActionBar(this);

        isStoragePermissionGranted();

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnPrintCard = (Button) findViewById(R.id.btnPrintCard);
        btnIdentify = (Button) findViewById(R.id.btnIdentify);

        btnPrintCard.setVisibility(View.INVISIBLE);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FirstActivity.this, "Singup user", Toast.LENGTH_SHORT).show();
                Intent formIntent = new Intent(FirstActivity.this, FormActivity.class);
                makeFolders();
                startActivity(formIntent);
            }
        });

        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FirstActivity.this, "Identification", Toast.LENGTH_SHORT).show();
                Intent identifyIntent = new Intent(FirstActivity.this, IdentificationActivity.class);
                makeFolders();
                startActivity(identifyIntent);
            }
        });

        btnPrintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FirstActivity.this, "Identification", Toast.LENGTH_SHORT).show();
                Intent printIntent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(printIntent);
            }
        });
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                isStoragePermissionGranted();
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void makeFolders(){
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "ProjetoMedico");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File folder2 = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "ProjetoMedico" + File.separator + "Medicos");

            if (!folder2.exists()) {
                folder2.mkdirs();
            }

            File folder3 = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "ProjetoMedico" + File.separator + "Pacientes");

            if (!folder3.exists()) {
                folder3.mkdirs();
            }
    }
}

