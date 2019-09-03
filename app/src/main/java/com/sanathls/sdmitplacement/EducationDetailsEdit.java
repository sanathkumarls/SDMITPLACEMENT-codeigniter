package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EducationDetailsEdit extends AppCompatActivity {

    String user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa;
    EditText Etuser_name,Etuser_usn,Etsslc,Etpuc,Etsem1,Etsem2,Etsem3,Etsem4,Etsem5,Etsem6,Etsem7;
    Float Msslc,Mpuc,Msem1,Msem2,Msem3,Msem4,Msem5,Msem6,Msem7=Float.valueOf(0),total,Mcgpa;

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

        //String received=user_email+"\n"+user_name+"\n"+user_usn+"\n"+sslc+"\n"+puc+"\n"+sem1+"\n"+sem2+"\n"+sem3+"\n"+sem4+"\n"+sem5+"\n"+sem6+"\n"+sem7+"\n"+cgpa;

        //Toast.makeText(this,received,Toast.LENGTH_LONG).show();

        Etuser_name=findViewById(R.id.Etuser_name);
        Etuser_usn=findViewById(R.id.Etuser_usn);
        Etsslc=findViewById(R.id.Etsslc);
        Etpuc=findViewById(R.id.Etpuc);
        Etsem1=findViewById(R.id.Etsem1);
        Etsem2=findViewById(R.id.Etsem2);
        Etsem3=findViewById(R.id.Etsem3);
        Etsem4=findViewById(R.id.Etsem4);
        Etsem5=findViewById(R.id.Etsem5);
        Etsem6=findViewById(R.id.Etsem6);
        Etsem7=findViewById(R.id.Etsem7);



        Etuser_name.setText(user_name);
        Etuser_usn.setText(user_usn);
        Etsslc.setText(sslc);
        Etpuc.setText(puc);
        Etsem1.setText(sem1);
        Etsem2.setText(sem2);
        Etsem3.setText(sem3);
        Etsem4.setText(sem4);
        Etsem5.setText(sem5);
        Etsem6.setText(sem6);
        Etsem7.setText(sem7);


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
        user_name=Etuser_name.getText().toString();
        user_usn=Etuser_usn.getText().toString();

        sslc=Etsslc.getText().toString();
        puc=Etpuc.getText().toString();

        sem1=Etsem1.getText().toString();
        sem2=Etsem2.getText().toString();
        sem3=Etsem3.getText().toString();
        sem4=Etsem4.getText().toString();
        sem5=Etsem5.getText().toString();
        sem6=Etsem6.getText().toString();
        sem7=Etsem7.getText().toString();

        if(user_name.equals("") || user_usn.equals("") || sslc.equals("") || puc.equals("") || sem1.equals("") || sem2.equals("") || sem3.equals("") || sem4.equals("") || sem5.equals("") || sem6.equals(""))
        {
            Toast.makeText(this,"All Fields Are Mandatory Except Sem 7 CGPA ",Toast.LENGTH_LONG).show();
        }
        else
        {
            Msslc=Float.parseFloat(sslc);
            Mpuc=Float.parseFloat(puc);

            Msem1=Float.parseFloat(sem1);
            Msem2=Float.parseFloat(sem2);
            Msem3=Float.parseFloat(sem3);
            Msem4=Float.parseFloat(sem4);
            Msem5=Float.parseFloat(sem5);
            Msem6=Float.parseFloat(sem6);

            if(!sem7.equals(""))
            {
                Msem7=Float.parseFloat(sem7);
            }

            if(Msslc > 100 || Mpuc > 100)
            {
                Toast.makeText(this,"Invalid SSLC Or Puc Percentage",Toast.LENGTH_LONG).show();
            }
            else if(Msem1 > 10 || Msem2 > 10 || Msem3 > 10 || Msem4 > 10 || Msem5 > 10 || Msem6 > 10 || Msem7 > 10)
            {
                Toast.makeText(this,"Invalid SGPA",Toast.LENGTH_LONG).show();
            }
            else
            {
                if(Msem7.equals(Float.valueOf(0)))
                {
                    total=Msem1+Msem2+Msem3+Msem4+Msem5+Msem6;
                    Mcgpa=total/6;
                    cgpa=String.format("%.2f",Mcgpa);
                }
                else
                {
                    total=Msem1+Msem2+Msem3+Msem4+Msem5+Msem6+Msem7;
                    Mcgpa=total/7;
                    cgpa=String.format("%.2f",Mcgpa);
                }
                //Toast.makeText(this,"TOTAL "+total+"\nCGPA "+cgpa,Toast.LENGTH_LONG).show();


            }



        }




    }
}
