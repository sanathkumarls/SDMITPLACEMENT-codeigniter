package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class EducationDetailsEdit extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_edit_layout);

        Bundle bundle=getIntent().getExtras();
        String sem1=bundle.getString("sem1");

        Toast.makeText(this,sem1,Toast.LENGTH_LONG).show();

    }
}
