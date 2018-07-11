package com.testerautocomplete.ahmedalrusaini.apptestautocomplete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.id_autocompletetextview);
    // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apina.address.gov.sa/NationalAddress/v3.1/lookup/cities?format=JSON&regionid=-1&language=A&api_key=03276a0c4d6f46719efba071a0c3b8be";
    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            LinkedList<String> City=new LinkedList<>();
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray JSONCities = jsonObject.getJSONArray("Cities");
                            for (int i = 0 ; i < JSONCities.length() ; i++){
                                JSONObject jsonCity=JSONCities.getJSONObject(i);
                                String CityName = jsonCity.getString("Name");
                                City.addLast(CityName);
                            }
                            String[] words = new String[City.size()];
                            for(int i=0;i<City.size();i++){
                                words[i] = City.get(i);
                            }
                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1 , words);
                            autoCompleteTextView.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}