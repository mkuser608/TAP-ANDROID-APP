package com.loginui.tapbit.notice;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.loginui.tapbit.R;

public class ViewHolderNoticeList extends RecyclerView.ViewHolder{

    TextView noticeTitle;
    TextView noticeDate;

    public ViewHolderNoticeList(@NonNull View itemView) {
        super(itemView);
        noticeTitle=itemView.findViewById(R.id.noticeTitle);
        noticeDate=itemView.findViewById(R.id.noticeDate);

    }
}
