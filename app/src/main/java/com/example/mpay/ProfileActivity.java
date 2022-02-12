package com.example.mpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    DrawerLayout drawerLayout;
    ImageView bt_menu;
    RecyclerView recyclerView;

    ImageView profileImageView;
    EditText editTextName,editTextPhone,editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_layout);
        bt_menu      = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.menu_recyclerView);

        profileImageView = findViewById(R.id.circleImageView);
        editTextName     = findViewById(R.id.user_profile);
        editTextPhone    = findViewById(R.id.phone_profile);
        editTextEmail    = findViewById(R.id.email_profile);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Dra_menu_adapter dra_menu_adapter = new Dra_menu_adapter(UserActivity.menuList,this);
        recyclerView.setAdapter(dra_menu_adapter);
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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
        //Glide.with(ProfileActivity.this).load(userData.getImageUrl()).into(profileImageView);


    }

    @Override
    protected void onPause() {
        super.onPause();
        UserActivity.closeDrawer(drawerLayout);
    }
}
