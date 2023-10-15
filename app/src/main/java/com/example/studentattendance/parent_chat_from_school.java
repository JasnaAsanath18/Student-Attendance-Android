//package com.example.schoolbustracking;
//import androidx.appcompat.app.AppCompatActivity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//import android.view.textclassifier.TextLinks;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//import android.annotation.TargetApi;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.telephony.SmsMessage;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.studentattendance.JsonReq;
//import com.example.studentattendance.JsonResponse;
//import com.example.studentattendance.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//public class parent_chat_from_school extends AppCompatActivity implements JsonResponse {
//    EditText e1;
//    String complaint, url;
//    Button b1;
//    ListView l1;
//    String[] complaints, reply, date, value;
//    SharedPreferences sh;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_parent_send_complaint_view_status);
//        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        e1 = (EditText) findViewById(R.id.etcomplaint);
//        b1 = (Button) findViewById(R.id.button);
//        l1 = (ListView) findViewById(R.id.lvview);
//
//        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        String hu = sh.getString("ip", "");
//
//        url = "http://" + hu + "/api/complaint_view_status";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//
//                params.put("type", "incoming");
//                params.put("lid", sh.getString("log_id", ""));
//
//                return params;
//            }
//        };
//        JsonReq JR = new JsonReq();
//        JR.json_response = (JsonResponse) parent_chat_from_school.this;
//        String q = "/parent_chat_from_school?lid=" + sh.getString("log_id", "");
//        q = q.replace(" ", "%20");
//        JR.execute(q);
//
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                complaint = e1.getText().toString();
//                if (complaint.equalsIgnoreCase("") || !complaint.matches("[a-zA-Z ]+")) {
//                    e1.setError("Enter your Complaint");
//                    e1.setFocusable(true);
//                } else {
//
//                    JsonReq JR = new JsonReq();
//                    JR.json_response = (JsonResponse) parent_chat_from_school.this;
//                    String q = "/parent_chat_from_school?comp=" + complaint + "&lid=" + sh.getString("log_id", "");
//                    q = q.replace(" ", "%20");
//                    JR.execute(q);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void response(JSONObject jo) {
//
//        try {
//
//            String method = jo.getString("method");
//
//
//            if (method.equalsIgnoreCase("send")) {
//                try {
//                    String status = jo.getString("status");
//                    Log.d("pearl", status);
//
//
//                    if (status.equalsIgnoreCase("success")) {
//                        Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(getApplicationContext(), Parenthome.class));
//
//                    } else {
//
//                        Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
//                    }
//
//
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                }
//            } else if (method.equalsIgnoreCase("view")) {
//                try {
//                    String status = jo.getString("status");
//                    Log.d("pearl", status);
//
//
//                    if (status.equalsIgnoreCase("success")) {
//                        JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
//                        //feedback_id=new String[ja1.length()];
//                        complaints = new String[ja1.length()];
//                        reply = new String[ja1.length()];
//                        date = new String[ja1.length()];
//                        value = new String[ja1.length()];
//
//                        for (int i = 0; i < ja1.length(); i++) {
//                            complaints[i] = ja1.getJSONObject(i).getString("complaint");
//                            reply[i] = ja1.getJSONObject(i).getString("reply");
//                            date[i] = ja1.getJSONObject(i).getString("date");
//                            value[i] = "complaints: " + complaints[i] + "\nreply: " + reply[i] + "\ndate: " + date[i];
//
//                        }
//                        ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                        l1.setAdapter(ar);
//                    }
//                } catch (JSONException jsonException) {
//                    jsonException.printStackTrace();
//                }
//            }
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }
//    }
//}