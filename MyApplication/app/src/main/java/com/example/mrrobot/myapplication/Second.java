package com.example.mrrobot.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Second extends AppCompatActivity {
    EditText edName;
    EditText edPassword;
    EditText edlname;
    Button btnInsert;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        edName = (EditText) findViewById(R.id.txtName);
        edPassword = (EditText) findViewById(R.id.txtPassword);
        edlname = (EditText) findViewById(R.id.txtLname);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnShow = (Button) findViewById(R.id.btnShow);
       // Toast.makeText(getApplicationContext(), "after", Toast.LENGTH_SHORT).show();
        //showData();
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(Second.this, Show_Data.class);
                startActivity(ii);
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "after", Toast.LENGTH_SHORT).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.23.31/Insert.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "the error" + e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error" + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> parms = new HashMap<String, String>();
                        parms.put("Name", edName.getText().toString());
                        parms.put("Password", edPassword.getText().toString());
                        parms.put("lname", edlname.getText().toString());

                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    public void showData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.32.31/showData.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(), "hii", Toast.LENGTH_LONG).show();
                try {

                    // JSONObject res=new JSONObject(URLDecoder.decode(response,"UTF-8"));
                    JSONArray jsonarray = new JSONArray(response);



                    if (jsonarray.length() > 0) {
                        Toast.makeText(getApplicationContext(), "hii", Toast.LENGTH_LONG).show();
                        ArrayList<row> arr = new ArrayList<row>();

                        for (int i = 0; i <= jsonarray.length(); i++) {
                            JSONObject jo = jsonarray.getJSONObject(i);
                            String Name = jo.getString("Name");
                            String Password = jo.getString("Password");
                            String lname= jo.getString("lname");
                            row itemrow = new row();
                            itemrow.Name = Name;
                            itemrow.Password = Password;
                            itemrow.lname=lname;
                            arr.add(itemrow);
                        }

                        customList adapter = new customList(getApplicationContext(), R.layout.showrow, arr);
                        ListView lsData = (ListView) findViewById(R.id.listView);
                        lsData.setAdapter(adapter);
                    }

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "the error" + e.getMessage(), Toast.LENGTH_LONG).show();


                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<String, String>();
                return super.getParams();
                /*parms.put("Name",tv1.getText().toString());
                parms.put("Password",tv2.getText().toString());

                return parms;*/
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}
