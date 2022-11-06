package com.loginui.tapbit.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.R;
import com.loginui.tapbit.valid.IsValidStudentData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SaveStudentEntitySingleTime extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    private static final String[] paths = {"Mechanical Engineering", "Electrical Engineering", "Production Engineering",
            "Metallurgical Engineering", "Chemical Engineering", "Civil Engineering",
            "Electronics & Communication Engineering", "Mining Engineering",
            "Computer Science and Engineering", "Information Technology"};
    private String selectedBranch;
    private String token;

    EditText registrationNo;
    EditText firstname;
    EditText lastName;
    EditText dateOfBirth;
    EditText admissionDate;
    EditText passingDate;
    EditText cgpa;
    EditText mobileNumber;
    EditText whatsAppNumber;
    EditText fatherName;
    EditText motherName;
    EditText fatherOccupation;
    EditText motherOccupation;
    Button btn;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_student_entity_single_time);

        Intent intent = getIntent();
        token = intent.getStringExtra("message_key");
        System.out.println("entity" + token);

        //spinner//
        spinner = (Spinner) findViewById(R.id.branchSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spiner//

        //id selection
        firstname = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        admissionDate = findViewById(R.id.collegeStarts);
        passingDate = findViewById(R.id.collegeEnd);
        cgpa = findViewById(R.id.cgpa);
        mobileNumber = findViewById(R.id.mobileNumber);
        whatsAppNumber = findViewById(R.id.whatsAppNumber);
        fatherName = findViewById(R.id.fatherName);
        motherName = findViewById(R.id.motherName);
        fatherOccupation = findViewById(R.id.fatherOccupation);
        motherOccupation = findViewById(R.id.motherOccupation);
        registrationNo = findViewById(R.id.registrationNo);

        //date
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateOfBirthFromCalender();
            }
        });

        admissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateOfAdmissionFromCalender();
            }
        });

        passingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateOfPassingFromCalender();
            }
        });
        //date end

        btn = findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = GlobalVariable.link + "saveStudent";
                System.out.println(url);

                RequestQueue queue = Volley.newRequestQueue(SaveStudentEntitySingleTime.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST, url, jsonBody(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(SaveStudentEntitySingleTime.this, StudentDashboard.class);
                        System.out.println(token);
                        intent.putExtra("message_key", token.substring(7));
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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


    private JSONObject jsonBody() {
        JSONObject jsonBody = new JSONObject();

        IsValidStudentData isValidStudentData = new IsValidStudentData();

        try {
            String regNo = registrationNo.getText().toString();
            String fName = firstname.getText().toString();
            String lName = lastName.getText().toString();
            String dOB = dateOfBirth.getText().toString();
            String adDate = admissionDate.getText().toString();
            String paDate = passingDate.getText().toString();
            String cgpaString = cgpa.getText().toString();
            String moNum = mobileNumber.getText().toString();
            String wNum = whatsAppNumber.getText().toString();
            String faName = fatherName.getText().toString();
            String maName = motherName.getText().toString();
            String fOccupation = fatherOccupation.getText().toString();
            String mOccupation = motherOccupation.getText().toString();

            if (isValidStudentData.isValidRegistrationNumber(regNo)) {
                jsonBody.put("registrationString_No", regNo);
            } else {
                registrationNo.setError("Enter Registration Number");
            }

            if (isValidStudentData.isValidMobileNumber(moNum)) {
                jsonBody.put("mobile_No", moNum);
            } else {
                mobileNumber.setError("Enter valid 10 digit Mobile Number");
            }

            if (isValidStudentData.isValidMobileNumber(wNum)) {
                jsonBody.put("whatsApp_No", wNum);
            } else {
                whatsAppNumber.setError("Enter Valid 10 digit WhatsApp Number ");
            }

            if (fName.isEmpty() || faName.length() < 3) {
                firstname.setError("Enter Correct First Name");
            } else {
                jsonBody.put("first_Name", fName);
            }

            if (lName.isEmpty() || lName.length() < 3) {
                lastName.setError("Enter Valid Last Name");

            } else {
                jsonBody.put("last_Name", lName);
            }


            jsonBody.put("roll_No", "auto");


            jsonBody.put("date_of_birth", dOB);
            jsonBody.put("branch", selectedBranch);
            jsonBody.put("date_of_college_enrolls", adDate);
            jsonBody.put("date_of_passing", paDate);
            jsonBody.put("cgpa", cgpaString);


            jsonBody.put("father_Name", faName);
            jsonBody.put("mother_Name", maName);
            jsonBody.put("father_Occupation", fOccupation);
            jsonBody.put("mother_Occupation", mOccupation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonBody);

        return jsonBody;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBranch = paths[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void setDateOfBirthFromCalender() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(SaveStudentEntitySingleTime.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                , setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (dayOfMonth / 10 == 0 && month / 10 == 0) {
                    String date = year + "-0" + month + "-0" + dayOfMonth;
                    dateOfBirth.setText(date);
                } else if (dayOfMonth / 10 == 0) {
                    String date = year + "-" + month + "-0" + dayOfMonth;
                    dateOfBirth.setText(date);
                } else if (month / 10 == 0) {
                    String date = year + "-0" + month + "-" + dayOfMonth;
                    dateOfBirth.setText(date);
                } else {
                    String date = year + "-" + month + "-" + dayOfMonth;
                    dateOfBirth.setText(date);
                }
            }
        };

    }

    public void setDateOfAdmissionFromCalender() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(SaveStudentEntitySingleTime.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                , setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (dayOfMonth / 10 == 0 && month / 10 == 0) {
                    String date = year + "-0" + month + "-0" + dayOfMonth;
                    admissionDate.setText(date);
                } else if (dayOfMonth / 10 == 0) {
                    String date = year + "-" + month + "-0" + dayOfMonth;
                    admissionDate.setText(date);
                } else if (month / 10 == 0) {
                    String date = year + "-0" + month + "-" + dayOfMonth;
                    admissionDate.setText(date);
                } else {
                    String date = year + "-" + month + "-" + dayOfMonth;
                    admissionDate.setText(date);
                }
            }
        };

    }

    public void setDateOfPassingFromCalender() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(SaveStudentEntitySingleTime.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                , setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (dayOfMonth / 10 == 0 && month / 10 == 0) {
                    String date = year + "-0" + month + "-0" + dayOfMonth;
                    passingDate.setText(date);
                } else if (dayOfMonth / 10 == 0) {
                    String date = year + "-" + month + "-0" + dayOfMonth;
                    passingDate.setText(date);
                } else if (month / 10 == 0) {
                    String date = year + "-0" + month + "-" + dayOfMonth;
                    passingDate.setText(date);
                } else {
                    String date = year + "-" + month + "-" + dayOfMonth;
                    passingDate.setText(date);
                }
            }
        };

    }
}