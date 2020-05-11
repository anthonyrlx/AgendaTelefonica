package com.example.agendatelefonica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sobre);
    }

    public void onClickButtons(View v){
        Intent newIntent = new Intent();
        if(v.getId() == R.id.button){
            newIntent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(v.getId() == R.id.buttonEmail) {
            newIntent = new Intent(getApplicationContext(), Email.class);
        }
        startActivity(newIntent);
    }
}
