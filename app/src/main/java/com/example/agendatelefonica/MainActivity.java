package com.example.agendatelefonica;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog ;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);

        // ListView on item selected listener.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
              String[] telefone = arrayList.get(position).split("/");
                Uri uri = Uri.parse("tel:" + telefone[1]);
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);

                 startActivity(intent);
            }
        });

    }

    public void lerJSON(View v) {
        if (checkInternetConection()){
            progressDialog = ProgressDialog.show(this, "", "Baixando dados");
            new DownloadJson().execute("http://mfpledon.com.br/listadecontatosbck.json");
        } else{
            Toast.makeText(getApplicationContext(),"Sem conexão. Verifique.",Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkInternetConection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
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


    public void mostraAgenda(String strjson){
        //recebe uma String com os dados do JSON
        String data = "";
        arrayList.clear();
        try {
            JSONObject objRaiz = new JSONObject(strjson);
            JSONArray jsonArray = objRaiz.getJSONArray("listacontatos");
            JSONObject jsonObject = null;

            for(int i=0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String nomecontato = jsonObject.getString("nomecontato");
                String email = jsonObject.getString("email");
                String endereco = jsonObject.getString("endereco");
                String genero = jsonObject.getString("genero");
                String celular = jsonObject.getString("celular");
                data = nomecontato + " / "+ celular;
                jsonObject = null;
                arrayList.add(data);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            arrayList.add(e.getMessage() +"\n\n"+ data + "\n\n");
            adapter.notifyDataSetChanged();
        }
        finally {
            progressDialog.dismiss();
        }
    }


    private class DownloadJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params[0] é o URL.
            try {
                return downloadJSON(params[0]);
            } catch (IOException e) {
                return "URL inválido";
            }
        }
//
//         onPostExecute exibe o resultado do AsyncTask
        @Override
        protected void onPostExecute(String result) {
            mostraAgenda(result);
        }

        private String downloadJSON(String myurl) throws IOException {
            InputStream is = null;
            String respostaHttp = "";
            HttpURLConnection conn = null;
            InputStream in = null;
            ByteArrayOutputStream bos = null;
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
                int len;
                while ((len = in.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                respostaHttp = bos.toString("UTF-8");
                return respostaHttp;
            } catch (Exception ex) {
                return "URL inválido ou estouro de memória ou...: \n" + ex.getMessage() + "\nmyurl: " + myurl;
            } finally {
                if (in != null) in.close();
            }
        }

    }


//
//    public void onClick(View view) {
//
//        String telefone = campoTelefone.getText().toString();
//
//        Uri uri = Uri.parse("tel:" + telefone);
//        Intent intent = new Intent(Intent.ACTION_DIAL,uri);
//
//        startActivity(intent);
//
//
//    }


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
