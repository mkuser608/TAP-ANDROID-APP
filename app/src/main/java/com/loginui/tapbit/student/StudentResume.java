package com.loginui.tapbit.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.loginui.tapbit.R;

public class StudentResume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_resume);

        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("token"));
        System.out.println(intent.getStringExtra("rollNo"));
    }
}