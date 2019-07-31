package com.sanathls.sdmitplacement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Otp extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    String entered_otp,user_name,user_email,user_otp;
    TextView tv_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_layout);
        Bundle bundle=getIntent().getExtras();
        user_name=bundle.getString("user_name");
        user_email=bundle.getString("user_email");
        user_otp=bundle.getString("user_otp");


        et1=(EditText)findViewById(R.id.et_one);
        et2=(EditText)findViewById(R.id.et_two);
        et3=(EditText)findViewById(R.id.et_three);
        et4=(EditText)findViewById(R.id.et_four);
        tv_email=(TextView)findViewById(R.id.tv_email);

        tv_email.setText(user_email);





    }



    public void register(View view) {


        entered_otp=et1.getText().toString();
        entered_otp+=et2.getText().toString();
        entered_otp+=et3.getText().toString();
        entered_otp+=et4.getText().toString();
        if(entered_otp.equals(user_otp))
        {
            Toast.makeText(this,"matched",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Not matched",Toast.LENGTH_LONG).show();
        }
    }
}
