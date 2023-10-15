package com.example.studentattendance;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONObject;

public class Parent_View_Mark extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String [] mark,subject,teacher,details;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_mark);
        String uid=sh.getString("parent_id", "");

        l1=(ListView)findViewById(R.id.lst);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Parent_View_Mark.this;
        String q="/parent_view_mark?parent_id="+uid;
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
                teacher= new String[ja1.length()];
                subject= new String[ja1.length()];
                mark = new String[ja1.length()];
//                time= new String[ja1.length()];
                details=new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {


                    subject[i] = ja1.getJSONObject(i).getString("subject");
                    mark[i]=ja1.getJSONObject(i).getString("mark");
//                    time[i]=ja1.getJSONObject(i).getString("time");
                    teacher[i]=ja1.getJSONObject(i).getString("teachername");
                    details[i] = "Subject : " + subject[i]+"\nTeacher : "+teacher[i]+"\nMark : "+mark[i];

//                    Toast.makeText(getApplicationContext(), details[i], Toast.LENGTH_LONG).show();

                }
//                ArrayAdapter<String> itemsAdapter =
//                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
//                ListView listView = (ListView) findViewById(R.id.order);
//                listView.setAdapter(itemsAdapter);

//
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, details);

                l1.setAdapter(ar);
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