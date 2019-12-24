package com.example.saif.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

public class DetailActivity extends AppCompatActivity {

    TextView userTaskDate;
    TextView userOneTask;
    TextView userOneRemark;
    TextView userTwoTask;
    TextView userTwoRemark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        userTaskDate = (TextView) findViewById(R.id.text_userTaskDate);
        userOneTask = (TextView) findViewById(R.id.text_userOneTask);
        userOneRemark = (TextView) findViewById(R.id.text_userOneRemark);
        userTwoTask = (TextView) findViewById(R.id.text_userTwoTask);
        userTwoRemark = (TextView) findViewById(R.id.text_userTwoRemark);


        String _userTaskDate = getIntent().getStringExtra("userTaskDate");
        String _userOneTask = getIntent().getStringExtra("userOneTask");
        String _userOneRemark = getIntent().getStringExtra("userOneRemark");
        String _userTwoTask = getIntent().getStringExtra("userTwoTask");
        String _userTwoRemark = getIntent().getStringExtra("userTwoRemark");

        userTaskDate.setText("Date : "+_userTaskDate);
        userOneTask.setText(_userOneTask);
        userOneRemark.setText("Remark : "+ _userOneRemark);
        userTwoTask.setText(_userTwoTask);
        userTwoRemark.setText("Remark : "+ _userTwoRemark);


    }
}
