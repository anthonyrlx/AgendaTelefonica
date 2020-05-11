package com.example.agendatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
