package com.example.socialchatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btRegister  = findViewById(R.id.btRegister);
        tvLogin     = findViewById(R.id.tvLogin);
        btRegister.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btRegister:
                Intent intent   = new Intent(LoginActivity.this,RegisterActivity.class);
                Pair[] pairs    = new Pair[1];
                pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent,activityOptions.toBundle());
                break;
        }
    }
}
