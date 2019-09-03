package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EducationDetailsEdit extends AppCompatActivity {

    String user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa;
    EditText Etsslc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_edit_layout);

        Bundle bundle=getIntent().getExtras();
        user_email=bundle.getString("user_email");
        user_name=bundle.getString("user_name");
        user_usn=bundle.getString("user_usn");
        sslc=bundle.getString("sslc");
        puc=bundle.getString("puc");
        sem1=bundle.getString("sem1");
        sem2=bundle.getString("sem2");
        sem3=bundle.getString("sem3");
        sem4=bundle.getString("sem4");
        sem5=bundle.getString("sem5");
        sem6=bundle.getString("sem6");
        sem7=bundle.getString("sem7");
        cgpa=bundle.getString("cgpa");

        //String received=user_email+"\n"+user_name+"\n"+user_usn+"\n"+sslc+"\n"+puc+"\n"+sem1+"\n"+sem2+"\n"+sem3+"\n"+sem4+"\n"+sem5+"\n"+sem6+"\n"+sem7+"\n"+cgpa;

        //Toast.makeText(this,received,Toast.LENGTH_LONG).show();

        Etsslc=findViewById(R.id.Etsslc);
        Etsslc.setText(sslc);


    }

    public void go_back_to_education_details(View view)
    {
        finish();
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    public void update_marks(View view)
    {


    }
}
