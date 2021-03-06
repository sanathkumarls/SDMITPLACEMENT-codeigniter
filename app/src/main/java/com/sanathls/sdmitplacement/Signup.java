package com.sanathls.sdmitplacement;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;

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

public class Signup extends AppCompatActivity {

    EditText name,email,usn,phone,password,repassword;
    String user_name,user_email,user_usn,user_phone,user_password,user_repassword,user_token,user_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        usn=(EditText)findViewById(R.id.usn);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        repassword=(EditText)findViewById(R.id.repassword);

    }

    public void already_a_user(View view) {

        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }

    public void register(View view) {

        user_name=name.getText().toString();
        user_email=email.getText().toString();
        user_usn=usn.getText().toString();
        user_phone=phone.getText().toString();
        user_password=password.getText().toString();
        user_repassword=repassword.getText().toString();

        user_device=android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;



        try
        {
            user_token= FirebaseInstanceId.getInstance().getToken();
            Log.e("registration token",user_token);
        }
        catch (Exception e)
        {
            Log.e("registration tokn error",e.toString());
            user_token="signup";
        }



        if(!user_name.equals("") && !user_email.equals("") && !user_usn.equals("") && !user_phone.equals("") && !user_password.equals("") && !user_repassword.equals("") )
        {
            if(user_password.equals(user_repassword))
            {
                //Toast.makeText(this,"Name : "+user_name+"\nEmail : "+user_email+"\nUSN : "+user_usn+"\nPhone : "+user_phone+"\nPassword : "+user_password,Toast.LENGTH_LONG).show();

                ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("Registering...");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();


//                if(Internet.hasInternetAccess(this))
//                {
                    SignupTask signupTask=new SignupTask(this,this,progressDialog);
                    signupTask.execute(user_name,user_email,user_usn,user_phone,user_password,user_token,user_device);
//                }
//                else
//                {
//                    progressDialog.cancel();
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//                    //Setting Dialog Title
//                    alertDialog.setTitle("No Connection !!!");
//                    //Setting Dialog Icon
//                    alertDialog.setIcon(R.mipmap.ic_launcher);
//                    //Setting Dialog Message
//                    alertDialog.setMessage("Check Your Internet Connection And Try Again ...");
//
//                    alertDialog.setCancelable(false);
//
//                    //On Pressing Setting button
//                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            finishAffinity();
//                        }
//                    });
//                    alertDialog.show();
//                }


            }
            else
            {
                Toast.makeText(this,"Passwords Not Matching",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"All Fields Are Required",Toast.LENGTH_LONG).show();
        }



    }
}


class SignupTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;

    SignupTask(Context ctx,Activity activity,ProgressDialog progressDialog)
    {
        this.ctx=ctx;
        this.activity=activity;
        this.progressDialog=progressDialog;
    }



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String user_name=params[0];
        String user_email=params[1];
        String user_usn=params[2];
        String user_phone=params[3];
        String user_password=params[4];
        String user_token=params[5];
        String user_device=params[6];



        try {
            URL url=new URL(Constants.base_url+"userapi/register");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_name","UTF-8") +"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                    URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"+
                    URLEncoder.encode("user_usn","UTF-8")+"="+URLEncoder.encode(user_usn,"UTF-8")+"&"+
                    URLEncoder.encode("user_phone","UTF-8")+"="+URLEncoder.encode(user_phone,"UTF-8")+"&"+
                    URLEncoder.encode("user_password","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8")+"&"+
                    URLEncoder.encode("user_token","UTF-8")+"="+URLEncoder.encode(user_token,"UTF-8")+"&"+
                    URLEncoder.encode("user_device","UTF-8")+"="+URLEncoder.encode(user_device,"UTF-8");
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

            //Toast.makeText(ctx,response,Toast.LENGTH_LONG).show();
            progressDialog.cancel();
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
                    Toast.makeText(ctx,"Resgistration Success.",Toast.LENGTH_LONG).show();
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
                    String user_role=jsonDataObject.getString("user_role");


                    Intent intent=new Intent(ctx,Otp.class);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_email",user_email);
                    intent.putExtra("user_otp",user_otp);
                    ctx.startActivity(intent);
                    activity.finish();
                }
                else
                {
                    Toast.makeText(ctx,"Registration Failed ...",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e)
            {

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
