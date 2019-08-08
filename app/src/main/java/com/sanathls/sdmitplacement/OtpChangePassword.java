package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OtpChangePassword extends AppCompatActivity {

    String user_email,user_otp,entered_otp,newpassword,repassword;
    EditText otp_et1,otp_et2,otp_et3,otp_et4,etConfirmNewPassword,etNewPassword;
    TextView otp_tv_email,tvHeader;
    LinearLayout otplayout,passwordlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_change_password_layout);
        Bundle bundle=getIntent().getExtras();

        user_email=bundle.getString("user_email");
        user_otp=bundle.getString("user_otp");

        otp_et1=(EditText)findViewById(R.id.otp_et1);
        otp_et2=(EditText)findViewById(R.id.otp_et2);
        otp_et3=(EditText)findViewById(R.id.otp_et3);
        otp_et4=(EditText)findViewById(R.id.otp_et4);
        otp_tv_email=(TextView)findViewById(R.id.otp_tv_email);

        otp_tv_email.setText(user_email);

        tvHeader=findViewById(R.id.tvHeader);
        etConfirmNewPassword=findViewById(R.id.etConfirmNewPassword);
        etNewPassword=findViewById(R.id.etNewPassword);

        otplayout=findViewById(R.id.otp_layout);
        passwordlayout=findViewById(R.id.password_layout);

        otp_et1.addTextChangedListener(new OtpChangePassword.GenericTextWatcher(otp_et1));
        otp_et2.addTextChangedListener(new OtpChangePassword.GenericTextWatcher(otp_et2));
        otp_et3.addTextChangedListener(new OtpChangePassword.GenericTextWatcher(otp_et3));
        otp_et4.addTextChangedListener(new OtpChangePassword.GenericTextWatcher(otp_et4));

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

    public void verify_otp(View view) {

        entered_otp=otp_et1.getText().toString();
        entered_otp+=otp_et2.getText().toString();
        entered_otp+=otp_et3.getText().toString();
        entered_otp+=otp_et4.getText().toString();

        if(entered_otp.equals(user_otp))
        {
            otplayout.setVisibility(View.GONE);
            passwordlayout.setVisibility(View.VISIBLE);
            tvHeader.setText("Change Password");
        }
        else
        {
            Toast.makeText(this,"OTP Does Not Match",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,ForgotPassword.class);
            startActivity(intent);
            finish();
        }


    }

    public void update_password(View view) {

        newpassword=etNewPassword.getText().toString();
        repassword=etConfirmNewPassword.getText().toString();

        if(!newpassword.equals("") && !repassword.equals(""))
        {
            if(newpassword.equals(repassword))
            {
                //update password
            }
            else
            {
                Toast.makeText(this,"Passwords Not Matching",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Password Fields Cannot Be Blank",Toast.LENGTH_LONG).show();
        }

    }


    /**
     * automatic moving text on verification
     */

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.otp_et1:
                    if (text.length() == 1)
                        otp_et2.requestFocus();
                    break;
                case R.id.otp_et2:
                    if (text.length() == 1)
                        otp_et3.requestFocus();
                    else
                        otp_et1.requestFocus();
                    break;
                case R.id.otp_et3:
                    if (text.length() == 1)
                        otp_et4.requestFocus();
                    else
                        otp_et2.requestFocus();
                    break;
                case R.id.otp_et4:
                    if (text.length() == 0)
                        otp_et3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
}


