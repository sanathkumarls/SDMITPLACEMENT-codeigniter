package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EducationDetailsEdit extends AppCompatActivity {

    TextView tvEmail,tvName,tvNameValue,tvUsn,tvUsnValue,tvSslc,tvSslcValue,tvPuc,tvPucValue;
    TextView  tvSem1,tvSem2,tvSem3,tvSem4,tvSem5,tvSem6,tvSem7,tvSem8;
    TextView tvSem1Value,tvSem2Value,tvSem3Value,tvSem4Value,tvSem5Value,tvSem6Value,tvSem7Value,tvSem8Value;
    TextView tvCgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_edit_layout);

        tvEmail=findViewById(R.id.tvEmail);
        tvName=findViewById(R.id.tvName);
        tvNameValue=findViewById(R.id.tvNameValue);
        tvUsn=findViewById(R.id.tvUsn);
        tvUsnValue=findViewById(R.id.tvUsnValue);
        tvSslc=findViewById(R.id.tvSslc);
        tvSslcValue=findViewById(R.id.tvSslcValue);
        tvPuc=findViewById(R.id.tvPuc);
        tvPucValue=findViewById(R.id.tvPucValue);


        tvSem1=findViewById(R.id.tvSem1);
        tvSem2=findViewById(R.id.tvSem2);
        tvSem3=findViewById(R.id.tvSem3);
        tvSem4=findViewById(R.id.tvSem4);
        tvSem5=findViewById(R.id.tvSem5);
        tvSem6=findViewById(R.id.tvSem6);
        tvSem7=findViewById(R.id.tvSem7);
        tvSem8=findViewById(R.id.tvSem8);


        tvSem1Value=findViewById(R.id.tvSem1Value);
        tvSem2Value=findViewById(R.id.tvSem2Value);
        tvSem3Value=findViewById(R.id.tvSem3Value);
        tvSem4Value=findViewById(R.id.tvSem4Value);
        tvSem5Value=findViewById(R.id.tvSem5Value);
        tvSem6Value=findViewById(R.id.tvSem6Value);
        tvSem7Value=findViewById(R.id.tvSem7Value);
        tvSem8Value=findViewById(R.id.tvSem8Value);

        tvCgpa=findViewById(R.id.tvCgpa);

    }
}
