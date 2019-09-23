package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Notification extends AppCompatActivity {

    TextView title,description,link;
    String current_title,current_description,current_link,user_name,user_email,user_role,id;
    ImageView ImgDelete;
    ProgressDialog progressDialog;
    Context ctx=this;
    Activity activity=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle bundle=getIntent().getExtras();
        current_title=bundle.getString("current_title");
        current_description=bundle.getString("current_description");
        current_link=bundle.getString("current_link");
        user_name=bundle.getString("user_name");
        user_email=bundle.getString("user_email");
        user_role=bundle.getString("user_role");
        id=bundle.getString("id");


        //Toast.makeText(this,id,Toast.LENGTH_LONG).show();

        setTitle(R.string.app_name);

        title=findViewById(R.id.tv_title);
        description=findViewById(R.id.tv_description);
        link=findViewById(R.id.tv_link);

        ImgDelete=findViewById(R.id.ImgDelete);

        if(user_role.equals("1"))
            ImgDelete.setVisibility(View.VISIBLE);


        //int versionCode = BuildConfig.VERSION_CODE;
        //Toast.makeText(this,String.valueOf(versionCode),Toast.LENGTH_LONG).show();
        //Log.e("version",versionCode);

        title.setText(current_title);
        description.setText(current_description);
        link.setText(current_link);
    }

    public void go_back(View view) {
        super.onBackPressed();
    }

    public void open_link(View view)
    {
        try
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(current_link));
            startActivity(i);
        }
        catch (Exception e)
        {
            Log.e("intent",e.toString());
            Toast.makeText(this,"Invalid Link",Toast.LENGTH_LONG).show();
        }

    }

    public void delete(View view)
    {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.notification_delete))
                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog=new ProgressDialog(ctx);
                        progressDialog.setTitle("Loading...");
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        DeleteNotificationTask deleteNotificationTask=new DeleteNotificationTask(ctx,activity,progressDialog);
                        deleteNotificationTask.execute(user_name,user_email,user_role,id);
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


class DeleteNotificationTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    String user_name,user_email,user_role,id;
    ProgressDialog progressDialog;

    DeleteNotificationTask(Context ctx,Activity activity,ProgressDialog progressDialog)
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
        user_role=params[2];
        id=params[3];


        try {
            URL url=new URL(Constants.base_url+"adminapi/delete_notification");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("id","UTF-8") +"="+URLEncoder.encode(id,"UTF-8");
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
                Toast.makeText(ctx,"Delete Success.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ctx,Dashboard.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_email",user_email);
                intent.putExtra("user_role",user_role);
                intent.putExtra("user_display","0");
                ctx.startActivity(intent);
                activity.finish();
            }
            else
            {
                Toast.makeText(ctx,"Delete Failed ...",Toast.LENGTH_LONG).show();
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

