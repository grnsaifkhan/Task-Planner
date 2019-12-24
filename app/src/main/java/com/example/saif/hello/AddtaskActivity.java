package com.example.saif.hello;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddtaskActivity extends AppCompatActivity {
    TextView _task_date ;
    EditText _task_user1 , _remark_user1 , _task_user2 , _remark_user2 ;
    Button _btn_tasksubmit;
    Calendar calendar;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        _task_date = (TextView) findViewById(R.id.task_date);
        _task_user1 = (EditText) findViewById(R.id.task_user1);
        _remark_user1 = (EditText) findViewById(R.id.remark_user1);
        _task_user2 = (EditText) findViewById(R.id.task_user2);
        _remark_user2 = (EditText) findViewById(R.id.remark_user2);
        _btn_tasksubmit = (Button) findViewById(R.id.btn_taskSubmit);

        _task_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddtaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        _task_date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },day,month,year);
                datePickerDialog.show();

            }
        });

        _btn_tasksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://smartfridge.000webhostapp.com/api/post_of_app_data.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddtaskActivity.this, "Something Wrong!!!", Toast.LENGTH_SHORT).show();
                    }
                }) {

                    protected Map<String , String> getParams(){
                        Map<String , String> parr = new HashMap<String , String>();

                        parr.put("mydate",_task_date.getText().toString());
                        parr.put("myuser1",_task_user1.getText().toString());
                        parr.put("myremark1",_remark_user1.getText().toString());
                        parr.put("myuser2",_task_user2.getText().toString());
                        parr.put("myremark2",_remark_user2.getText().toString());

                        return parr;
                    }

                };

                AppController.getInstance().addToRequestQueue(stringRequest);

                Toast.makeText(AddtaskActivity.this, "Task submitted successfully!!!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddtaskActivity.this , MainActivity.class);
                startActivity(intent);

            }
        });


    }
}
