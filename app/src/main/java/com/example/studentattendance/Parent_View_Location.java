package com.example.studentattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parent_View_Location extends AppCompatActivity implements  JsonResponse{
    SharedPreferences sh;
    String student_id,latitude,longitude,date,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_location);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String uid=sh.getString("parent_id", "");


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Parent_View_Location.this;
        String q="/parent_view_location?parent_id=" + uid ;
        q = q.replace(" ", "%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            Log.d("pearl", status);


            if(status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("check");
//                attendance_id = new String[ja1.length()];




                    student_id= ja1.getJSONObject(0).getString("student_id");
                    latitude=ja1.getJSONObject(0).getString("latitude");
                    longitude=ja1.getJSONObject(0).getString("longitude");
                    date=ja1.getJSONObject(0).getString("date");

                String url = "http://www.google.com/maps?q=" + latitude + "," + longitude;
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);


//                    Toast.makeText(getApplicationContext(), details[i], Toast.LENGTH_LONG).show();


//                ArrayAdapter<String> itemsAdapter =
//                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
//                ListView listView = (ListView) findViewById(R.id.order);
//                listView.setAdapter(itemsAdapter);

//

            }
            else

            {
                Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ParentHome.class));

            }
//            else if (status.equalsIgnoreCase("delivered"))
//            {
//                Toast.makeText(this, "Delivered", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(),Db_view_order.class));
//            }
//            else if (status.equalsIgnoreCase("undelivered"))
//            {
//                Toast.makeText(this, "Undelivered", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(),Db_view_order.class));
//            }

        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something happened"+e, Toast.LENGTH_LONG).show();
        }
    }
}