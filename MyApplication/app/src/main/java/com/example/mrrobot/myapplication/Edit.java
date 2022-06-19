package com.example.mrrobot.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {
    Bundle b;
    EditText edtxtpassword;
    EditText edtxtname;
    EditText edtxtlname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
         b=getIntent().getExtras();
        edtxtname=(EditText)findViewById(R.id.edtxtName);
        edtxtpassword=(EditText)findViewById(R.id.edtxtPassword);
        edtxtlname=(EditText)findViewById(R.id.edtxtlname);
        Button btnSave=(Button)findViewById(R.id.btnSave);
        edtxtname.setText(b.getString("Name"));
        edtxtpassword.setText(b.getString("Password"));
        edtxtpassword.setText(b.getString("lname"));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Edit.this, ""+b.getString("Name"), Toast.LENGTH_SHORT).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.32.31/edit.php", new Response.Listener<String>() {
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
                    protected Map<String, String> getParams()throws AuthFailureError {
                        Map<String, String> parms = new HashMap<>();
                        parms.put("Name",edtxtname.getText().toString());
                        parms.put("Password",edtxtpassword.getText().toString());
                        parms.put("lname",edtxtlname.getText().toString());
                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }
}
