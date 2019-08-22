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
import android.widget.EditText;
import android.widget.Toast;

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

public class ChangePassword extends AppCompatActivity {

    String user_name,user_email,oldpassword,newpassword,repassword;
    EditText EtOldPassword,EtConfirmNewPassword,EtNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);

        Bundle bundle=getIntent().getExtras();
        //user_name=bundle.getString("user_name");
        user_email=bundle.getString("user_email");
        //Toast.makeText(this,user_email,Toast.LENGTH_LONG).show();

        EtOldPassword=findViewById(R.id.EtOldPassword);
        EtConfirmNewPassword=findViewById(R.id.EtConfirmNewPassword);
        EtNewPassword=findViewById(R.id.EtNewPassword);
    }

    public void change_password(View view)
    {
        oldpassword=EtOldPassword.getText().toString();
        newpassword=EtNewPassword.getText().toString();
        repassword=EtConfirmNewPassword.getText().toString();

        if(!oldpassword.equals("") && !newpassword.equals("") && !repassword.equals(""))
        {
            if(newpassword.equals(repassword))
            {
                //update password
                ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("Verifying...");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();


//                if(Internet.hasInternetAccess(this))
//                {
                    ChangePasswordTask changePasswordTask=new ChangePasswordTask(this,this,progressDialog);
                    changePasswordTask.execute(user_email,oldpassword,newpassword);
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
            Toast.makeText(this,"All Fields Are Mandatory ",Toast.LENGTH_LONG).show();
        }
    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }


    @Override
    public void onBackPressed()
    {
        finish();
    }
}



class ChangePasswordTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    String user_email,old_password,new_password;
    ProgressDialog progressDialog;

    ChangePasswordTask(Context ctx,Activity activity,ProgressDialog progressDialog)
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
        old_password=params[1];
        new_password=params[2];

        try {
            URL url=new URL(Constants.base_url+"userapi/change_password");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_email","UTF-8") +"="+URLEncoder.encode(user_email,"UTF-8")+"&"+
                    URLEncoder.encode("user_password","UTF-8") +"="+URLEncoder.encode(old_password,"UTF-8")+"&"+
                    URLEncoder.encode("new_password","UTF-8") +"="+URLEncoder.encode(new_password,"UTF-8");
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
                    Toast.makeText(ctx,"Password Update Success.",Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                else
                {
                    Toast.makeText(ctx,"Password Update Failed ...",Toast.LENGTH_LONG).show();
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


