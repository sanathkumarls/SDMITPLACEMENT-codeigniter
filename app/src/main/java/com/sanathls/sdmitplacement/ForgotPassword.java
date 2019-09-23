package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

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

public class ForgotPassword extends AppCompatActivity {

    String user_email;
    EditText etemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);
        etemail=findViewById(R.id.etEmail);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void go_back_to_login(View view) {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }

    public void submit(View view) {
        user_email=etemail.getText().toString();
        if(!user_email.equals(""))
        {
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Validating...");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();


//            if(Internet.hasInternetAccess(this))
//            {
                ForgotPasswordTask forgotPasswordTask=new ForgotPasswordTask(this,this,progressDialog);
                forgotPasswordTask.execute(user_email);
//            }
//            else
//            {
//                progressDialog.cancel();
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//                //Setting Dialog Title
//                alertDialog.setTitle("No Connection !!!");
//                //Setting Dialog Icon
//                alertDialog.setIcon(R.mipmap.ic_launcher);
//                //Setting Dialog Message
//                alertDialog.setMessage("Check Your Internet Connection And Try Again ...");
//
//                alertDialog.setCancelable(false);
//
//                //On Pressing Setting button
//                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        finishAffinity();
//                    }
//                });
//                alertDialog.show();
//            }


        }
        else
        {
            Toast.makeText(this,"Enter Your Registered Email Id",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }

}


class ForgotPasswordTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    String user_email;
    ProgressDialog progressDialog;

    ForgotPasswordTask(Context ctx,Activity activity,ProgressDialog progressDialog)
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

        user_email=params[0];


        try {
            URL url=new URL(Constants.base_url+"userapi/forgot_password");
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
            return "offline";
        } catch (IOException e) {
            Log.e("ioexcetion",e.toString());
            return "offline";
        }

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
                    //Toast.makeText(ctx,"Verification Success.",Toast.LENGTH_LONG).show();
                    String data=jsonObject.getString("0");
                    Log.e("0",data);
                    JSONObject jsonDataObject=new JSONObject(data);
                    String id=jsonDataObject.getString("id");
                    String user_name=jsonDataObject.getString("user_name");
                    String user_email=jsonDataObject.getString("user_email");
                    String user_usn=jsonDataObject.getString("user_usn");
                    String user_phone=jsonDataObject.getString("user_phone");
                    String user_password=jsonDataObject.getString("user_password");
                    String user_token=jsonDataObject.getString("user_token");
                    String user_device=jsonDataObject.getString("user_device");
                    String user_otp=jsonDataObject.getString("user_otp");



                    Intent intent=new Intent(ctx,OtpChangePassword.class);
                    intent.putExtra("user_otp",user_otp);
                    intent.putExtra("user_email",user_email);
                    ctx.startActivity(intent);
                    activity.finish();

                }
                else
                {
                    Toast.makeText(ctx,"Validation Failed ...",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                //Setting Dialog Title
                alertDialog.setTitle("Cannot Connect To Server !!!");
                //Setting Dialog Icon
                alertDialog.setIcon(R.mipmap.ic_launcher);
                //Setting Dialog Message
                alertDialog.setMessage("Check Your Internet Connection Or Try Again Later ...");

                alertDialog.setCancelable(false);

                //On Pressing Setting button
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finishAffinity();
                    }
                });
                alertDialog.show();
            }

    }
}


