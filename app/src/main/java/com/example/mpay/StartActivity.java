package com.example.mpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    if(firebaseAuth.getCurrentUser()==null){
                        Intent intent = new Intent(StartActivity.this,AdminAndUser.class);
                        startActivity(intent);

                    }
                    else if(firebaseAuth.getUid().equals("maAXQUo10OMpeYHSK8iWQEDmio02")){
                        Intent intent = new Intent(StartActivity.this,AdminMainActivity.class);
                        startActivity(intent);

                    }
                    else
                    {
                        Intent intent = new Intent(StartActivity.this,UserActivity.class);
                        startActivity(intent);

                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }




}