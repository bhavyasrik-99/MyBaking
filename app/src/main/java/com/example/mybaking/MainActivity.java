package com.example.mybaking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    int[] posters={R.drawable.nutella,R.drawable.brownies,R.drawable.cheesecake,R.drawable.yellowcake};
     ArrayList<BakePojo>mypojo=new ArrayList<>();
     String recipeurl="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
     ProgressBar progressBar;
     RecyclerView recyclerView;
     LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_main);
        progressBar = findViewById(R.id.progress_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isOnline()) {
            new RecipeAsync().execute();

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
        public boolean isOnline()
    {
            ConnectivityManager connectivityManager;
            connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();
        }
class RecipeAsync extends AsyncTask<String,Void,String>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url;
            url = new URL(recipeurl);
            HttpURLConnection httpURLConnection;
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();;
            InputStream is=httpURLConnection.getInputStream();
            Scanner scanner;
            scanner=new Scanner(is);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) return scanner.next();
            else return  null;
        }catch(MalformedURLException e){
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        try {

        JSONArray jsonArray=new JSONArray(s);
           int len=jsonArray.length();
            for (int a=0;a<len;a++) {
                JSONObject jsonObject = jsonArray.getJSONObject(a);
                String stepsarr = String.valueOf(jsonObject.getJSONArray("steps"));
                String ingrearr = String.valueOf(jsonObject.getJSONArray("ingredients"));
                String namesarr = jsonObject.getString("name");
                mypojo.add(new BakePojo(namesarr, ingrearr, stepsarr));
            }

                recyclerView.setAdapter(new BakeAdapter(posters, mypojo, MainActivity.this));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
}
