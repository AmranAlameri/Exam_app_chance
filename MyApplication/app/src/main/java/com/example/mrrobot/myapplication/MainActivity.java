package com.example.mrrobot.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String s;
    EditText tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=(Button) findViewById(R.id.clicked);
      tv1=(EditText)findViewById(R.id.t1);
      tv2=(EditText)findViewById(R.id.t2);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //s=tv1.getText().toString();
                //Toast.makeText(getApplicationContext(),s ,Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST,  "http://192.168.32.31/show_users.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_LONG).show();


                        try {
                           // JSONObject res=new JSONObject(URLDecoder.decode(response,"UTF-8"));
                            JSONArray jsonarray = new JSONArray(response);
                           // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();


                            if(jsonarray.length ()> 0){
                                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();


                                Intent i=new Intent(MainActivity.this,Second.class);
                                 startActivity(i);





                            }
                            else{

                               Toast.makeText(getApplicationContext(),"wrong data", Toast.LENGTH_SHORT).show();

                            }





                       }  catch (Exception e) {

                           Toast.makeText(getApplicationContext(),"the error"+e.getMessage(),Toast.LENGTH_LONG).show();



                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"error"+error, Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        parms.put("Name",tv1.getText().toString());
                        parms.put("Password",tv2.getText().toString());

                        return parms;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }
        });
    }
}
