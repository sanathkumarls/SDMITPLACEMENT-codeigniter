package com.sanathls.sdmitplacement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_layout);
        Bundle bundle=getIntent().getExtras();
        String otp=bundle.getString("user_otp");
        Toast.makeText(this,otp,Toast.LENGTH_LONG).show();
    }
}
