package com.sanathls.sdmitplacement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Signup extends AppCompatActivity {

    EditText name,email,usn,phone,password,repassword;
    String user_name,user_email,user_usn,user_phone,user_password,user_repassword,user_token,user_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        usn=(EditText)findViewById(R.id.usn);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        repassword=(EditText)findViewById(R.id.repassword);

    }

    public void already_a_user(View view) {

        super.onBackPressed();
    }

    public void register(View view) {

        user_name=name.getText().toString();
        user_email=email.getText().toString();
        user_usn=usn.getText().toString();
        user_phone=phone.getText().toString();
        user_password=password.getText().toString();
        user_repassword=repassword.getText().toString();
        user_token= FirebaseInstanceId.getInstance().getToken();
        user_device=android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;



        if(user_password.equals(user_repassword))
        {
            //Toast.makeText(this,"Name : "+user_name+"\nEmail : "+user_email+"\nUSN : "+user_usn+"\nPhone : "+user_phone+"\nPassword : "+user_password,Toast.LENGTH_LONG).show();
            SignupTask signupTask=new SignupTask(this,this);
            signupTask.execute(user_name,user_email,user_usn,user_phone,user_password,user_token,user_device);
        }
        else
        {
            Toast.makeText(this,"Passwords Not Matching",Toast.LENGTH_LONG).show();
        }



    }
}


class SignupTask extends AsyncTask<String,String,String>
{


    String baseurl="http://192.168.43.85/placement/";
    Context ctx;
    Activity activity;

    SignupTask(Context ctx,Activity activity)
    {
        this.ctx=ctx;
        this.activity=activity;
    }



    @Override
    protected void onPreExecute() {

//waiting dialog



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
            URL url=new URL(baseurl+"userapi/register.php");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            user_token=user_token.substring(0,50);
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
        } catch (IOException e) {
            Log.e("ioexcetion",e.toString());
        }


        return "failure";


    }

    @Override
    protected void onPostExecute(String response) {

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
                    Toast.makeText(ctx,"Resgistration Success.",Toast.LENGTH_LONG).show();
                    String id=jsonObject.getString("id");
                    String user_name=jsonObject.getString("user_name");
                    String user_email=jsonObject.getString("user_email");
                    String user_usn=jsonObject.getString("user_usn");
                    String user_phone=jsonObject.getString("user_phone");
                    String user_password=jsonObject.getString("user_password");
                    String user_token=jsonObject.getString("user_token");
                    String user_device=jsonObject.getString("user_device");
                    String user_otp=jsonObject.getString("user_otp");

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

            } catch (JSONException e) {
                Toast.makeText(ctx,"Registration Failed ...",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }



    }
}
