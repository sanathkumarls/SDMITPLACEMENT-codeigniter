package com.sanathls.sdmitplacement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {

    EditText useremail,userpassword;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        useremail=(EditText)findViewById(R.id.user_email);
        userpassword=(EditText)findViewById(R.id.user_pass);


        //get token
       // String token = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

        //get device name
       // String deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
        //Toast.makeText(getApplicationContext(),deviceName,Toast.LENGTH_LONG).show();
    }

    public void login(View view) {

        email=useremail.getText().toString();
        password=userpassword.getText().toString();

        Toast.makeText(this,"Email : "+email+"\nPassword : "+password,Toast.LENGTH_LONG).show();
    }

    public void signup(View view) {
        Intent intent=new Intent(this,Signup.class);
        startActivity(intent);
    }

    public void forgotpassword(View view) {
        Toast.makeText(getApplicationContext(),"forgotpassword",Toast.LENGTH_LONG).show();
    }
}
