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

public class SendNotification extends AppCompatActivity {

    EditText title_send,message,link;
    String title,descrition,web_link,user_email,user_name,user_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_notification_layout);

        Bundle bundle=getIntent().getExtras();
        user_email=bundle.getString("user_email");
        user_name=bundle.getString("user_name");
        user_role=bundle.getString("user_role");

        title_send=findViewById(R.id.title_send);
        message=findViewById(R.id.message);
        link=findViewById(R.id.link);
    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }

    public void send_notification(View view)
    {
        title=title_send.getText().toString();
        descrition=message.getText().toString();
        web_link=link.getText().toString();

        //Toast.makeText(this,title+"\n"+descrition+"\n"+web_link,Toast.LENGTH_LONG).show();

        if(!title.equals("") && !descrition.equals("") && !web_link.equals(""))
        {
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Sending...");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            SendNotificationTask sendNotificationTask=new SendNotificationTask(this,this,progressDialog);
            sendNotificationTask.execute(user_email,title,descrition,web_link,user_name,user_role);
        }
        else
        {
            Toast.makeText(this,"ALL FIELDS ARE MANDATORY",Toast.LENGTH_LONG).show();
        }
    }
}



class SendNotificationTask extends AsyncTask<String,String,String> {
    Context ctx;
    Activity activity;
    String user_email, title,description,link,user_name,user_role;
    ProgressDialog progressDialog;

    SendNotificationTask(Context ctx, Activity activity, ProgressDialog progressDialog) {
        this.ctx = ctx;
        this.activity = activity;
        this.progressDialog = progressDialog;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {


        user_email = params[0];
        title = params[1];
        description = params[2];
        link = params[3];
        user_name=params[4];
        user_role=params[5];


        try {
            URL url = new URL(Constants.base_url + "adminapi/send_notification");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8") + "&" +
                    URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8")+ "&" +
                    URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8")+ "&" +
                    URLEncoder.encode("link", "UTF-8") + "=" + URLEncoder.encode(link, "UTF-8");
            bw.write(data);
            bw.flush();
            bw.close();
            os.close();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String response = "", line = "";
            while ((line = br.readLine()) != null) {
                response += line;
            }
            br.close();
            is.close();
            con.disconnect();
            return response;

        } catch (MalformedURLException e) {
            Log.e("malformedurl", e.toString());
            return "offline";
        } catch (IOException e) {
            Log.e("ioexcetion", e.toString());
            return "offline";
        }


    }


    @Override
    protected void onPostExecute(String response) {

        progressDialog.cancel();
        //Toast.makeText(ctx,response,Toast.LENGTH_LONG).show();
        Log.e("Response", response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            if (result.equals("failure")) {
                String message = jsonObject.getString("message");
                Log.e("message", message);
                Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
            } else if (result.equals("success")) {
                Toast.makeText(ctx, "Notification Sent Successfully.", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ctx,Dashboard.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_email",user_email);
                intent.putExtra("user_role",user_role);
                intent.putExtra("user_display","0");
                ctx.startActivity(intent);
                activity.finish();
            } else {
                Toast.makeText(ctx, "Notification Sending Failed ...", Toast.LENGTH_LONG).show();
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