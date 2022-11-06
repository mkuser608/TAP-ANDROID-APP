package com.loginui.tapbit.notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewNoticeList extends AppCompatActivity {
    String token;
    RecyclerView rcv;
    SingleNoticeListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice_list);

        Intent intent = getIntent();
        token = intent.getStringExtra("message_key");
        System.out.println(token);

        rcv = findViewById(R.id.recyclerView);
        getNoticeFromServer();
    }

    private void getNoticeFromServer() {
        ArrayList<ModelSingleNoticeList> noticeLists = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = GlobalVariable.link + "viewAllNotice";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0; i< response.length();i++){

                            ModelSingleNoticeList model = new ModelSingleNoticeList();
                            try {
                                model.setNoticeId((Integer) response.getJSONObject(i).get("id"));
                                model.setNoticeTitle(response.getJSONObject(i).get("title").toString());
                                model.setNoticeDate(response.getJSONObject(i).get("updatedDate").toString().substring(0,10));
                                model.setNoticeUrl(response.getJSONObject(i).get("noticeUrl").toString());
                                noticeLists.add(model);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        //recycler view
                        rcv = findViewById(R.id.recyclerView);
                        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                        listAdapter = new SingleNoticeListAdapter(getApplicationContext(),noticeLists,token);
                        rcv.setAdapter(listAdapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        error.printStackTrace();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                return headers;
            }};
        queue.add(jsonObjectRequest);
    }

}