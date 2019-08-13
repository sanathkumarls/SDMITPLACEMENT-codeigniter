package com.sanathls.sdmitplacement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Otp extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    String entered_otp,user_name,user_email,user_otp;
    TextView tv_email;
    Button btn_verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_layout);
        Bundle bundle=getIntent().getExtras();
        user_name=bundle.getString("user_name");
        user_email=bundle.getString("user_email");
        user_otp=bundle.getString("user_otp");


        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        tv_email=(TextView)findViewById(R.id.tv_email);

        btn_verify=(Button)findViewById(R.id.bt_verify);

        tv_email.setText(user_email);


        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));


    }



    public void check_otp(View view) {


        entered_otp=et1.getText().toString();
        entered_otp+=et2.getText().toString();
        entered_otp+=et3.getText().toString();
        entered_otp+=et4.getText().toString();
        Log.e("e",entered_otp);

        if(entered_otp.equals(user_otp))
        {
            //Toast.makeText(this,"matched",Toast.LENGTH_LONG).show();

            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Verifying...");
            progressDialog.setMessage("Please Wait");
            progressDialog.show();

            // make as active
            OtpTask otpTask=new OtpTask(this,this,progressDialog);
            otpTask.execute(user_name,user_email);
        }
        else
        {
            Toast.makeText(this,"OTP Does Not Match",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,Signup.class);
            startActivity(intent);
            finish();
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

                case R.id.et1:
                    if (text.length() == 1)
                        et2.requestFocus();
                    break;
                case R.id.et2:
                    if (text.length() == 1)
                        et3.requestFocus();
                    else
                        et1.requestFocus();
                    break;
                case R.id.et3:
                    if (text.length() == 1)
                        et4.requestFocus();
                    else
                        et2.requestFocus();
                    break;
                case R.id.et4:
                    if (text.length() == 0)
                        et3.requestFocus();
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


class OtpTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    String user_name,user_email;
    ProgressDialog progressDialog;

    OtpTask(Context ctx,Activity activity,ProgressDialog progressDialog)
    {
        this.ctx=ctx;
        this.activity=activity;
        this.progressDialog=progressDialog;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        user_name=params[0];
        user_email=params[1];


        try {
            URL url=new URL(Constants.base_url+"userapi/activate_user");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_email","UTF-8") +"="+URLEncoder.encode(user_email,"UTF-8");
            bw.write(data);
            bw.flush();
            bw.close();
            os.close();
            InputStream is=con.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String response="",line="";
            while((line=br.readLine()) != null)
            {
                response+=line;
            }
            br.close();
            is.close();
            con.disconnect();
            return response;

        } catch (MalformedURLException e) {
            Log.e("malformedurl",e.toString());
        } catch (IOException e) {
            Log.e("ioexcetion",e.toString());
        }


        return "failure";

    }


    @Override
    protected void onPostExecute(String response) {

        progressDialog.cancel();
        //Toast.makeText(ctx,response,Toast.LENGTH_LONG).show();
        Log.e("Response",response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            String result=jsonObject.getString("result");
            if(result.equals("failure"))
            {
                String message=jsonObject.getString("message");
                Log.e("message",message);
                Toast.makeText(ctx,message,Toast.LENGTH_LONG).show();
            }
            else if (result.equals("success"))
            {
                Toast.makeText(ctx,"Verification Success.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ctx,Splash.class);
                ctx.startActivity(intent);
                activity.finish();
            }
            else
            {
                Toast.makeText(ctx,"Verification Failed ...",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            Toast.makeText(ctx,"Verification Failed ...",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}

