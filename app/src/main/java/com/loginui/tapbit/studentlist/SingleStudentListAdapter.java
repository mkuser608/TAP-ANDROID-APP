package com.loginui.tapbit.studentlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loginui.tapbit.R;
import com.loginui.tapbit.student.StudentResume;

import java.util.ArrayList;

public class SingleStudentListAdapter extends RecyclerView.Adapter<ViewHolderStudentList> implements Filterable
{
    Context context;
    ArrayList<ModelSingleStudentList> data;
    ArrayList<ModelSingleStudentList> backup;
    String token;

    public SingleStudentListAdapter(ArrayList<ModelSingleStudentList> data, Context context, String token) {
        this.data = data;
        backup=new ArrayList<>(data);
        this.context=context;
        this.token=token;
    }




    @NonNull
    @Override
    public ViewHolderStudentList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlestudentforlist, parent, false);
        return new ViewHolderStudentList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStudentList holder, int position) {

        ModelSingleStudentList modelSingleStudentList = data.get(position);

        Glide.with(context).load(data.get(position).getImage()).into(holder.studentImage);
        holder.studentName.setText(data.get(position).getFullName());
        holder.studentRollNo.setText(data.get(position).getRollNo());
        holder.studentBranch.setText(data.get(position).getBranch());
        holder.cgpa.setText(data.get(position).getCgpa());
        holder.passingYear.setText(data.get(position).getPassingYear());

        holder.studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentResume.class);
                intent.putExtra("rollNo", modelSingleStudentList.getRollNo());
                intent.putExtra("token", token);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
         return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {

            ArrayList<ModelSingleStudentList> filteredData = new ArrayList<>();

            if(keyword.toString().isEmpty())
            {
                filteredData.addAll(backup);
            }else
            {
                for(ModelSingleStudentList obj : backup){
                    if(obj.getFullName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filteredData.add(obj);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            data.clear();
            data.addAll((ArrayList<ModelSingleStudentList>)results.values);
            notifyDataSetChanged();
        }
    };

}
