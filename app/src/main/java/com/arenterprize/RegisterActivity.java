package com.arenterprize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText RgFullName, Rgemail, RgPAss, RgPhone, RgCountry;
    private ProgressBar progressBar;
    private Button RegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RgFullName = (EditText) findViewById(R.id.rgfullname);
        Rgemail = (EditText) findViewById(R.id.rgemail);
        RgPAss = (EditText) findViewById(R.id.rgpass);
        RgPhone = (EditText) findViewById(R.id.rgphone);
        RgCountry = (EditText) findViewById(R.id.rgcountry);
        progressBar = (ProgressBar) findViewById(R.id.reprogressbar);
        RegisterBtn = (Button) findViewById(R.id.rgregister);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
    }

    public void RegisterUser() {
        final String name = RgFullName.getText().toString().trim();
        final String email = Rgemail.getText().toString().trim();
        String pass = RgPAss.getText().toString().trim();
        final String phone = RgPhone.getText().toString().trim();
        final String country = RgCountry.getText().toString().trim();

        if (name.isEmpty()) {
            RgFullName.setError("Full name is required");
            RgFullName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            Rgemail.setError("email is required");
            Rgemail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            RgPAss.setError("password is required");
            RgPAss.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            RgPhone.setError("phone is required");
            RgPhone.requestFocus();
            return;
        }
        if (country.isEmpty()) {
            RgCountry.setError("country is required");
            RgCountry.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Rgemail.setError("email is not valid");
            Rgemail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String current_user_id;
                            final DatabaseReference customerRef;
                            current_user_id = mAuth.getCurrentUser().getUid();
                            customerRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id);

                            HashMap usermap = new HashMap();
                            usermap.put("name", name);
                            usermap.put("email", email);
                            usermap.put("phone", phone);
                            usermap.put("country", country);

                            User user = new User();
                            user.setname(name);
                            user.setEmail(email);
                            user.setPhone(phone);
                            user.setCountry(country);
                            customerRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account Created Succesfully.", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
