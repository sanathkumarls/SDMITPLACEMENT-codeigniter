package com.sanathls.sdmitplacement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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

public class Splash extends AppCompatActivity {

    ProgressDialog progressDialog;
    String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);



        //Toast.makeText(this,user_token,Toast.LENGTH_LONG).show();
        try
        {
            user_token = FirebaseInstanceId.getInstance().getToken();
            Log.e("slash token",user_token);
        }
        catch (NullPointerException e)
        {
            Log.e("slash token error",e.toString());
            user_token="splash";
        }



        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


//        if(Internet.hasInternetAccess(this))
//        {
            SplashTask splashTask=new SplashTask(this,this,progressDialog);
            splashTask.execute(user_token);
//        }
//        else
//        {
//            progressDialog.cancel();
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//            //Setting Dialog Title
//            alertDialog.setTitle("No Connection !!!");
//            //Setting Dialog Icon
//            alertDialog.setIcon(R.mipmap.ic_launcher);
//            //Setting Dialog Message
//            alertDialog.setMessage("Check Your Internet Connection And Try Again ...");
//
//            alertDialog.setCancelable(false);
//
//            //On Pressing Setting button
//            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finishAffinity();
//                }
//            });
//            alertDialog.show();
//        }






    }
}


class SplashTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;

    SplashTask(Context ctx,Activity activity,ProgressDialog progressDialog)
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


        String user_token=params[0];


        try {
            URL url=new URL(Constants.base_url+"userapi/session/");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_token","UTF-8") +"="+URLEncoder.encode(user_token,"UTF-8");
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
                    Intent intent=new Intent(ctx,Login.class);
                    ctx.startActivity(intent);
                    activity.finish();
                }
                else if (result.equals("success"))
                {

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


                    Intent intent=new Intent(ctx,Dashboard.class);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_email",user_email);
                    intent.putExtra("user_role",user_role);
                    ctx.startActivity(intent);
                    activity.finish();
                }
                else
                {
                    Intent intent=new Intent(ctx,Login.class);
                    ctx.startActivity(intent);
                    activity.finish();
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

