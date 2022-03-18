package com.rkvermacode.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


     private RecyclerView recyclerView;
     private ProgressBar progressBar;
     private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
     private CurrencyRVAdapter currencyRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.pbLoading);



        currencyRVModelArrayList = new ArrayList<>();
        currencyRVAdapter = new CurrencyRVAdapter(currencyRVModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(currencyRVAdapter);

        getCurrencyData();



    }



    private void getCurrencyData() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i=0;i<dataArray.length();i++){
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String name = dataObject.getString("name");
                        String symbol = dataObject.getString("symbol");
                        JSONObject quate = dataObject.getJSONObject("quote");
                        JSONObject USD = quate.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        currencyRVModelArrayList.add(new CurrencyRVModel(name,symbol,price));
                    }
                    currencyRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.getStackTrace();
                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("X-CMC_PRO_API_KEY","ee494c7f-ff28-4633-9e0e-a29198e820e8");
                return hashMap;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}