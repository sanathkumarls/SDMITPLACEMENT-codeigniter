package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ViewUsers extends AppCompatActivity {

    String admin_email,sslc,sslc_score="null",puc,puc_score="null",cgpa,cgpa_score="null";
    ListView listView;
    ProgressDialog progressDialog;
    TextView tvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_users_layout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle bundle=getIntent().getExtras();
        admin_email=bundle.getString("admin_email");
        sslc=bundle.getString("sslc");
        if(sslc.equals("yes"))
        {
            sslc_score=bundle.getString("sslc_score");
        }
        puc=bundle.getString("puc");
        if(puc.equals("yes"))
        {
            puc_score=bundle.getString("puc_score");
        }
        cgpa=bundle.getString("cgpa");
        if(cgpa.equals("yes"))
        {
            cgpa_score=bundle.getString("cgpa_score");
        }


        //Toast.makeText(this,"mail : "+admin_email+"\nsslc : "+sslc+" sslc score : "+sslc_score+"\npuc : "+puc+" puc score : "+puc_score+"\ncgpa : "+cgpa+" cgpa score : "+cgpa_score,Toast.LENGTH_LONG).show();

        listView=(ListView) findViewById(R.id.list_view_users);
        tvUsers=findViewById(R.id.tvUsers);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ViewUsersTask viewUsersTask=new ViewUsersTask(this,this,listView,progressDialog,tvUsers);
        viewUsersTask.execute(admin_email,sslc,sslc_score,puc,puc_score,cgpa,cgpa_score);

    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }

    public void export(View view)
    {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Exporting...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ExportTask exportTask=new ExportTask(this,this,progressDialog);
        exportTask.execute(admin_email,sslc,sslc_score,puc,puc_score,cgpa,cgpa_score);
    }
}


class ViewUsersTask extends AsyncTask<String,String,String>
{

    Context ctx;
    Activity activity;
    ListView listView;
    TextView tvUsers;
    ProgressDialog progressDialog;

    ViewUsersTask(Context ctx,Activity activity,ListView listView,ProgressDialog progressDialog,TextView tvUsers)
    {
        this.ctx=ctx;
        this.activity=activity;
        this.listView=listView;
        this.progressDialog=progressDialog;
        this.tvUsers=tvUsers;
    }



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String admin_email=params[0];
        String sslc=params[1];
        String sslc_score=params[2];
        String puc=params[3];
        String puc_score=params[4];
        String cgpa=params[5];
        String cgpa_score=params[6];


        try {
            URL url=new URL(Constants.base_url+"adminapi/filter_users");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("admin_email","UTF-8") +"="+URLEncoder.encode(admin_email,"UTF-8")+"&"+
                    URLEncoder.encode("sslc","UTF-8") +"="+URLEncoder.encode(sslc,"UTF-8")+"&"+
                    URLEncoder.encode("sslc_score","UTF-8") +"="+URLEncoder.encode(sslc_score,"UTF-8")+"&"+
                    URLEncoder.encode("puc","UTF-8") +"="+URLEncoder.encode(puc,"UTF-8")+"&"+
                    URLEncoder.encode("puc_score","UTF-8") +"="+URLEncoder.encode(puc_score,"UTF-8")+"&"+
                    URLEncoder.encode("cgpa","UTF-8") +"="+URLEncoder.encode(cgpa,"UTF-8")+"&"+
                    URLEncoder.encode("cgpa_score","UTF-8") +"="+URLEncoder.encode(cgpa_score,"UTF-8");
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
                //Toast.makeText(this,"Login Success.",Toast.LENGTH_SHORT).show();
                String arraysize=jsonObject.getString("size");
                int size=Integer.parseInt(arraysize);

                tvUsers.setText("Users ( "+size+" )");

                if(size == 0)
                {
                    Toast.makeText(ctx,"No Users Available",Toast.LENGTH_LONG).show();
                }

                String[] user_name=new String[size],user_email = new String[size],user_usn = new String[size],user_phone=new String[size],view=new String[size];
                String[] user_device=new String[size],sslc=new String[size],puc=new String[size],sem1=new String[size],sem2=new String[size];
                String[] sem3=new String[size],sem4=new String[size],sem5=new String[size],sem6=new String[size],sem7=new String[size],cgpa=new String[size];

                for(int i=0;i<size;i++)
                {
                    user_name[i]=jsonObject.getString("user_name"+i);
                    user_email[i]=jsonObject.getString("user_email"+i);
                    user_usn[i]=jsonObject.getString("user_usn"+i);
                    user_phone[i]=jsonObject.getString("user_phone"+i);
                    user_device[i]=jsonObject.getString("user_device"+i);
                    sslc[i]=jsonObject.getString("sslc"+i);
                    puc[i]=jsonObject.getString("puc"+i);
                    sem1[i]=jsonObject.getString("sem1"+i);
                    sem2[i]=jsonObject.getString("sem2"+i);
                    sem3[i]=jsonObject.getString("sem3"+i);
                    sem4[i]=jsonObject.getString("sem4"+i);
                    sem5[i]=jsonObject.getString("sem5"+i);
                    sem6[i]=jsonObject.getString("sem6"+i);
                    sem7[i]=jsonObject.getString("sem7"+i);
                    cgpa[i]=jsonObject.getString("cgpa"+i);
                    view[i]=user_name[i]+" ( "+user_usn[i]+" )\nSSLC : "+sslc[i]+"%  PUC : "+puc[i]+"%  CGPA : "+cgpa[i];
                }

                final ArrayAdapter<String> view_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,view);
                listView.setAdapter(view_adapter);

