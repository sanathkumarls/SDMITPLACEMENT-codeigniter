package com.sanathls.sdmitplacement;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseService extends FirebaseMessagingService
{

    String title,message;

    @Override
    public void onNewToken(String token) {
        Log.d("token", "Refreshed token: " + token);



        try {
            URL url=new URL(Constants.base_url+"userapi/firebase.php");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os=con.getOutputStream();
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data= URLEncoder.encode("user_token","UTF-8") +"="+URLEncoder.encode(token,"UTF-8");
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
            Log.e("token response",response);

        } catch (MalformedURLException e) {
            Log.e("malformedurl",e.toString());
        } catch (IOException e) {
            Log.e("ioexcetion",e.toString());
        }

    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            // Log.e("amith", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        if (remoteMessage.getData() != null) {
            //Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage);
        }


    }



    private void sendNotification(RemoteMessage messageBody) {


        try
        {
            Map<String, String> params = messageBody.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON OBJECT", object.toString());
            title = object.getString("title");
            message =object.getString("message");
            //rest of the code
        }catch (JSONException e)
        {

        }



        Intent intent = new Intent(this, Splash.class);
        // Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);

        intent.putExtra("screenName", "Notifiy");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.bigText(message);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"fcm_default_channel");
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setStyle(bigTextStyle);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {
            notificationBuilder.setColor(Color.WHITE);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));


        }



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationBuilder.setChannelId("my_channel_01");
            CharSequence name = "My New Channel";                   // The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("my_channel_01",name, importance); //Create Notification Channel
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(0/* ID of notification */, notificationBuilder.build());
    }

}