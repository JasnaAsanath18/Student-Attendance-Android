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

public class Question_paper extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String [] qp,subject,teacher,details,val,date;
    SharedPreferences sh;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        String uid=sh.getString("student_id", "");

        l1=(ListView)findViewById(R.id.notes);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                path=qp[position];
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
        JR.json_response=(JsonResponse)Question_paper.this;
        String q="/student_view_qp";
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
                qp= new String[ja1.length()];
                subject= new String[ja1.length()];
                teacher = new String[ja1.length()];
                date= new String[ja1.length()];
                details=new String[ja1.length()];
                val=new String[ja1.length()];
                for (int i = 0; i < ja1.length(); i++) {


                    qp[i] = ja1.getJSONObject(i).getString("qp");
                    subject[i]=ja1.getJSONObject(i).getString("subject");
                    date[i]=ja1.getJSONObject(i).getString("date");
                    teacher[i]=ja1.getJSONObject(i).getString("teachername");
                    details[i] = "Subject : " + subject[i]+"\nTeacher : "+teacher[i]+"\nDate : "+date[i];

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