package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ViewUsers extends AppCompatActivity {

    String admin_email,sslc,sslc_score,puc,puc_score,cgpa,cgpa_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_users_layout);

        Bundle bundle=getIntent().getExtras();
        admin_email=bundle.getString("admin_email");
        sslc=bundle.getString("sslc");
        sslc_score=bundle.getString("sslc_score");
        puc=bundle.getString("puc");
        puc_score=bundle.getString("puc_score");
        cgpa=bundle.getString("cgpa");
        cgpa_score=bundle.getString("cgpa_score");

        //Toast.makeText(this,"mail : "+admin_email+"\nsslc : "+sslc+" sslc score : "+sslc_score+"\npuc : "+puc+" puc score : "+puc_score+"\ncgpa : "+cgpa+" cgpa score : "+cgpa_score,Toast.LENGTH_LONG).show();

    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }
}
