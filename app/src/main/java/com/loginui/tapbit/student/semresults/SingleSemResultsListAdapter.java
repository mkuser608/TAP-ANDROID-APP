package com.loginui.tapbit.student.semresults;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.R;
import com.loginui.tapbit.studentlist.ViewHolderStudentList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleSemResultsListAdapter extends RecyclerView.Adapter<ViewHolderSemResultList>{
    Context context;
    ArrayList<ModelSingleSemResultList> data;
    String token;
    Intent oldIntent;

    public SingleSemResultsListAdapter(Context context, ArrayList<ModelSingleSemResultList> data, String token, Intent intent) {
        this.context = context;
        this.data = data;
        this.oldIntent = intent;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolderSemResultList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sinlesemresultforlist, parent, false);
        return new ViewHolderSemResultList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSemResultList holder, int position) {
        ModelSingleSemResultList modelSingleSemResultList = data.get(position);
        holder.semNo.setText(Long.toString((modelSingleSemResultList.getSemNo())));
        holder.registrationNo.setText(modelSingleSemResultList.getRegistrationNo());
        holder.sGPA.setText(modelSingleSemResultList.getsGPA().toString());
        holder.cGPA.setText(modelSingleSemResultList.getcGPA().toString());
        holder.remarks.setText(modelSingleSemResultList.getRemarks());

        holder.updateSemResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatedSemResults.class);
                intent.putExtra("message_key", token);
                intent.putExtra("id",(modelSingleSemResultList.getId()));
                intent.putExtra("semNo",Long.toString((modelSingleSemResultList.getSemNo())));
                intent.putExtra("registrationNo",modelSingleSemResultList.getRegistrationNo());
                intent.putExtra("sgpa",modelSingleSemResultList.getsGPA().toString());
                intent.putExtra("cgpa",modelSingleSemResultList.getcGPA().toString());
                intent.putExtra("remarks",modelSingleSemResultList.getRemarks());
                context.startActivity(intent);
            }
        });

        holder.deleteSemResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.deleteSemResult.getText().equals("Delete")){
                    holder.deleteSemResult.setText("Confirm Delete");
                    Toast.makeText(context, "please Confirm", Toast.LENGTH_SHORT).show();
                }else{
                    deleteSemResult(modelSingleSemResultList.getId());
                }
            }
        });

    }

    private void deleteSemResult(long id) {
        String url = GlobalVariable.link+"deleteSemesterResult?id="+id;
        System.out.println(url);
        System.out.println(token);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    context.startActivity(oldIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("nhi hua");
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
    public int getItemCount() {
        return data.size();
    }
}
