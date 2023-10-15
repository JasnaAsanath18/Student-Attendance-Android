





package com.example.studentattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Chat_St extends AppCompatActivity implements JsonResponse{
    ListView list1;
    String [] name,place,phone,email,details,desig,quali,tid;
    SharedPreferences sh;
    public static String teacher_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_st);
        String uid=sh.getString("parent_id", "");

        list1=(ListView)findViewById(R.id.lst);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Chat_St.this;
        String q="/student_view_teacher";
        q = q.replace(" ", "%20");
        JR.execute(q);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                teacher_id=tid[i];
//                amnt=amount[i];
                final CharSequence[] items = {"Chat","Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Chat_St.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        if (items[item].equals("Chat"))
                        {
                            Intent il=new Intent(getApplicationContext(),Chat_student.class);
                            startActivity(il);
                        }
                        else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }


    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            Log.d("pearl", status);


            if(status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("check");
//                attendance_id = new String[ja1.length()];
                name= new String[ja1.length()];
                place= new String[ja1.length()];
                phone = new String[ja1.length()];
                email= new String[ja1.length()];
                desig= new String[ja1.length()];
                quali= new String[ja1.length()];
                details=new String[ja1.length()];
                tid=new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    tid[i] = ja1.getJSONObject(i).getString("login_id");

                    name[i] = ja1.getJSONObject(i).getString("teachername");
                    place[i]=ja1.getJSONObject(i).getString("place");
                    phone[i]=ja1.getJSONObject(i).getString("phone");
                    email[i]=ja1.getJSONObject(i).getString("email");
                    desig[i]=ja1.getJSONObject(i).getString("designation");
                    quali[i]=ja1.getJSONObject(i).getString("qualification");
                    details[i] = "Name : " + name[i]+"\nPlace : "+place[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+"\nDesignation : "+desig[i]+"\nQualification : "+quali[i];

//                    Toast.makeText(getApplicationContext(), details[i], Toast.LENGTH_LONG).show();

                }
//                ArrayAdapter<String> itemsAdapter =
//                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
//                ListView listView = (ListView) findViewById(R.id.order);
//                listView.setAdapter(itemsAdapter);

//
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, details);

                list1.setAdapter(ar);
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