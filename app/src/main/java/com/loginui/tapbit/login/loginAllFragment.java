package com.loginui.tapbit.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loginui.tapbit.admin.AdminDashboard;
import com.loginui.tapbit.cordinator.CordinatorDashboard;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.professor.ProfessorDashboard;
import com.loginui.tapbit.R;
import com.loginui.tapbit.student.StudentDashboard;

import org.json.JSONException;
import org.json.JSONObject;


public class loginAllFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText email;
    EditText password;
    Button btn;
    String jwtToken = null;
    float v=0;
    private Spinner spinner;
    private static final String[] paths = {"Admin", "Company", "Professor","Cordinator", "Student"};
    private String role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_all, container, false);



        //Check logged in users



        //spinner//
        spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spiner//

        Button button = (Button) view.findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               String em = email.getText().toString();
               String pw = password.getText().toString();
// ...

// Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(container.getContext());
                String url = GlobalVariable.link+"getToken";
                System.out.println(url);
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("userName", em);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonBody.put("password", pw);
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
                                    System.out.println(response.get("userRole"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {


                                    if(role.equals("Student")&&(response.get("userRole")).equals("Student")){
                                        jwtToken= response.get("jwtToken").toString();
                                        System.out.println(jwtToken);
                                        getStudentDashBoard();
                                    }
                                    if(role.equals("Professor")&&(response.get("userRole")).equals("Professor")){
                                        jwtToken= response.get("jwtToken").toString();
                                        System.out.println(jwtToken);
                                        getProfessorDashBoard();
                                    }
                                    if(role.equals("Admin")&&(response.get("userRole")).equals("Admin")){
                                        jwtToken= response.get("jwtToken").toString();
                                        System.out.println(jwtToken);
                                        getAdminDashBoard();
                                    }
                                    if(role.equals("Cordinator")&&(response.get("userRole")).equals("Cordinator")){
                                        jwtToken= response.get("jwtToken").toString();
                                        System.out.println(jwtToken);
                                        getProfessorDashBoard();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                jwtToken=null;
                                error.printStackTrace();

                            }});
                if (jwtToken==null){
                    Toast.makeText(getContext(), "enter email and password", Toast.LENGTH_SHORT).show();
                }

                queue.add(jsonObjectRequest);


            }
        });

        email = view.findViewById(R.id.email_id);
        password = view.findViewById(R.id.password);
        btn = view.findViewById(R.id.login);

        email.setTranslationX(800);
        password.setTranslationX(800);
        btn.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        btn.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn.animate().translationX(0).alpha(1).setDuration(700).setDuration(700).start();

        return view;

    }

    public void getStudentDashBoard(){
        if (getActivity() != null) { // check if activity not null
            Intent intent = new Intent(getActivity(), StudentDashboard.class);
            intent.putExtra("message_key", jwtToken);
            getActivity().startActivity(intent);
        }
    }
    public void getProfessorDashBoard(){
        if (getActivity() != null) { // check if activity not null
            Intent intent = new Intent(getActivity(), ProfessorDashboard.class);
            intent.putExtra("message_key", jwtToken);
            getActivity().startActivity(intent);
        }
    }
    public void getAdminDashBoard(){
        if (getActivity() != null) { // check if activity not null
            Intent intent = new Intent(getActivity(), AdminDashboard.class);
            intent.putExtra("message_key", jwtToken);
            getActivity().startActivity(intent);
        }
    }
    public void getCordinatorDashBoard(){
        if (getActivity() != null) { // check if activity not null
            Intent intent = new Intent(getActivity(), CordinatorDashboard.class);
            intent.putExtra("message_key", jwtToken);
            getActivity().startActivity(intent);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                role=paths[0];
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                role=paths[1];
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                role=paths[2];
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 3:
                role=paths[3];
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 4:
                role=paths[4];
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}