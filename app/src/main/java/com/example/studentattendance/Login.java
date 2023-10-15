package com.example.studentattendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentattendance.StudentHome;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse {

    EditText e1,e2;
    Button b1;
    String uname,pasw,logid,usertype;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1= findViewById(R.id.uname);
        e2=(EditText) findViewById(R.id.passw);

        b1=(Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uname=e1.getText().toString();
                pasw=e2.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse)Login.this;
                String q="/login?username=" + uname + "&password=" + pasw;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("check");
                logid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("user_type");
                SharedPreferences.Editor e = sh.edit();
                Toast.makeText(this, usertype, Toast.LENGTH_SHORT).show();
                e.putString("log_id", logid);
                e.commit();

                if(usertype.equals("student"))
                {
                    String user_id = jo.getString("student_id");
                    e.putString("student_id", user_id);
                    e.commit();
                    Toast.makeText(getApplicationContext(),"Logged in Successfully", Toast.LENGTH_SHORT).show();//length long is here
                    startActivity(new Intent(getApplicationContext(), StudentHome.class));
                }
                else if(usertype.equals("parent"))
                {
                    String user_id = jo.getString("parent_id");
                    e.putString("parent_id", user_id);
                    e.commit();
                    Toast.makeText(getApplicationContext(),"Logged in Successfully", Toast.LENGTH_SHORT).show();//length long is here
                    startActivity(new Intent(getApplicationContext(),ParentHome.class));

                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}