package com.example.socialchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlayout;
    private Animation animation;
    private Button signUp;
    private EditText username,email,password,confirm_password;
    private ProgressDialog progressDialog;
    String mUsername,mEmail,mPassword,mConfirm_password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findIds();

    }

    private void findIds() {
        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);

        username = findViewById(R.id.et_username);
        email = findViewById(R.id.et_email);
        password= findViewById(R.id.et_password);
        confirm_password= findViewById(R.id.et_retype_password);
        confirm_password= findViewById(R.id.et_retype_password);

        mAuth = FirebaseAuth.getInstance();


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering user...");

        signUp= findViewById(R.id.bt_signup);
        signUp.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_signup:
                validation();
                break;
        }
    }

    private void validation() {

        mUsername=username.getText().toString().trim();
        mEmail=email.getText().toString().trim();
        mPassword=password.getText().toString().trim();
        mConfirm_password=confirm_password.getText().toString().trim();

//        if(mUsername.isEmpty()){
//            username.setError("Enter username.");
//            username.setFocusable(true);
//        }
        if (mEmail.isEmpty()){
            email.setError("Enter email address.");
            email.setFocusable(true);
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError("Invalid email.");
            email.setFocusable(true);
        }
        else if (mPassword.isEmpty()){
            password.setError("Enter password");
            password.setFocusable(true);
        }
        else if (mPassword.length()<6){
            password.setError("Password length must be 6 characters.");
            password.setFocusable(true);
        }
        else if (!mPassword.equals(mConfirm_password)){
            confirm_password.setError("Password Not matching");
            confirm_password.setFocusable(true);
        }
        else {
            registerUser(mEmail,mPassword);
        }
    }

    private void registerUser(String mEmail, String mPassword) {

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success,
                            progressDialog.dismiss();
                            Log.d("success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Register Email:"+user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w("fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
