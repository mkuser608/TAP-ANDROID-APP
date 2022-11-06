package com.loginui.tapbit.student.semresults;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.loginui.tapbit.R;

public class ViewHolderSemResultList extends RecyclerView.ViewHolder{


    TextView semNo;
    TextView registrationNo;
    TextView sGPA;
    TextView cGPA;
    TextView remarks;
    Button updateSemResult;
    Button deleteSemResult;

    public ViewHolderSemResultList(@NonNull View itemView) {
        super(itemView);
        semNo = itemView.findViewById(R.id.semNumber);
        registrationNo =itemView.findViewById(R.id.studentSemRegNo);
        sGPA = itemView.findViewById(R.id.semSGPA);
        cGPA = itemView.findViewById(R.id.semCGPA);
        remarks = itemView.findViewById(R.id.semRemarks);
        updateSemResult =itemView.findViewById(R.id.updateSemResult);
        deleteSemResult = itemView.findViewById(R.id.deleteSemResult);

    }
}
