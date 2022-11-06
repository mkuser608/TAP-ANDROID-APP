package com.loginui.tapbit.student.semresults;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemResultsDasboard extends AppCompatActivity {

    Button createNewSemResult;
    String jwtToken = null;
    RecyclerView rcv;
    Intent oldIntent;
    SingleSemResultsListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_results_dasboard);

        createNewSemResult = findViewById(R.id.createNewSemResults);
        oldIntent = getIntent();
        jwtToken = getIntent().getStringExtra("message_key");
        setTitle("Sem Results");
        showSemData();

    }

    public void createNewSemResults(View v) {
        Intent intent = new Intent(SemResultsDasboard.this, AddNewSemesterResults.class);
        intent.putExtra("message_key", jwtToken);
        startActivity(intent);

    }


    private void showSemData() {
        RequestQueue queue = Volley.newRequestQueue(SemResultsDasboard.this);
        String url = GlobalVariable.link + "getSemesterResults";
        System.out.println(url);
        ArrayList<ModelSingleSemResultList> semResultLists = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                        try {
                            for (int i =0; i< response.length();i++){
                                ModelSingleSemResultList model = new ModelSingleSemResultList();
                                model.setId(Long.parseLong(response.getJSONObject(i).get("id").toString()));
                                model.setSemNo(Integer.parseInt(response.getJSONObject(i).get("semNo").toString()));
                                model.setRegistrationNo(response.getJSONObject(i).get("registrationNo").toString());
                                model.setsGPA(new BigDecimal( response.getJSONObject(i).get("sgpa").toString()));
                                model.setcGPA(new BigDecimal( response.getJSONObject(i).get("cgpa").toString()));
                                model.setRemarks(response.getJSONObject(i).get("remarks").toString());

                                semResultLists.add(model);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        rcv = findViewById(R.id.recyclerView);
                        rcv.setLayoutManager(new LinearLayoutManager(SemResultsDasboard.this));

                        listAdapter = new SingleSemResultsListAdapter(SemResultsDasboard.this, semResultLists,jwtToken,oldIntent);
                        rcv.setAdapter(listAdapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        error.printStackTrace();
                    }

                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", jwtToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
}