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

public class EducationDetailsEdit extends AppCompatActivity {

    String user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa,phone;
    EditText Etuser_name,Etuser_usn,Etsslc,Etpuc,Etsem1,Etsem2,Etsem3,Etsem4,Etsem5,Etsem6,Etsem7,Etphone;
    Float Msslc,Mpuc,Msem1,Msem2,Msem3,Msem4,Msem5,Msem6,Msem7=Float.valueOf(0),total,Mcgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_details_edit_layout);

        Bundle bundle=getIntent().getExtras();
        user_email=bundle.getString("user_email");
        user_name=bundle.getString("user_name");
        user_usn=bundle.getString("user_usn");
        sslc=bundle.getString("sslc");
        puc=bundle.getString("puc");
        sem1=bundle.getString("sem1");
        sem2=bundle.getString("sem2");
        sem3=bundle.getString("sem3");
        sem4=bundle.getString("sem4");
        sem5=bundle.getString("sem5");
        sem6=bundle.getString("sem6");
        sem7=bundle.getString("sem7");

        phone=bundle.getString("phone");

        //String received=user_email+"\n"+user_name+"\n"+user_usn+"\n"+sslc+"\n"+puc+"\n"+sem1+"\n"+sem2+"\n"+sem3+"\n"+sem4+"\n"+sem5+"\n"+sem6+"\n"+sem7+"\n"+cgpa;

        //Toast.makeText(this,received,Toast.LENGTH_LONG).show();

        Etuser_name=findViewById(R.id.Etuser_name);
        Etuser_usn=findViewById(R.id.Etuser_usn);
        Etsslc=findViewById(R.id.Etsslc);
        Etpuc=findViewById(R.id.Etpuc);
        Etsem1=findViewById(R.id.Etsem1);
        Etsem2=findViewById(R.id.Etsem2);
        Etsem3=findViewById(R.id.Etsem3);
        Etsem4=findViewById(R.id.Etsem4);
        Etsem5=findViewById(R.id.Etsem5);
        Etsem6=findViewById(R.id.Etsem6);
        Etsem7=findViewById(R.id.Etsem7);
        Etphone=findViewById(R.id.Etphone);



        Etuser_name.setText(user_name);
        Etuser_usn.setText(user_usn);
        Etsslc.setText(sslc);
        Etpuc.setText(puc);
        Etsem1.setText(sem1);
        Etsem2.setText(sem2);
        Etsem3.setText(sem3);
        Etsem4.setText(sem4);
        Etsem5.setText(sem5);
        Etsem6.setText(sem6);
        Etsem7.setText(sem7);
        Etphone.setText(phone);


    }

    public void go_back_to_education_details(View view)
    {
        Intent intent=new Intent(this,EducationDetails.class);
        intent.putExtra("user_email",user_email);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this,EducationDetails.class);
        intent.putExtra("user_email",user_email);
        startActivity(intent);
        finish();
    }

    public void update_marks(View view)
    {
        user_name=Etuser_name.getText().toString();
        user_usn=Etuser_usn.getText().toString();
        phone=Etphone.getText().toString();

        sslc=Etsslc.getText().toString();
        puc=Etpuc.getText().toString();

        sem1=Etsem1.getText().toString();
        sem2=Etsem2.getText().toString();
        sem3=Etsem3.getText().toString();
        sem4=Etsem4.getText().toString();
        sem5=Etsem5.getText().toString();
        sem6=Etsem6.getText().toString();
        sem7=Etsem7.getText().toString();

        if(user_name.equals("") || user_usn.equals("") || sslc.equals("") || puc.equals("") || sem1.equals("") || sem2.equals("") || sem3.equals("") || sem4.equals("") || sem5.equals("") || sem6.equals("") || phone.equals(""))
        {
            Toast.makeText(this,"All Fields Are Mandatory Except Sem 7 CGPA ",Toast.LENGTH_LONG).show();
        }
        else
        {
            Msslc=Float.parseFloat(sslc);
            Mpuc=Float.parseFloat(puc);

            Msem1=Float.parseFloat(sem1);
            Msem2=Float.parseFloat(sem2);
            Msem3=Float.parseFloat(sem3);
            Msem4=Float.parseFloat(sem4);
            Msem5=Float.parseFloat(sem5);
            Msem6=Float.parseFloat(sem6);

            if(!sem7.equals(""))
            {
                Msem7=Float.parseFloat(sem7);
            }

            if(Msslc > 100 || Mpuc > 100)
            {
                Toast.makeText(this,"Invalid SSLC Or Puc Percentage",Toast.LENGTH_LONG).show();
            }
            else if(Msem1 > 10 || Msem2 > 10 || Msem3 > 10 || Msem4 > 10 || Msem5 > 10 || Msem6 > 10 || Msem7 > 10)
            {
                Toast.makeText(this,"Invalid SGPA",Toast.LENGTH_LONG).show();
            }
            else
            {
                if(Msem7.equals(Float.valueOf(0)))
                {
                    total=Msem1+Msem2+Msem3+Msem4+Msem5+Msem6;
                    Mcgpa=total/6;
                }
                else
                {
                    total=Msem1+Msem2+Msem3+Msem4+Msem5+Msem6+Msem7;
                    Mcgpa=total/7;
                }

                sslc=String.format("%.2f",Msslc);
                puc=String.format("%.2f",Mpuc);

                sem1=String.format("%.2f",Msem1);
                sem2=String.format("%.2f",Msem2);
                sem3=String.format("%.2f",Msem3);
                sem4=String.format("%.2f",Msem4);
                sem5=String.format("%.2f",Msem5);
                sem6=String.format("%.2f",Msem6);
                sem7=String.format("%.2f",Msem7);

                cgpa=String.format("%.2f",Mcgpa);

                //Toast.makeText(this,"TOTAL "+total+"\nCGPA "+cgpa,Toast.LENGTH_LONG).show();

                ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("Updating Marks...");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                EducationDetailsEditTask educationDetailsEditTask=new EducationDetailsEditTask(this,this,progressDialog);
                educationDetailsEditTask.execute(user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa,phone);

            }



        }
    }
}


