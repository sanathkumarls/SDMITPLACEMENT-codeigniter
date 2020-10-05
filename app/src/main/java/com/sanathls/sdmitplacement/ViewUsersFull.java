package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewUsersFull extends AppCompatActivity {

    String current_user_name,current_user_email,current_user_usn,current_user_phone,current_user_device,current_sslc,current_puc;
    String current_sem1,current_sem2,current_sem3,current_sem4,current_sem5,current_sem6,current_sem7,current_cgpa;

    ImageView iv_user_download;
    TextView tvUser_name,tvUser_email,tvUser_usn,tvUser_phone,tvUser_device,tvUser_sslc,tvUser_puc,tvUser_sem1,tvUser_sem2,tvUser_sem3,tvUser_sem4,tvUser_sem5,tvUser_sem6,tvUser_sem7,tvUser_cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_users_full_layout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle bundle=getIntent().getExtras();
        current_user_name=bundle.getString("current_user_name");
        current_user_email=bundle.getString("current_user_email");
        current_user_usn=bundle.getString("current_user_usn");
        current_user_phone=bundle.getString("current_user_phone");
        current_user_device=bundle.getString("current_user_device");
        current_sslc=bundle.getString("current_sslc");
        current_puc=bundle.getString("current_puc");
        current_sem1=bundle.getString("current_sem1");
        current_sem2=bundle.getString("current_sem2");
        current_sem3=bundle.getString("current_sem3");
        current_sem4=bundle.getString("current_sem4");
        current_sem5=bundle.getString("current_sem5");
        current_sem6=bundle.getString("current_sem6");
        current_sem7=bundle.getString("current_sem7");
        current_cgpa=bundle.getString("current_cgpa");


        tvUser_name=findViewById(R.id.tvUser_name);
        tvUser_email=findViewById(R.id.tvUser_email);
        tvUser_usn=findViewById(R.id.tvUser_usn);
        tvUser_phone=findViewById(R.id.tvUser_phone);
        tvUser_device=findViewById(R.id.tvUser_device);
        tvUser_sslc=findViewById(R.id.tvUser_sslc);
        tvUser_puc=findViewById(R.id.tvUser_puc);
        tvUser_sem1=findViewById(R.id.tvUser_sem1);
        tvUser_sem1=findViewById(R.id.tvUser_sem1);
        tvUser_sem2=findViewById(R.id.tvUser_sem2);
        tvUser_sem3=findViewById(R.id.tvUser_sem3);
        tvUser_sem4=findViewById(R.id.tvUser_sem4);
        tvUser_sem5=findViewById(R.id.tvUser_sem5);
        tvUser_sem6=findViewById(R.id.tvUser_sem6);
        tvUser_sem7=findViewById(R.id.tvUser_sem7);
        tvUser_cgpa=findViewById(R.id.tvUser_cgpa);
        iv_user_download = findViewById(R.id.ivUserDownload);


        tvUser_name.setText(current_user_name);
        tvUser_email.setText(current_user_email);
        tvUser_usn.setText(current_user_usn);
        tvUser_phone.setText(current_user_phone);
        tvUser_device.setText(current_user_device);
        tvUser_sslc.setText(current_sslc);
        tvUser_puc.setText(current_puc);
        tvUser_sem1.setText(current_sem1);
        tvUser_sem2.setText(current_sem2);
        tvUser_sem3.setText(current_sem3);
        tvUser_sem4.setText(current_sem4);
        tvUser_sem5.setText(current_sem5);
        tvUser_sem6.setText(current_sem6);
        tvUser_sem7.setText(current_sem7);
        tvUser_cgpa.setText(current_cgpa);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait.. Profile photo is loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        GetImageTask getImageTask = new GetImageTask(this,this,iv_user_download,progressDialog);
        getImageTask.execute(current_user_email);

    }

    public void go_back(View view)
    {
        finish();
    }

    public void download_image(View view) {


        String appName = getString(R.string.app_name);
        String filepath = this.getExternalFilesDir("").getAbsolutePath();
        String extraPortion = "Android/data/" + BuildConfig.APPLICATION_ID
                + File.separator + "files";
        // Remove extraPortion
        filepath = filepath.replace(extraPortion, "");
        Log.e("path",filepath);
        File dir = new File(filepath+appName+"/");
        Log.e("final path",dir.toString());
        dir.mkdir();

        File file = new File(dir,current_user_usn+"-"+System.currentTimeMillis()+".jpg");
        FileOutputStream outputStream;
        try {
            iv_user_download.buildDrawingCache();
            Bitmap bitmap = iv_user_download.getDrawingCache();
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            try {
                outputStream.flush();
                Toast.makeText(this,"Photo Stored In "+file.toString(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e("IOException",e.toString());
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                Log.e("IOException",e.toString());
            }
        } catch (Exception e) {
            Log.e("Exception",e.toString());
        }
    }

    public void edit(View view)
    {
        Intent i=new Intent(this,EducationDetailsEdit.class);
        i.putExtra("user_email",current_user_email);
        i.putExtra("user_name",current_user_name);
        i.putExtra("user_usn",current_user_usn);
        i.putExtra("sslc",current_sslc);
        i.putExtra("puc",current_puc);
        i.putExtra("sem1",current_sem1);
        i.putExtra("sem2",current_sem2);
        i.putExtra("sem3",current_sem3);
        i.putExtra("sem4",current_sem4);
        i.putExtra("sem5",current_sem5);
        i.putExtra("sem6",current_sem6);
        i.putExtra("sem7",current_sem7);
        i.putExtra("cgpa",current_cgpa);
        i.putExtra("phone",current_user_phone);
        i.putExtra("user_role","1");
        startActivity(i);
        finish();
    }
}
