package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewUsersFull extends AppCompatActivity {

    String current_user_name,current_user_email,current_user_usn,current_user_phone,current_user_device,current_sslc,current_puc;
    String current_sem1,current_sem2,current_sem3,current_sem4,current_sem5,current_sem6,current_sem7,current_cgpa;

    TextView tvUser_name,tvUser_email,tvUser_usn,tvUser_phone,tvUser_device,tvUser_sslc,tvUser_puc,tvUser_sem1,tvUser_sem2,tvUser_sem3,tvUser_sem4,tvUser_sem5,tvUser_sem6,tvUser_sem7,tvUser_cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_users_full_layout);


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

    }

    public void go_back(View view)
    {
        finish();
    }
}
