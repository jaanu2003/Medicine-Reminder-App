package com.example.reminderapp;

import static com.example.reminderapp.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class LoginActivity extends AppCompatActivity{
    TextView textviewsignup, textView3;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String usertype, username, email, password;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText inputEmail, inputPassword, inputUsername;
    Button btnLogin, googlebutton, facebookbutton, btnUser, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        textviewsignup = findViewById(id.textviewsignup);
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        googlebutton = findViewById(R.id.googlebutton);
        facebookbutton = findViewById(R.id.facebookbutton);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        textView3 = findViewById(id.textView3);
        btnLogin = findViewById(R.id.btnLogin);
        btnUser = findViewById(R.id.btnUser);
        btnAdmin = findViewById(R.id.btnAdmin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("People");
        textviewsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, GoogleSigninActivity.class);
                startActivity(intent);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                finish();
            }
        });
    }


    public void login(View view) {
        if (usertype!=null || !usertype.isEmpty()) {
            username = inputUsername.getText().toString();
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            reference.child(usertype).child(username)
                    .addListenerForSingleValueEvent(listener);
        } else {
            Toast.makeText(this, "Select User", Toast.LENGTH_SHORT).show();
        }
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                String pass = snapshot.child("password")
                        .getValue(String.class);
                String mail = snapshot.child("email")
                        .getValue(String.class);
                if (pass.equals(password) && mail.equals(email)) {
                    Intent intent = new Intent(LoginActivity.this, AddingPage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "You are not a registered User\n", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }
    };
        private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, AddingPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        }
}

