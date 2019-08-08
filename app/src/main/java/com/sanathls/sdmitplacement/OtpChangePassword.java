package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OtpChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_change_password_layout);
    }

    public void go_back_to_login(View view) {
        Intent intent=new Intent(this,ForgotPassword.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,ForgotPassword.class);
        startActivity(intent);
        finish();
    }
}
