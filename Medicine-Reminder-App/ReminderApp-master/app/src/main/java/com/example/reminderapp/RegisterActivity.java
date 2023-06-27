package com.example.reminderapp;

import static com.example.reminderapp.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{
    TextView alreadyhaveaccount;
    String usertype;
    ProgressDialog progressDialog;
    Button btnRegister,btnUser,btnAdmin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText inputEmail, inputPassword, inputUsername, inputConfirmPassword;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alreadyhaveaccount=findViewById(R.id.alreadyhaveaccount);
        inputEmail=findViewById(R.id.inputEmail);
        inputUsername=findViewById(R.id.inputUsername);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);
        btnUser=findViewById(R.id.btnUser);
        btnAdmin=findViewById(R.id.btnAdmin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("People");
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertype = "admin";
                btnAdmin.setBackgroundColor(getResources().getColor(R.color.selected));
                btnUser.setBackgroundColor(getResources().getColor(R.color.notselected));
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertype = "user";
                btnUser.setBackgroundColor(getResources().getColor(R.color.selected));
                btnAdmin.setBackgroundColor(getResources().getColor(R.color.notselected));
            }
        });
    btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PerformAuth();
            }
        });
    }
    private void PerformAuth() {
        String email = inputEmail.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputConfirmPassword.getText().toString();
        if(username.length() < 6){
            inputUsername.setError("Username must be of minimum 7 characters");
        }
        else if (!email.matches(emailPattern)) {
            inputEmail.setError("Please Enter Valid Email");
        }
        else if (password.isEmpty() || password.length() < 7) {
            inputPassword.setError("Password must be of minimum 7 characters");
        }
        else if (!password.equals(confirmpassword)) {
            inputConfirmPassword.setError("Password does not match");
        }
        else {
            progressDialog.setMessage("Please Wait While Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Users us = new Users(username,email,password,confirmpassword,usertype);
                            reference.child(usertype).child(email).setValue(us)
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Data not Registered\n"+e.toString(),Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisterActivity.this,"Data inserted!!!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Already Registered \n"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}