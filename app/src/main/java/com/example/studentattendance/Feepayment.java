package com.example.studentattendance;

import static com.example.studentattendance.Parent_View_Fee.amnt;
import static com.example.studentattendance.Parent_View_Fee.fee_id;

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

        import org.json.JSONObject;

public class Feepayment extends AppCompatActivity implements JsonResponse{
    EditText e1,e2,e3,e4;
    Button b1;
    String holder,cvv,expdate,amount;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feepayment);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.et1);
        e2=(EditText)findViewById(R.id.et2);
        e3=(EditText)findViewById(R.id.et3);
        e4=(EditText)findViewById(R.id.et4);

        e4.setText(amnt);
        e4.setEnabled(false);
        b1=(Button)findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder=e1.getText().toString();
                cvv=e2.getText().toString();
                expdate=e3.getText().toString();
                amount=e4.getText().toString();

                if(holder.equalsIgnoreCase("")||!holder.matches("[A-Za-z]+"))
                {
                    e1.setError("Enter your first name");
                    e1.setFocusable(true);
                }

                else if(cvv.equalsIgnoreCase("")||!cvv.matches("[0-9]+")||cvv.length()!=3)
                {
                    e2.setError("Enter a valid cvv");
                    e2.setFocusable(true);
                }
                else if(expdate.equalsIgnoreCase(""))
//                ||!expdate.matches("[0-9\\-0-9\\-0-9]+")
                {
                    e3.setError("Enter a valid date ");
                    e3.setFocusable(true);
                }
                else if(amount.equalsIgnoreCase("")||!cvv.matches("[0-9]+"))
                {
                    e4.setError("Enter a valid cvv");
                    e4.setFocusable(true);
                }
//
                else
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) Feepayment.this;
                    String q="/parent_pay_fee?fid="+fee_id+"&amount="+amnt;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }}
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){

                Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),ParentHome.class));

            }
            else
            {
//                startActivity(new Intent(getApplicationContext(),Feepayment.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),ParentHome.class);
        startActivity(b);
    }
}