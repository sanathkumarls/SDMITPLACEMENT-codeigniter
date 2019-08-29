package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EducationDetails extends AppCompatActivity {

    TextView tvEmail,tvNameValue,tvUsnValue,tvSslcValue,tvPucValue;
    TextView tvSem1Value,tvSem2Value,tvSem3Value,tvSem4Value,tvSem5Value,tvSem6Value,tvSem7Value,tvSem8Value;
    TextView tvCgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_layout);

        tvEmail=findViewById(R.id.tvEmail);
        tvNameValue=findViewById(R.id.tvNameValue);
        tvUsnValue=findViewById(R.id.tvUsnValue);
        tvSslcValue=findViewById(R.id.tvSslcValue);
        tvPucValue=findViewById(R.id.tvPucValue);


        tvSem1Value=findViewById(R.id.tvSem1Value);
        tvSem2Value=findViewById(R.id.tvSem2Value);
        tvSem3Value=findViewById(R.id.tvSem3Value);
        tvSem4Value=findViewById(R.id.tvSem4Value);
        tvSem5Value=findViewById(R.id.tvSem5Value);
        tvSem6Value=findViewById(R.id.tvSem6Value);
        tvSem7Value=findViewById(R.id.tvSem7Value);
        tvSem8Value=findViewById(R.id.tvSem8Value);


        tvCgpa=findViewById(R.id.tvCgpa);


        TextView textViews[]=new TextView[14];

        textViews[0]=tvEmail;
        textViews[1]=tvNameValue;
        textViews[2]=tvUsnValue;
        textViews[3]=tvSslcValue;
        textViews[4]=tvPucValue;


        textViews[5]=tvSem1Value;
        textViews[6]=tvSem2Value;
        textViews[7]=tvSem3Value;
        textViews[8]=tvSem4Value;
        textViews[9]=tvSem5Value;
        textViews[10]=tvSem6Value;
        textViews[11]=tvSem7Value;
        textViews[12]=tvSem8Value;


        textViews[13]=tvCgpa;


    }

    public void go_back_to_login(View view)
    {
        finish();
    }

    public void edit(View view) {
        Intent i=new Intent(this,EducationDetailsEdit.class);
        startActivity(i);
    }
}
