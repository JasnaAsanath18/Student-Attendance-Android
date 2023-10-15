package com.example.studentattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Notes extends AppCompatActivity implements JsonResponse{
    ListView note;
    String [] notess,subject,teacher,details;
    SharedPreferences sh;
String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        String uid=sh.getString("student_id", "");

        note=(ListView)findViewById(R.id.notes);
        note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                path=notess[position];
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setClassName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity2");
//                Uri uri = Uri.parse("http://" +IpSettings.text+"/api/"+path);
                Uri uri = Uri.parse("http://" +IpSettings.text+"/api/"+path);
                intent.setData(uri);
                startActivity(intent);

            }
        });
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Notes.this;
        String q="/student_view_notes";
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
                notess= new String[ja1.length()];
                subject= new String[ja1.length()];
                teacher = new String[ja1.length()];
//                time= new String[ja1.length()];
                details=new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {


                    notess[i] = ja1.getJSONObject(i).getString("note");
                    subject[i]=ja1.getJSONObject(i).getString("subject");
//                    time[i]=ja1.getJSONObject(i).getString("time");
                    teacher[i]=ja1.getJSONObject(i).getString("teachername");
                    details[i] = "Subject : " + subject[i]+"\nTeacher : "+teacher[i];

//                    Toast.makeText(getApplicationContext(), details[i], Toast.LENGTH_LONG).show();

                }
//                ArrayAdapter<String> itemsAdapter =
//                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
//                ListView listView = (ListView) findViewById(R.id.order);
//                listView.setAdapter(itemsAdapter);

//
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, details);

                note.setAdapter(ar);
            }
            else

            {
                Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_LONG).show();

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