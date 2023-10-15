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

public class Parent_View_Fee extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String [] fee,amount,date,details,fid;
    SharedPreferences sh;
    public static String fee_id,amnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_fee);
        String uid=sh.getString("parent_id", "");

        l1=(ListView)findViewById(R.id.lst);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Parent_View_Fee.this;
        String q="/parent_view_fee?parent_id="+uid;
        q = q.replace(" ", "%20");
        JR.execute(q);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                fee_id=fid[i];
                amnt=amount[i];
                final CharSequence[] items = {"Payment","Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Parent_View_Fee.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        if (items[item].equals("Payment"))
                        {
                            Intent il=new Intent(getApplicationContext(),Feepayment.class);
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
                fee = new String[ja1.length()];
                fid = new String[ja1.length()];
                amount= new String[ja1.length()];
                date= new String[ja1.length()];
//                mark = new String[ja1.length()];
//                time= new String[ja1.length()];
                details=new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {

                    fid[i] = ja1.getJSONObject(i).getString("fee_id");
                    fee[i] = ja1.getJSONObject(i).getString("fee");
                    amount[i]=ja1.getJSONObject(i).getString("amount");
                    date[i]=ja1.getJSONObject(i).getString("date");
//                    teacher[i]=ja1.getJSONObject(i).getString("teachername");
                    details[i] = "Fee : " + fee[i]+"\nAmount : "+amount[i]+"\nDate : "+date[i];

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