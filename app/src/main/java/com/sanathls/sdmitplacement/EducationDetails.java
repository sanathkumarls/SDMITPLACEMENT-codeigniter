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
import android.widget.TextView;
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

public class EducationDetails extends AppCompatActivity {

    TextView tvEmail,tvNameValue,tvUsnValue,tvSslcValue,tvPucValue;
    TextView tvSem1Value,tvSem2Value,tvSem3Value,tvSem4Value,tvSem5Value,tvSem6Value,tvSem7Value;
    TextView tvCgpaValue;

    String user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa;
    String values[]=new String[]{user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_layout);

        Bundle bundle=getIntent().getExtras();
        user_email=bundle.getString("user_email");

        tvEmail=findViewById(R.id.tvEmail);
        tvNameValue=findViewById(R.id.tvNameValue);
        tvUsnValue=findViewById(R.id.tvUsnValue);
        tvSslcValue=findViewById(R.id.tvSslcValue);
        tvPucValue=findViewById(R.id.tvPucValue);


        tvSem1Value=findViewById(R.id.tvSem1Value);
        tvSem2Value=findViewById(R.id.tvSem2Value);
        tvSem3Value=findViewById(R.id.tvSem3Value);
        tvSem4Value=findViewById(R.id.tvSem4Value);
        tvSem5Value=findViewById(R.id.tvSem5Value);
        tvSem6Value=findViewById(R.id.tvSem6Value);
        tvSem7Value=findViewById(R.id.tvSem7Value);


        tvCgpaValue=findViewById(R.id.tvCgpaValue);


        TextView textViews[]=new TextView[13];

        textViews[0]=tvEmail;
        textViews[1]=tvNameValue;
        textViews[2]=tvUsnValue;
        textViews[3]=tvSslcValue;
        textViews[4]=tvPucValue;


        textViews[5]=tvSem1Value;
        textViews[6]=tvSem2Value;
        textViews[7]=tvSem3Value;
        textViews[8]=tvSem4Value;
        textViews[9]=tvSem5Value;
        textViews[10]=tvSem6Value;
        textViews[11]=tvSem7Value;


        textViews[12]=tvCgpaValue;

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();



        EducationDetailsTask educationDetailsTask=new EducationDetailsTask(this,this,progressDialog,textViews,values);
        educationDetailsTask.execute(user_email);

    }

    public void go_back_to_login(View view)
    {
        finish();
    }

    public void edit(View view) {
        Intent i=new Intent(this,EducationDetailsEdit.class);
        i.putExtra("user_email",user_email);
        i.putExtra("user_name",values[1]);
        i.putExtra("user_usn",values[2]);
        i.putExtra("sslc",values[3]);
        i.putExtra("puc",values[4]);
        i.putExtra("sem1",values[5]);
        i.putExtra("sem2",values[6]);
        i.putExtra("sem3",values[7]);
        i.putExtra("sem4",values[8]);
        i.putExtra("sem5",values[9]);
        i.putExtra("sem6",values[10]);
        i.putExtra("sem7",values[11]);
        i.putExtra("cgpa",values[12]);
        startActivity(i);
        finish();
    }
}

class EducationDetailsTask extends AsyncTask<String,String,String>
{

    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;
    TextView[] textViews;
    String[] values;

    EducationDetailsTask(Context ctx,Activity activity,ProgressDialog progressDialog,TextView[] textViews,String[] values)
    {
        this.ctx=ctx;
        this.activity=activity;
        this.progressDialog=progressDialog;
        this.textViews=textViews;
        this.values=values;
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {

        String user_email=params[0];


        try {
            URL url=new URL(Constants.base_url+"userapi/get_marks");
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
                //Toast.makeText(ctx,"Success.",Toast.LENGTH_SHORT).show();
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

                String sslc=jsonDataObject.getString("sslc");
                String puc=jsonDataObject.getString("puc");
                String sem1=jsonDataObject.getString("sem1");
                String sem2=jsonDataObject.getString("sem2");
                String sem3=jsonDataObject.getString("sem3");
                String sem4=jsonDataObject.getString("sem4");
                String sem5=jsonDataObject.getString("sem5");
                String sem6=jsonDataObject.getString("sem6");
                String sem7=jsonDataObject.getString("sem7");
                String cgpa=jsonDataObject.getString("cgpa");
                String last_updated=jsonDataObject.getString("updated_at");


                values[0]=user_email;
                values[1]=user_name;
                values[2]=user_usn;
                values[3]=sslc;
                values[4]=puc;
                values[5]=sem1;
                values[6]=sem2;
                values[7]=sem3;
                values[8]=sem4;
                values[9]=sem5;
                values[10]=sem6;
                values[11]=sem7;
                values[12]=cgpa;


                for (int i=0;i<textViews.length;i++)
                {
                    textViews[i].setText(values[i]);
                }

            }
            else
            {
                Toast.makeText(ctx,"Failed ...",Toast.LENGTH_LONG).show();
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

