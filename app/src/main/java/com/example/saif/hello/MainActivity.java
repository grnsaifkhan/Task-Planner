package com.example.saif.hello;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button button;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.CYAN,Color.BLUE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchingData();
            }
        });

        fetchingData();

        button = (Button) findViewById(R.id.btn_addTask);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , AddtaskActivity.class);
                startActivity(intent);

            }
        });



    }

    void fetchingData(){
        String url = "http://smartfridge.000webhostapp.com/api/json_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                swipeRefreshLayout.setRefreshing(true);

                final String[] date = new String[response.length()];
                final String[] user1 = new String[response.length()];
                final String[] remark1 = new String[response.length()];
                final String[] user2 = new String[response.length()];
                final String[] remark2 = new String[response.length()];


                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        date[response.length()-i-1] = jsonObject.getString("date");//this line is for print date in descending order //date[i] = jsonObject.getString("date"); // for ascending order
                        user1[response.length()-i-1] = jsonObject.getString("user1");//this line is for print user1 in descending order //  //user1[i] = jsonObject.getString("user1");// for ascending order
                        remark1[response.length()-i-1] = jsonObject.getString("remark1");// same as above line
                        user2[response.length()-i-1] = jsonObject.getString("user2");//this line is for print user2 in descending order // user2[i] = jsonObject.getString("user2");// for ascending order
                        remark2[response.length()-i-1] = jsonObject.getString("remark2");// same as above line

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_item,R.id.text_taskDate,date));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                        intent.putExtra("userTaskDate",date[position]);
                        intent.putExtra("userOneTask",user1[position]);
                        intent.putExtra("userOneRemark",remark1[position]);
                        intent.putExtra("userTwoTask",user2[position]);
                        intent.putExtra("userTwoRemark",remark2[position]);
                        startActivity(intent);
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        com.example.saif.hello.AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Exit From Application");
        alert.setMessage("Are You Sure?");
        alert.setCancelable(true);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
