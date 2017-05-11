package com.surajapps.networkapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        errorMsg = (TextView) findViewById(R.id.error_msg);

        if (checkInternetStatus()) {
            errorMsg.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            runVolley();
        } else {
            errorMsg.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            errorMsg.setText(R.string.noInternet);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public Boolean checkInternetStatus() {
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected =  activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void runVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String webAddress = "https://s3-us-west-2.amazonaws.com/mobileappclass/android_terms.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, webAddress, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Book> booksList = new ArrayList<>();
                try {
                    String title, imgCover, description;
                    for (int i = 0; i < response.length(); i++) {
                        title = response.getJSONObject(i).get("title").toString();
                        description = response.getJSONObject(i).get("description").toString();
                        imgCover = response.getJSONObject(i).get("img").toString();
                        Book book = new Book(title, description, imgCover);
                        booksList.add(book);
                    }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               populateList(booksList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                errorMsg.setText(R.string.volleyError + ": " + error.toString());
                errorMsg.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void populateList(ArrayList<Book> booksList) {
        errorMsg.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerVAdapter recyclerVAdapter = new RecyclerVAdapter(getApplicationContext(), booksList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerVAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
