package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EducationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_layout);
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
