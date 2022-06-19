package com.example.mrrobot.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

/**
 * Created by MrRobot on 6/18/2022.
 */
public class customList extends ArrayAdapter {
    private Context con;
    protected ArrayList<row> data;
    private int reslayout;

customList(Context context,int resource,ArrayList objects)
{
    super(context,resource,objects);
    con=context;
    reslayout=resource;
    data=objects;
}
    @Override
    public int getCount(){return data.size();}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView=inflater.inflate(reslayout,null);
        final TextView name=(TextView)rootView.findViewById(R.id.name);
        final TextView password=(TextView)rootView.findViewById(R.id.password);
        final TextView lname=(TextView)rootView.findViewById(R.id.lname);
        Button delete=(Button)rootView.findViewById(R.id.btnDelete);
        final Button edit=(Button)rootView.findViewById(R.id.btnEdit);

        //Arraylist
        final row itemrow=data.get(position);
        name.setText(itemrow.Name);
        password.setText(itemrow.Password);

delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.32.31/delete.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Toast.makeText(con, "" + response, Toast.LENGTH_SHORT).show();
                    data.remove(itemrow);
                    customList.this.notifyDataSetChanged();

                } catch (Exception e) {
                   // Toast.makeText(getApplicationContext(), "the error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), "error" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parms = new HashMap<String, String>();
                parms.put("Name",name.getText().toString());
                parms.put("Password", password.getText().toString());
                parms.put("lname", lname.getText().toString());
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
});

edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent editint=new Intent(con,Edit.class);
        editint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        editint.putExtra("Name",itemrow.Name);
        editint.putExtra("Password",itemrow.Password);
        editint.putExtra("lname",itemrow.lname);
        con.startActivity(editint);
    }
});




        return rootView;
    }
}