class EducationDetailsEditTask extends AsyncTask<String,String,String>
{
    Context ctx;
    Activity activity;
    String user_email,user_name,user_usn,sslc,puc,sem1,sem2,sem3,sem4,sem5,sem6,sem7,cgpa,phone;
    ProgressDialog progressDialog;

    EducationDetailsEditTask(Context ctx,Activity activity,ProgressDialog progressDialog)
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
        user_name=params[1];
        user_usn=params[2];
        sslc=params[3];
        puc=params[4];
        sem1=params[5];
        sem2=params[6];
        sem3=params[7];
        sem4=params[8];
        sem5=params[9];
        sem6=params[10];
        sem7=params[11];
        cgpa=params[12];
        phone=params[13];

        try {
            URL url=new URL(Constants.base_url+"userapi/update_marks_and_name");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_email","UTF-8") +"="+URLEncoder.encode(user_email,"UTF-8")+"&"+
                    URLEncoder.encode("user_name","UTF-8") +"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                    URLEncoder.encode("user_usn","UTF-8") +"="+URLEncoder.encode(user_usn,"UTF-8")+"&"+
                    URLEncoder.encode("sslc","UTF-8") +"="+URLEncoder.encode(sslc,"UTF-8")+"&"+
                    URLEncoder.encode("puc","UTF-8") +"="+URLEncoder.encode(puc,"UTF-8")+"&"+
                    URLEncoder.encode("sem1","UTF-8") +"="+URLEncoder.encode(sem1,"UTF-8")+"&"+
                    URLEncoder.encode("sem2","UTF-8") +"="+URLEncoder.encode(sem2,"UTF-8")+"&"+
                    URLEncoder.encode("sem3","UTF-8") +"="+URLEncoder.encode(sem3,"UTF-8")+"&"+
                    URLEncoder.encode("sem4","UTF-8") +"="+URLEncoder.encode(sem4,"UTF-8")+"&"+
                    URLEncoder.encode("sem5","UTF-8") +"="+URLEncoder.encode(sem5,"UTF-8")+"&"+
                    URLEncoder.encode("sem6","UTF-8") +"="+URLEncoder.encode(sem6,"UTF-8")+"&"+
                    URLEncoder.encode("sem7","UTF-8") +"="+URLEncoder.encode(sem7,"UTF-8")+"&"+
                    URLEncoder.encode("cgpa","UTF-8") +"="+URLEncoder.encode(cgpa,"UTF-8")+"&"+
                    URLEncoder.encode("phone","UTF-8") +"="+URLEncoder.encode(phone,"UTF-8");
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
                Toast.makeText(ctx,"Details Update Success.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ctx,EducationDetails.class);
                intent.putExtra("user_email",user_email);
                ctx.startActivity(intent);
                activity.finish();
            }
            else
            {
                Toast.makeText(ctx,"Details Update Failed ...",Toast.LENGTH_LONG).show();
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

