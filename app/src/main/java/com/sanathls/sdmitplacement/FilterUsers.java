package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class FilterUsers extends AppCompatActivity {

    String admin_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_users_layout);

        Bundle bundle=getIntent().getExtras();
        admin_email=bundle.getString("user_email");

    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }

    public void filter(View view) {
    }
}
