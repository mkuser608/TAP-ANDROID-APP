package com.loginui.tapbit.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.R;
import com.loginui.tapbit.student.semresults.SemResultsDasboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentDashboard extends AppCompatActivity {
    TextView roll;
    TextView dateOfBirth;
    ImageView profilePhoto;
    ImageView semResults;
    ImageView UpdateProfile;
    String token;
    Toolbar toolbar;
    ImageView resume;
    String rollNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        roll = findViewById(R.id.rollNo);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        profilePhoto = findViewById(R.id.profilePhoto);
        toolbar = findViewById(R.id.toolbar);


        Intent intent = getIntent();
        token = "Bearer " + intent.getStringExtra("message_key");


        UpdateProfile = findViewById(R.id.UpdateProfile);
        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDashboard.this, UpdateStudentProfilePicture.class);
                System.out.println("this is dashboard"+token);
                intent.putExtra("message_key", token);
                startActivity(intent);
            }
        });

        semResults = findViewById(R.id.semResults);
        semResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDashboard.this, SemResultsDasboard.class);
                System.out.println("this is dashboard"+token);
                intent.putExtra("message_key", token);
                startActivity(intent);
            }
        });

        resume = findViewById(R.id.imageView4);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDashboard.this, StudentResume.class);
                intent.putExtra("rollNo", rollNo);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(StudentDashboard.this);
        String url = GlobalVariable.link + "getStudent";

        System.out.println(GlobalVariable.link + "getStudent");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            for (int i = 0; i < response.getJSONArray("educationalHistories").length(); i++) {
                                System.out.println(response.getJSONArray("educationalHistories").getJSONObject(i).get("type"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String profileUrl = null;
                        try {
                            if(!(response.get("profilePhotoString").toString().length()==4)) {
                                if(response.get("profilePhotoString").toString().startsWith("http://localhost")){
                                    profileUrl = GlobalVariable.link + response.get("profilePhotoString").toString().substring(22);
                                }else {
                                    profileUrl =  response.get("profilePhotoString").toString();
                                }
                                Glide.with(StudentDashboard.this).load(profileUrl).into(profilePhoto);
                            }

                            dateOfBirth.setText(response.get("date_of_birth").toString());
                            toolbar.setTitle(response.get("first_Name").toString() + " " + response.get("last_Name").toString());
                            rollNo = response.get("roll_No").toString();
                            roll.setText("Roll No: " + rollNo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.getMessage().contains("org.json.JSONException: End of input at character 0 of")){

                            Intent intent = new Intent(StudentDashboard.this, SaveStudentEntitySingleTime.class);
                            System.out.println("this is dashboard"+token);
                            intent.putExtra("message_key", token);
                            startActivity(intent);
                        }

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


}