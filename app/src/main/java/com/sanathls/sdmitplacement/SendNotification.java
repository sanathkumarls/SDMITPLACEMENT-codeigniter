package com.sanathls.sdmitplacement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendNotification extends AppCompatActivity {

    EditText title_send,message,link;
    String title,descrition,web_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_notification_layout);

        title_send=findViewById(R.id.title_send);
        message=findViewById(R.id.message);
        link=findViewById(R.id.link);
    }

    public void go_back_to_dashboard(View view)
    {
        finish();
    }

    public void send_notification(View view)
    {
        title=title_send.getText().toString();
        descrition=message.getText().toString();
        web_link=link.getText().toString();

        Toast.makeText(this,title+"\n"+descrition+"\n"+web_link,Toast.LENGTH_LONG).show();
    }
}
