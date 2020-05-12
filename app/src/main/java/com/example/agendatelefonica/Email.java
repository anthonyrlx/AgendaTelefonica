package com.example.agendatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_emails);
    }

    public void onClickButtons(View v){
        Intent newIntent = new Intent();
        if(v.getId() == R.id.button){
            newIntent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(v.getId() == R.id.buttonSobre){
            newIntent = new Intent(getApplicationContext(), Sobre.class);
        }
        startActivity(newIntent);
    }
}
