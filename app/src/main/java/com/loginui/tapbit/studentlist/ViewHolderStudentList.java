package com.loginui.tapbit.studentlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loginui.tapbit.R;

public class ViewHolderStudentList extends RecyclerView.ViewHolder {

    ImageView studentImage;
    TextView studentName;
    TextView studentBranch;
    TextView studentRollNo;
    TextView passingYear;
    TextView cgpa;
    public ViewHolderStudentList(@NonNull View itemView) {
        super(itemView);

        studentImage = itemView.findViewById(R.id.imgStudent);
        studentBranch = itemView.findViewById(R.id.branchText);
        studentName = itemView.findViewById(R.id.nameStudent);
        studentRollNo = itemView.findViewById(R.id.rollText);
        passingYear=itemView.findViewById(R.id.passingYear);
        cgpa=itemView.findViewById(R.id.cgpaText);
    }
}
