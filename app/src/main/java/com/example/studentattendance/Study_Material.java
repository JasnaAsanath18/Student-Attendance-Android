
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

public class Study_Material extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String [] subject,details,teacher,title,smdetails;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);
        String uid=sh.getString("student_id", "");

        l1=(ListView)findViewById(R.id.lst);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Study_Material.this;
        String q="/student_view_studymaterial";
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
                subject = new String[ja1.length()];
                title= new String[ja1.length()];
                teacher= new String[ja1.length()];
                smdetails= new String[ja1.length()];
                details=new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {


                    subject[i] = ja1.getJSONObject(i).getString("subject");
                    title[i]=ja1.getJSONObject(i).getString("title");
                    smdetails[i]=ja1.getJSONObject(i).getString("details");
                    teacher[i]=ja1.getJSONObject(i).getString("teachername");
                    details[i] = "Subject : " + subject[i] + "\nTeacher : " + teacher[i] + "\nTitle : " + title[i] + "\nStudy Material : " + smdetails[i];

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