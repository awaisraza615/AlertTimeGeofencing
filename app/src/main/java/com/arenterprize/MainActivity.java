package com.arenterprize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView LgForget , LgRegister;
    private EditText LgEmail , LgPassword;
    private ProgressBar progressBar;
    private Button LoginBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LgRegister = (TextView) findViewById(R.id.lgregister);
        LgEmail = (EditText) findViewById(R.id.lgemail);
        LgPassword = (EditText) findViewById(R.id.lgpass);
        progressBar = (ProgressBar) findViewById(R.id.lgprogress);
        LoginBtn = (Button) findViewById(R.id.btnlogin);


        mAuth = FirebaseAuth.getInstance();
        LgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }
    public void LoginUser()
    {
        String email = LgEmail.getText().toString().trim();
        final String pass = LgPassword.getText().toString().trim();


        if (email.isEmpty()) {
            LgEmail.setError("email is required");
            LgEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            LgPassword.setError("password is required");
            LgPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}