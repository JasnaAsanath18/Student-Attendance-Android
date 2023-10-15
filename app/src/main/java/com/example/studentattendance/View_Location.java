package com.example.studentattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_Location extends AppCompatActivity implements JsonResponse{
    SharedPreferences sh;
    String longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);



        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)View_Location.this;
        String q="/view_student_location?login_id=" +sh.getString("log_id","") ;
        q = q.replace(" ", "%20");
        JR.execute(q);


    }



    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("check");
                latitude = ja1.getJSONObject(0).getString("latitude");
                longitude = ja1.getJSONObject(0).getString("longitude");

                String url = "http://www.google.com/maps?q=" + latitude + "," + longitude;
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);
            }
            else {
                Toast.makeText(getApplicationContext(), "Sorry No data Avilable", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ParentHome.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }



    }
}