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

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tv_name,tv_email;
    ProgressDialog progressDialog;
    String user_name,user_email;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle=getIntent().getExtras();
        user_name=bundle.getString("user_name");
        user_email=bundle.getString("user_email");

        Toast.makeText(this,"Welcome "+user_name,Toast.LENGTH_SHORT).show();

        listView=(ListView) findViewById(R.id.list_view);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        if(Internet.hasInternetAccess(this))
        {
            NotificationsTask notifications=new NotificationsTask(this,this,listView,progressDialog);
            notifications.execute(user_email);
        }
        else
        {
            progressDialog.cancel();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            //Setting Dialog Title
            alertDialog.setTitle("No Connection !!!");
            //Setting Dialog Icon
            alertDialog.setIcon(R.mipmap.ic_launcher);
            //Setting Dialog Message
            alertDialog.setMessage("Check Your Internet Connection And Try Again ...");

            //On Pressing Setting button
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finishAffinity();
                }
            });
            alertDialog.show();
        }







        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
       tv_name = (TextView) header.findViewById(R.id.tv_user_name);
        tv_email = (TextView) header.findViewById(R.id.tv_user_email);

        tv_name.setText(user_name);
        tv_email.setText(user_email);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }  else if (id == R.id.change_password) {

        } else if (id == R.id.share) {

        } else if (id == R.id.logout) {


            if(Internet.hasInternetAccess(this))
            {
                LogoutTask logoutTask=new LogoutTask(this,this,progressDialog);
                logoutTask.execute(user_email);
            }
            else
            {
                progressDialog.cancel();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                //Setting Dialog Title
                alertDialog.setTitle("No Connection !!!");
                //Setting Dialog Icon
                alertDialog.setIcon(R.mipmap.ic_launcher);
                //Setting Dialog Message
                alertDialog.setMessage("Check Your Internet Connection And Try Again ...");

                //On Pressing Setting button
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });
                alertDialog.show();
            }




        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class NotificationsTask extends AsyncTask<String,String,String>
{

    Context ctx;
    Activity activity;
    ListView listView;
    ProgressDialog progressDialog;

    NotificationsTask(Context ctx,Activity activity,ListView listView,ProgressDialog progressDialog)
    {
        this.ctx=ctx;
        this.activity=activity;
        this.listView=listView;
        this.progressDialog=progressDialog;
    }



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String user_email=params[0];


        try {
            URL url=new URL(Constants.base_url+"userapi/notifications");
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
        if(response.equals("offline"))
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
            //Setting Dialog Title
            alertDialog.setTitle("Cannot Connect To Server !!!");
            //Setting Dialog Icon
            alertDialog.setIcon(R.mipmap.ic_launcher);
            //Setting Dialog Message
            alertDialog.setMessage("Check Your Internet Connection Or Try Again Later ...");

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
        else
        {
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

                    String[] title=new String[size],description = new String[size],link = new String[size];

                    for(int i=0;i<size;i++)
                    {
                        title[i]=jsonObject.getString("title"+i);
                        description[i]=jsonObject.getString("description"+i);
                        link[i]=jsonObject.getString("link"+i);
                    }

                    final ArrayAdapter<String> titleadapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,title);
                    listView.setAdapter(titleadapter);

                    final ArrayAdapter<String> descriptionadapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,description);

                    final ArrayAdapter<String> linkadapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,android.R.id.text1,link);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String current_title=titleadapter.getItem(position);
                            String current_description=descriptionadapter.getItem(position);
                            String current_link=linkadapter.getItem(position);
                            //Toast.makeText(ctx,current_description+"\n"+current_link,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ctx, Notification.class);
                            intent.putExtra("current_title",current_title);
                            intent.putExtra("current_description",current_description);
                            intent.putExtra("current_link",current_link);
                            ctx.startActivity(intent);


                        }
                    });


                }
                else
                {
                    Toast.makeText(ctx,"No Notifications",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Toast.makeText(ctx,"No Notifications",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}

class LogoutTask extends AsyncTask<String,String,String>
{

    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;

    LogoutTask(Context ctx,Activity activity,ProgressDialog progressDialog)
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

        String user_email=params[0];


        try {
            URL url=new URL(Constants.base_url+"userapi/logout");
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
        if(response.equals("offline"))
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
            //Setting Dialog Title
            alertDialog.setTitle("Cannot Connect To Server !!!");
            //Setting Dialog Icon
            alertDialog.setIcon(R.mipmap.ic_launcher);
            //Setting Dialog Message
            alertDialog.setMessage("Check Your Internet Connection Or Try Again Later ...");

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
        else
        {
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
                    Toast.makeText(ctx,"Logout Successful.",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ctx,Login.class);
                    ctx.startActivity(intent);
                    activity.finish();

                }
                else
                {
                    Toast.makeText(ctx,"Logout Failed",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Toast.makeText(ctx,"Logout Failed",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}

