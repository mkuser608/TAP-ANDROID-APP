package com.loginui.tapbit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GlobalVariable {


    public static String link="http://10.0.2.2:8080/";


    public static class EducationalHistoryDashBoard extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_educational_history_dash_board);
        }
    }
}
