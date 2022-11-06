package com.loginui.tapbit.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.loginui.tapbit.R;

public class Introductory extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        getSupportActionBar().hide();

        btn = findViewById(R.id.getStartedButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent( Introductory.this, Login.class);
                startActivity(intent);
            }
        });
    }
}