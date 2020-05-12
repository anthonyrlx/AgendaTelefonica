package com.example.agendatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButtons(View v){
        Intent newIntent = new Intent();
        if(v.getId() == R.id.buttonEmail){
            newIntent = new Intent(getApplicationContext(), Email.class);
        }
        else if(v.getId() == R.id.buttonSobre){
            newIntent = new Intent(getApplicationContext(), Sobre.class);
        }
        startActivity(newIntent);
    }

    public void mostrarJSON(String strjson){
        String data = "";
        try{
            JSONObject objRaiz = new JSONObject(strjson);
            JSONArray jsonArray = objRaiz.optJSONArray("listacontatos");
            for(int i = 0; i <= jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.optString("id");
                String nomecontato = jsonObject.optString("nomecontato  ");
                String email = jsonObject.optString("email");
                String endereco = jsonObject.optString("endereco");
                String genero = jsonObject.optString("genero");
                String celular = jsonObject.optString("celular");
                data += "\n id = " + id + ", email = " + email + ", nome" ;
            }
        } catch (Exception ex){

        }
    }

    private String downloadJSON (String myurl) throws IOException {
        InputStream is = null; String respostaHttp = "";
        HttpURLConnection conn = null; InputStream in = null;
        ByteArrayOutputStream bos = null; int len;
        try {
            URL u = new URL(myurl);
            conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(7000); // 7 segundos de timeout
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            in = conn.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            respostaHttp = bos.toString("UTF-8");
            return respostaHttp;
        } catch (Exception ex) {
            return ex.toString();
        } finally { if (in != null) in.close(); }
    }
}
