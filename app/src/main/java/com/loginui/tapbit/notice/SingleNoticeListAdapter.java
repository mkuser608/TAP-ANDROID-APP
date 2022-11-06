package com.loginui.tapbit.notice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loginui.tapbit.PDFViewer;
import com.loginui.tapbit.R;
import com.loginui.tapbit.student.StudentResume;
import com.loginui.tapbit.studentlist.ViewHolderStudentList;

import java.util.ArrayList;

public class SingleNoticeListAdapter extends RecyclerView.Adapter<ViewHolderNoticeList>{
    Context context;
    ArrayList<ModelSingleNoticeList> data;
    String token;

    public SingleNoticeListAdapter(Context context, ArrayList<ModelSingleNoticeList> data,String token) {
        this.context = context;
        this.data = data;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolderNoticeList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlenoticeforlist, parent, false);
        return new ViewHolderNoticeList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNoticeList holder, int position) {

        ModelSingleNoticeList modelSingleNoticeList =data.get(position);
        holder.noticeTitle.setText(modelSingleNoticeList.getNoticeTitle());
        holder.noticeDate.setText(modelSingleNoticeList.getNoticeDate());

        holder.noticeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PDFViewer.class);
                intent.putExtra("PDFUrl", modelSingleNoticeList.getNoticeUrl());
                intent.putExtra("title", modelSingleNoticeList.getNoticeTitle());
                intent.putExtra("token", token);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
