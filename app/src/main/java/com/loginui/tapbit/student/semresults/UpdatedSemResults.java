package com.loginui.tapbit.student.semresults;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.loginui.tapbit.student.StudentDashboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UpdatedSemResults extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
   private long id;
    private  String semNo;
    private EditText registrationNo;

    private EditText sgpa;
    private EditText cgpa;
    private EditText remarks;
    private String token;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_sem_results);

        registrationNo = findViewById(R.id.registrationNo);
        sgpa= findViewById(R.id.sgpa);
        cgpa=findViewById(R.id.cgpa);
        remarks=findViewById(R.id.remarks);
        btn = findViewById(R.id.submit);

        token=getIntent().getStringExtra("message_key");
        id=getIntent().getLongExtra("id",0L);
         final String[] paths = {getIntent().getStringExtra("semNo")};

         registrationNo.setText(getIntent().getStringExtra("registrationNo").toString());
         sgpa.setText(getIntent().getStringExtra("sgpa").toString());
         cgpa.setText(getIntent().getStringExtra("cgpa").toString());
         remarks.setText(getIntent().getStringExtra("remarks").toString());
         semNo=paths[0];

        //spinner//
        spinner = (Spinner)findViewById(R.id.SemNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spiner//







        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveSemesterResult();


            }
        });




    }

    private JSONObject getDatafromFormandReturnJason()  {
        JSONObject jsonObject = new JSONObject();
        try {
            String regNo = registrationNo.getText().toString();
            String sgpal = sgpa.getText().toString();
            String cgpal = cgpa.getText().toString();
            String remark = remarks.getText().toString();

            jsonObject.put("id",id);
            jsonObject.put("registrationNo", regNo);
            jsonObject.put( "semNo",semNo);
            jsonObject.put("sgpa",new BigDecimal(sgpal));
            jsonObject.put("cgpa",new BigDecimal(cgpal));
            jsonObject.put("remarks", remark);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

    private void saveSemesterResult( ){
        RequestQueue queue = Volley.newRequestQueue(UpdatedSemResults.this);

        String url = GlobalVariable.link +"updateSemeterResult";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                getDatafromFormandReturnJason(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(UpdatedSemResults.this, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(UpdatedSemResults.this, StudentDashboard.class);
                System.out.println(token);
                intent.putExtra("message_key", token.substring(7));
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(jsonObjectRequest);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}