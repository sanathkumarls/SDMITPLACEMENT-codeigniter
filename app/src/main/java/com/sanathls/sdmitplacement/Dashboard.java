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
import android.view.Menu;
import android.view.MenuInflater;
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
    String user_name,user_email,user_role;
    ListView listView;
    Context ctx;
    Activity activity;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ctx=this;
        activity=this;

        Bundle bundle=getIntent().getExtras();
        user_name=bundle.getString("user_name");
        user_email=bundle.getString("user_email");
        user_role=bundle.getString("user_role");


        //        sendNotification= menu.findItem(R.id.send_notification);
//        if(user_role.equals("1"))
//        {
//            sendNotification.setVisible(true);
//        }



        Toast.makeText(this,"Welcome "+user_name,Toast.LENGTH_SHORT).show();

        listView=(ListView) findViewById(R.id.list_view);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        if(Internet.hasInternetAccess(this))
//        {
            NotificationsTask notifications=new NotificationsTask(this,this,listView,progressDialog);
            notifications.execute(user_email);
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
//                    dialog.dismiss();
//                    finishAffinity();
//                }
//            });
//            alertDialog.show();
//        }







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


        if(user_role.equals("1"))
        {
            menu=navigationView.getMenu();
            if(menu!= null)
            {
                menu.findItem(R.id.send_notification).setVisible(true);
                menu.findItem(R.id.education_details).setVisible(false);
            }
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
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

        if (id == R.id.nav_home)
        {
            progressDialog.show();

//            if(Internet.hasInternetAccess(this))
//            {
                NotificationsTask notifications=new NotificationsTask(this,this,listView,progressDialog);
                notifications.execute(user_email);
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
        else if (id == R.id.change_password)
        {
            Intent intent=new Intent(this,ChangePassword.class);
            intent.putExtra("user_email",user_email);
            startActivity(intent);
        }
        else if (id == R.id.share)
        {
            try {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hi! I have had a great experience with SDMIT Placement and highly recommend that you should try this app" + "\n" + " Use App link: https://sdmitcseplacement.cf" );
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"Opps!! It seems that you have not installed any sharing app.",Toast.LENGTH_LONG).show();
            }
        }
        else if (id == R.id.logout)
        {
            progressDialog.cancel();
           final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            //Setting Dialog Title
            alertDialog.setTitle("No Connection !!!");
            //Setting Dialog Icon
            alertDialog.setIcon(R.mipmap.ic_launcher);
            //Setting Dialog Message
            alertDialog.setMessage("Check Your Internet Connection And Try Again ...");

            alertDialog.setCancelable(false);

            //On Pressing Setting button
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finishAffinity();
                }
            });

            try {
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.logout_msg))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
//                                if(Internet.hasInternetAccess(ctx))
//                                {
                                    LogoutTask logoutTask=new LogoutTask(ctx,activity,progressDialog);
                                    logoutTask.execute(user_email);
//                                }
//                                else
//                                {
//
//                                    alertDialog.show();
//                                }
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if(id == R.id.send_notification)
        {
            //Toast.makeText(this,"send ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,SendNotification.class);
            intent.putExtra("user_email",user_email);
            startActivity(intent);
        }
        else if(id == R.id.education_details)
        {
            //Toast.makeText(this,"education ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,EducationDetails.class);
            intent.putExtra("user_email",user_email);
            startActivity(intent);
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

