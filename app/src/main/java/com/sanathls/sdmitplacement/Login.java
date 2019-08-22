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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {

    EditText useremail,userpassword;
    String user_email,user_password,user_token,role;

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

        user_email=useremail.getText().toString();
        user_password=userpassword.getText().toString();


        //Toast.makeText(this,role,Toast.LENGTH_SHORT).show();

        //Toast.makeText(this,"Email : "+email+"\nPassword : "+password,Toast.LENGTH_LONG).show();

        try
        {
            user_token=FirebaseInstanceId.getInstance().getToken();
            Log.e("login token",user_token);
        }
        catch (Exception e)
        {
            Log.e("login token error",e.toString());
            user_token="login";
        }



        if(!user_email.equals("") && !user_password.equals(""))
        {

            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Logging in");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();


//            if(Internet.hasInternetAccess(this))
//            {

                LoginTask loginTask=new LoginTask(this,this,progressDialog);
                loginTask.execute(user_email,user_password,user_token);

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
            Toast.makeText(this,"Fill All Fields",Toast.LENGTH_LONG).show();
        }



    }

    public void signup(View view) {
        Intent intent=new Intent(this,Signup.class);
        startActivity(intent);
        finish();
    }

    public void forgotpassword(View view) {
        Intent intent=new Intent(this,ForgotPassword.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {

        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.msg_dialog))
                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();//changed by sanath from finish
                    }
                })
                .setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}


class LoginTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;

    LoginTask(Context ctx,Activity activity,ProgressDialog progressDialog)
    {
        this.ctx=ctx;
        this.activity=activity;
        this.progressDialog=progressDialog;
    }
    //ProgressDialog progressDialog=new ProgressDialog(ctx);


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {

        String user_email=params[0];
        String user_password=params[1];
        String user_token=params[2];


        try {
            URL url=new URL(Constants.base_url+"userapi/login");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_email","UTF-8") +"="+URLEncoder.encode(user_email,"UTF-8")+"&"+
                    URLEncoder.encode("user_password","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8")+"&"+
                    URLEncoder.encode("user_token","UTF-8")+"="+URLEncoder.encode(user_token,"UTF-8");
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
                    Toast.makeText(ctx,"Login Success.",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ctx,"Login Failed ...",Toast.LENGTH_LONG).show();
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
