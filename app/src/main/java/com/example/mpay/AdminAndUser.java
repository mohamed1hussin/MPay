package com.example.mpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mpay.databinding.ActivityAdminAndUserBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminAndUser extends AppCompatActivity {
    ActivityAdminAndUserBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAndUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAndUser.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(View view) {
        String email = binding.loginEmail.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(this, "Email Required", Toast.LENGTH_SHORT).show();
            return;
         }
        String password = binding.loginPassword.getText().toString().trim();
        if(password.isEmpty()){
            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(firebaseAuth.getUid().equals("maAXQUo10OMpeYHSK8iWQEDmio02")){
                            Intent intent = new Intent(AdminAndUser.this,AdminMainActivity.class);
                            startActivity(intent);
                            Toast.makeText(AdminAndUser.this, "Login Success", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                        Intent intent = new Intent(AdminAndUser.this,UserActivity.class);
                        startActivity(intent);
                        Toast.makeText(AdminAndUser.this, "Login Success", Toast.LENGTH_LONG).show();
                        finish();}
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAndUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}