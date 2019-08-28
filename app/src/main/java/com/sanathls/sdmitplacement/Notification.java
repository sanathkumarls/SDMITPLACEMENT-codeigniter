package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Notification extends AppCompatActivity {

    TextView title,description,link;
    String current_title,current_description,current_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        Bundle bundle=getIntent().getExtras();
        current_title=bundle.getString("current_title");
        current_description=bundle.getString("current_description");
        current_link=bundle.getString("current_link");

        setTitle(R.string.app_name);

        title=findViewById(R.id.tv_title);
        description=findViewById(R.id.tv_description);
        link=findViewById(R.id.tv_link);


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
}
