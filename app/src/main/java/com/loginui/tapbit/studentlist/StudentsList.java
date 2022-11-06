package com.loginui.tapbit.studentlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

public class StudentsList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String token;
    RecyclerView rcv;
    SingleStudentListAdapter listAdapter;
    String selectedBranch;
    EditText passingYearEditText;

    private Spinner spinner;
    private static final String[] paths = {"Civil Engineering", "Computer Science", "Information Technology"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);


        Intent intent = getIntent();
        token = intent.getStringExtra("message_key");
        System.out.println(token);
        setTitle("Get Students Data");

        //spinner//
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spiner//

        //Api fetching

        Button button = findViewById(R.id.getButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ModelSingleStudentList> studentLists = new ArrayList<>();

                passingYearEditText = findViewById(R.id.passingYearEdit);
                if (!(passingYearEditText.length() == 4)) {
                    passingYearEditText.setError("Enter a valid Year for Eg: 2001 or 2023");
                }
                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                String url = GlobalVariable.link + "getAllStudentsByBranchAndPassingYear?branch=" + selectedBranch + "&passingYear=" + passingYearEditText.getText();
                System.out.println(url);

                System.out.println(token);


                JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                System.out.println(response);

                                for (int i = 0; i < response.length(); i++) {
                                    try {

                                        ModelSingleStudentList model = new ModelSingleStudentList();
                                        model.setFullName(response.getJSONObject(i).get("fullName").toString());
                                        model.setBranch(response.getJSONObject(i).get("branch").toString());
                                        model.setRollNo(response.getJSONObject(i).get("roll_No").toString());
                                        if ((response.getJSONObject(i).get("profilePhotoString").toString().length() != 4)) {
                                            String profileUrl;
                                            if (response.getJSONObject(i).get("profilePhotoString").toString().startsWith("http://localhost")) {
                                                profileUrl = GlobalVariable.link + response.getJSONObject(i).get("profilePhotoString").toString().substring(22);
                                            } else {
                                                profileUrl = response.getJSONObject(i).get("profilePhotoString").toString();
                                            }

                                            model.setImage(profileUrl);
                                        }
                                        model.setPassingYear(response.getJSONObject(i).get("passingYear").toString());
                                        model.setCgpa(response.getJSONObject(i).get("cgpa").toString());
                                        System.out.println(model);
                                        studentLists.add(model);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //recycler view
                                rcv = findViewById(R.id.recyclerView);
                                rcv.setLayoutManager(new LinearLayoutManager(v.getContext()));


                                listAdapter = new SingleStudentListAdapter(studentLists, getApplicationContext(), token);
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
                    }
                };

                queue.add(jsonObjectRequest);


            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBranch = paths[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchstudentmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchicon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println(newText);
                listAdapter.getFilter().filter(newText);
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }
}