                final ArrayAdapter<String> user_name_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,user_name);

                final ArrayAdapter<String> user_email_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,user_email);

                final ArrayAdapter<String> user_usn_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,user_usn);

                final ArrayAdapter<String> user_phone_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,user_phone);

                final ArrayAdapter<String> user_device_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,user_device);

                final ArrayAdapter<String> sslc_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sslc);

                final ArrayAdapter<String> puc_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,puc);

                final ArrayAdapter<String> sem1_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem1);

                final ArrayAdapter<String> sem2_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem2);

                final ArrayAdapter<String> sem3_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem3);

                final ArrayAdapter<String> sem4_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem4);

                final ArrayAdapter<String> sem5_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem5);

                final ArrayAdapter<String> sem6_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem6);

                final ArrayAdapter<String> sem7_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,sem7);

                final ArrayAdapter<String> cgpa_adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,cgpa);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String current_user_name=user_name_adapter.getItem(position);
                        String current_user_email=user_email_adapter.getItem(position);
                        String current_user_usn=user_usn_adapter.getItem(position);
                        String current_user_phone=user_phone_adapter.getItem(position);
                        String current_user_device=user_device_adapter.getItem(position);
                        String current_sslc=sslc_adapter.getItem(position);
                        String current_puc=puc_adapter.getItem(position);
                        String current_sem1=sem1_adapter.getItem(position);
                        String current_sem2=sem2_adapter.getItem(position);
                        String current_sem3=sem3_adapter.getItem(position);
                        String current_sem4=sem4_adapter.getItem(position);
                        String current_sem5=sem5_adapter.getItem(position);
                        String current_sem6=sem6_adapter.getItem(position);
                        String current_sem7=sem7_adapter.getItem(position);
                        String current_cgpa=cgpa_adapter.getItem(position);

                        //Toast.makeText(ctx,current_description+"\n"+current_link,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ctx, ViewUsersFull.class);
                        intent.putExtra("current_user_name",current_user_name);
                        intent.putExtra("current_user_email",current_user_email);
                        intent.putExtra("current_user_usn",current_user_usn);
                        intent.putExtra("current_user_phone",current_user_phone);
                        intent.putExtra("current_user_device",current_user_device);
                        intent.putExtra("current_sslc",current_sslc);
                        intent.putExtra("current_puc",current_puc);
                        intent.putExtra("current_sem1",current_sem1);
                        intent.putExtra("current_sem2",current_sem2);
                        intent.putExtra("current_sem3",current_sem3);
                        intent.putExtra("current_sem4",current_sem4);
                        intent.putExtra("current_sem5",current_sem5);
                        intent.putExtra("current_sem6",current_sem6);
                        intent.putExtra("current_sem7",current_sem7);
                        intent.putExtra("current_cgpa",current_cgpa);
                        ctx.startActivity(intent);


                    }
                });


            }
            else
            {
                Toast.makeText(ctx,"No Users",Toast.LENGTH_LONG).show();
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



class ExportTask extends AsyncTask<String,String,String> {

    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;

    ExportTask(Context ctx, Activity activity, ProgressDialog progressDialog) {
        this.ctx = ctx;
        this.activity = activity;
        this.progressDialog = progressDialog;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String admin_email = params[0];
        String sslc = params[1];
        String sslc_score = params[2];
        String puc = params[3];
        String puc_score = params[4];
        String cgpa = params[5];
        String cgpa_score = params[6];


        try {
            URL url = new URL(Constants.base_url + "adminapi/filter_users");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String data = URLEncoder.encode("admin_email", "UTF-8") + "=" + URLEncoder.encode(admin_email, "UTF-8") + "&" +
                    URLEncoder.encode("sslc", "UTF-8") + "=" + URLEncoder.encode(sslc, "UTF-8") + "&" +
                    URLEncoder.encode("sslc_score", "UTF-8") + "=" + URLEncoder.encode(sslc_score, "UTF-8") + "&" +
                    URLEncoder.encode("puc", "UTF-8") + "=" + URLEncoder.encode(puc, "UTF-8") + "&" +
                    URLEncoder.encode("puc_score", "UTF-8") + "=" + URLEncoder.encode(puc_score, "UTF-8") + "&" +
                    URLEncoder.encode("cgpa", "UTF-8") + "=" + URLEncoder.encode(cgpa, "UTF-8") + "&" +
                    URLEncoder.encode("cgpa_score", "UTF-8") + "=" + URLEncoder.encode(cgpa_score, "UTF-8");
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
                //Toast.makeText(this,"Login Success.",Toast.LENGTH_SHORT).show();
                String arraysize = jsonObject.getString("size");
                int size = Integer.parseInt(arraysize);

                if (size == 0)
                {
                    Toast.makeText(ctx, "No Users Available", Toast.LENGTH_LONG).show();
                }

                String[] user_name = new String[size], user_email = new String[size], user_usn = new String[size], user_phone = new String[size];
                String[] sslc = new String[size], puc = new String[size], sem1 = new String[size], sem2 = new String[size];
                String[] sem3 = new String[size], sem4 = new String[size], sem5 = new String[size], sem6 = new String[size], sem7 = new String[size], cgpa = new String[size];

                for (int i = 0; i < size; i++)
                {
                    user_name[i] = jsonObject.getString("user_name" + i);
                    user_email[i] = jsonObject.getString("user_email" + i);
                    user_usn[i] = jsonObject.getString("user_usn" + i);
                    user_phone[i] = jsonObject.getString("user_phone" + i);
                    sslc[i] = jsonObject.getString("sslc" + i);
                    puc[i] = jsonObject.getString("puc" + i);
                    sem1[i] = jsonObject.getString("sem1" + i);
                    sem2[i] = jsonObject.getString("sem2" + i);
                    sem3[i] = jsonObject.getString("sem3" + i);
                    sem4[i] = jsonObject.getString("sem4" + i);
                    sem5[i] = jsonObject.getString("sem5" + i);
                    sem6[i] = jsonObject.getString("sem6" + i);
                    sem7[i] = jsonObject.getString("sem7" + i);
                    cgpa[i] = jsonObject.getString("cgpa" + i);
                }

                if(size > 0)
                {
                    StringBuilder data =new StringBuilder();
                    data.append("Sl. No,Name,Email,USN,Phone,SSLC,PUC,SEM 1,SEM 2,SEM 3,SEM 4,SEM 5,SEM 6,SEM 7,CGPA");
                    for(int i=0;i<user_name.length;i++)
                    {
                        data.append(
                                "\n"+
                                        (i+1)+","+
                                        user_name[i]+","+
                                        user_email[i]+","+
                                        user_usn[i]+","+
                                        user_phone[i]+","+
                                        sslc[i]+","+
                                        puc[i]+","+
                                        sem1[i]+","+
                                        sem2[i]+","+
                                        sem3[i]+","+
                                        sem4[i]+","+
                                        sem5[i]+","+
                                        sem6[i]+","+
                                        sem7[i]+","+
                                        cgpa[i]
                        );
                    }
                    try
                    {
                        FileOutputStream out=ctx.openFileOutput("data.csv", Context.MODE_PRIVATE);
                        out.write(data.toString().getBytes());
                        out.close();

                        //export


                        File fileLocation=new File(ctx.getFilesDir(),"data.csv");
                        Uri path= FileProvider.getUriForFile(ctx,"com.sanathls.sdmitplacement.fileprovider",fileLocation);
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/csv");
                        intent.putExtra(Intent.EXTRA_SUBJECT,"Users");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_STREAM,path);
                        ctx.startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            } else {
                Toast.makeText(ctx, "No Users", Toast.LENGTH_LONG).show();
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
