package com.example.mpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdminMainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    ImageView profileImageView;
    EditText editTextName,editTextPhone,editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        profileImageView = findViewById(R.id.circleImageView);
        editTextName     = findViewById(R.id.user_profile);
        editTextPhone    = findViewById(R.id.phone_profile);
        editTextEmail    = findViewById(R.id.email_profile);
        getUserData();
    }
    private void getUserData() {
        String uid = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("MPay").document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserData userData = documentSnapshot.toObject(UserData.class);
                        UpdateUi(userData);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void UpdateUi(UserData userData) {
        editTextName.setText(userData.getName());
        editTextPhone.setText(userData.getPhone());
        editTextEmail.setText(userData.getEmail());
        Picasso.get().load(userData.getImageUrl()).into(profileImageView);


    }
}