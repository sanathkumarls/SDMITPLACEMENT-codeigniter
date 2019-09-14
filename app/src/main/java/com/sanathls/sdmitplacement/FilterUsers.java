package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FilterUsers extends AppCompatActivity {

    CheckBox Cb_sslc_filter,Cb_puc_filter,Cb_cgpa_filter;
    EditText Etsslc_filter,Etpuc_filter,Etcgpa_filter;
    Button BtnFilter;
    String admin_email,sslc="no",sslc_score,puc="no",puc_score,cgpa="no",cgpa_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_users_layout);

        Bundle bundle=getIntent().getExtras();
        admin_email=bundle.getString("user_email");

        Cb_sslc_filter=findViewById(R.id.Cb_sslc_filter);
        Cb_puc_filter=findViewById(R.id.Cb_puc_filter);
        Cb_cgpa_filter=findViewById(R.id.Cb_cgpa_filter);

        Etsslc_filter=findViewById(R.id.Etsslc_filter);
        Etpuc_filter=findViewById(R.id.Etpuc_filter);
        Etcgpa_filter=findViewById(R.id.Etcgpa_filter);

        BtnFilter=findViewById(R.id.BtnFilter);


    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }

    public void filter(View view)
    {
        int flag=1;
        if(Cb_sslc_filter.isChecked())
        {
            sslc="yes";
            sslc_score=Etsslc_filter.getText().toString();
            if(!sslc_score.equals(""))
            {
                if(Float.parseFloat(sslc_score) > 100)
                {
                    Toast.makeText(this,"Invalid SSLC Percentage",Toast.LENGTH_LONG).show();
                    flag=0;
                }
            }
            else
            {
                Toast.makeText(this,"Please Enter SSLC Percentage",Toast.LENGTH_LONG).show();
                flag=0;
            }

        }
        if(Cb_puc_filter.isChecked())
        {
            puc="yes";
            puc_score=Etpuc_filter.getText().toString();
            if(!puc_score.equals(""))
            {
                if(Float.parseFloat(puc_score) > 100)
                {
                    Toast.makeText(this,"Invalid PUC Percentage",Toast.LENGTH_LONG).show();
                    flag=0;
                }
            }
            else
            {
                Toast.makeText(this,"Please Enter PUC Percentage",Toast.LENGTH_LONG).show();
                flag=0;
            }

        }
        if(Cb_cgpa_filter.isChecked())
        {
            cgpa="yes";
            cgpa_score=Etcgpa_filter.getText().toString();
            if(!cgpa_score.equals(""))
            {
                if(Float.parseFloat(cgpa_score) > 10)
                {
                    Toast.makeText(this,"Invalid CGPA",Toast.LENGTH_LONG).show();
                    flag=0;
                }
            }
            else
            {
                Toast.makeText(this,"Please Enter CGPA",Toast.LENGTH_LONG).show();
                flag=0;
            }

        }

        if(flag == 1)
        {
            //Toast.makeText(this, "sslc : "+sslc+" score : "+sslc_score+"\npuc :"+puc+" score : "+puc_score+"\n cgpa : "+cgpa+" score : "+cgpa_score,Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,ViewUsers.class);
            i.putExtra("admin_email",admin_email);
            i.putExtra("sslc",sslc);
            i.putExtra("sslc_score",sslc_score);
            i.putExtra("puc",puc);
            i.putExtra("puc_score",puc_score);
            i.putExtra("cgpa",cgpa);
            i.putExtra("cgpa_score",cgpa_score);
            startActivity(i);
        }






    }

    public void enable_disable_sslc(View view)
    {
        if(Cb_sslc_filter.isChecked())
        {
            //Toast.makeText(this,"yes",Toast.LENGTH_LONG).show();
            Etsslc_filter.setEnabled(true);
            BtnFilter.setText("Filter");
        }
        else
        {
            //Toast.makeText(this,"no",Toast.LENGTH_LONG).show();
            Etsslc_filter.setEnabled(false);
            if(!Cb_puc_filter.isChecked() && !Cb_cgpa_filter.isChecked())
            {
                BtnFilter.setText(R.string.view_all_users);
            }
        }
    }

    public void enable_disable_puc(View view)
    {
        if(Cb_puc_filter.isChecked())
        {
            //Toast.makeText(this,"yes",Toast.LENGTH_LONG).show();
            Etpuc_filter.setEnabled(true);
            BtnFilter.setText("Filter");
        }
        else
        {
            //Toast.makeText(this,"no",Toast.LENGTH_LONG).show();
            Etpuc_filter.setEnabled(false);
            if(!Cb_sslc_filter.isChecked() && !Cb_cgpa_filter.isChecked())
            {
                BtnFilter.setText(R.string.view_all_users);
            }
        }
    }

    public void enable_disable_cgpa(View view)
    {
        if(Cb_cgpa_filter.isChecked())
        {
            //Toast.makeText(this,"yes",Toast.LENGTH_LONG).show();
            Etcgpa_filter.setEnabled(true);
            BtnFilter.setText("Filter");
        }
        else
        {
            //Toast.makeText(this,"no",Toast.LENGTH_LONG).show();
            Etcgpa_filter.setEnabled(false);
            if(!Cb_sslc_filter.isChecked() && !Cb_puc_filter.isChecked())
            {
                BtnFilter.setText(R.string.view_all_users);
            }
        }
    }
}
