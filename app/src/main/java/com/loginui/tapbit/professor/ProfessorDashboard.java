package com.loginui.tapbit.professor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.loginui.tapbit.student.CreateNewStudent;
import com.loginui.tapbit.R;
import com.loginui.tapbit.notice.CreateNotice;
import com.loginui.tapbit.notice.ViewNoticeList;
import com.loginui.tapbit.studentlist.StudentsList;

public class ProfessorDashboard extends AppCompatActivity {
    String token;
    ImageView createStudent;
    ImageView viewStudent;
    ImageView viewNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);

        Intent intent = getIntent();
        token = "Bearer " + intent.getStringExtra("message_key");
        System.out.println(token);

        createStudent = findViewById(R.id.CreateStudent);
        createStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorDashboard.this, CreateNewStudent.class);
                System.out.println(token);
                intent.putExtra("message_key", token);
                startActivity(intent);
            }
        });
        viewStudent = findViewById(R.id.viewStudents);
        viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorDashboard.this, StudentsList.class);
                System.out.println(token);
                intent.putExtra("message_key", token);
                startActivity(intent);
            }
        });

        viewNotice = findViewById(R.id.viewNotice);
        viewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorDashboard.this, ViewNoticeList.class);
                System.out.println(token);
                intent.putExtra("message_key", token);
                startActivity(intent);
            }
        });
    }

    public void openCreateNotice(View view){
        Intent intent = new Intent(ProfessorDashboard.this, CreateNotice.class);
        System.out.println(token);
        intent.putExtra("message_key", token);
        startActivity(intent);
    }
}