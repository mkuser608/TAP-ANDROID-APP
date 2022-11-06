package com.loginui.tapbit.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CreateNewStudent extends AppCompatActivity {

    String token;
    TextView message;
    EditText email;
    EditText password;
    EditText name;
    EditText rollNo;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_student);

       btn = findViewById(R.id.CreateStudentButton);
        Intent intent = getIntent();
        token = intent.getStringExtra("message_key");
        System.out.println(token);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               System.out.println(token);
               email = findViewById(R.id.email_id);
               name = findViewById(R.id.fullName);
               rollNo = findViewById(R.id.studentRollNo);
               password = findViewById(R.id.password);
               btn = findViewById(R.id.CreateStudentButton);
               message = findViewById(R.id.messageTextView);

               String url = GlobalVariable.link+"createNewStudent?rollNo="+rollNo.getText().toString();
               RequestQueue queue = Volley.newRequestQueue(v.getContext());
               System.out.println(url);
               JSONObject jsonBody = new JSONObject();
               try {
                   jsonBody.put("email",email.getText().toString());
                   jsonBody.put("name",name.getText().toString());
                   jsonBody.put("password",password.getText().toString());
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               System.out.println(jsonBody);
               // Request a string response from the provided URL
               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                       (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {

                               try {
                                   String responseMessage = response.get("name").toString()+" "+response.get("message");
                                   message.setText(responseMessage);
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }


                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {


                            if(error.networkResponse.statusCode == 409){
                                JSONObject jsonObject = null;
                                if(error.networkResponse.data!=null) {
                                    try {
                                        String body = null;
                                        body = new String(error.networkResponse.data,"UTF-8");
                                        try {
                                             jsonObject = new JSONObject(body);
                                            Toast.makeText(CreateNewStudent.this, jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                                        }catch (JSONException err){
                                            Log.d("Error", err.toString());
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                               if(error.networkResponse.statusCode == 400){
                                   if(error.networkResponse.data!=null) {
                                       JSONObject jsonObject;
                                       try {
                                           String body = null;
                                           body = new String(error.networkResponse.data,"UTF-8");
                                           jsonObject = new JSONObject(body);
                                           rollNo.setError("ROll no should valid");
                                           name.setError(jsonObject.getString("name"));
                                            password.setError(jsonObject.getString("password"));
                                           email.setError(jsonObject.getString("email"));

                                       } catch (UnsupportedEncodingException | JSONException e) {
                                           e.printStackTrace();
                                       }

                                   }

                               }
                           }
                       }
                       ){
                   @Override
                   public String getBodyContentType() {
                       return "application/json; charset=utf-8";
                   }

                   @Override
                   public Map<String, String> getHeaders() throws AuthFailureError {
                       HashMap<String, String> headers = new HashMap<String, String>();
                       System.out.println("hello torkn");
                       System.out.println(token);
                       headers.put("Authorization", token);
                       return headers;
                   }
               };
               queue.add(jsonObjectRequest);
           }
       });


    }
}