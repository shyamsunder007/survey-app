package com.survey.iiits.survey_iiits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectGroup extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7","Person 8", "Person 9", "Person 10", "Person 11", "Person 12", "Person 13", "Person 14"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_group);



        final String draftId = getIntent().getStringExtra("draft_id");
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, ipclass.url+"/api/getgroups", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ArrayList groupNames = new ArrayList<>();
                ArrayList groupIds = new ArrayList<>();
                try {
                    JSONArray groupsJSON = new JSONArray(response);
                    for(int i=0;i<groupsJSON.length();i++)
                    {
                        JSONObject row = groupsJSON.getJSONObject(i);
                        groupNames.add(i,row.getString("groupname"));
                        groupIds.add(i,row.getString("idgroups"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selectgroup_recyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                GroupRecyclerAdapter customAdapter = new GroupRecyclerAdapter(getApplicationContext(), groupNames,groupIds,draftId);
                recyclerView.setAdapter(customAdapter);
                Log.d("CRESPOTER", "onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("hai","Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }
}
