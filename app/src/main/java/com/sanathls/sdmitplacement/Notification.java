package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    TextView title,description,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        Bundle bundle=getIntent().getExtras();
        String current_title=bundle.getString("current_title");
        String current_description=bundle.getString("current_description");
        String current_link=bundle.getString("current_link");

        setTitle(R.string.app_name);

        title=findViewById(R.id.tv_title);
        description=findViewById(R.id.tv_description);
        link=findViewById(R.id.tv_link);

        title.setText(current_title);
        description.setText(current_description);
        link.setText(current_link);
    }

    public void go_back(View view) {
        super.onBackPressed();
    }
}
