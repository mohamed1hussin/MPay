package com.example.mpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mpay.databinding.ActivityAdminAndUserBinding;
import com.example.mpay.databinding.ActivityUserBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    DrawerLayout drawerLayout;
    ImageView bt_menu;
    RecyclerView recyclerView;
    ImageView profile;
   static ArrayList<String>menuList=new ArrayList<>();

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        drawerLayout = findViewById(R.id.drawer_layout);
        bt_menu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.menu_recyclerView);
        profile = findViewById(R.id.menu_profile_image);

        menuList.clear();
        ItemOfMenu();

        Dra_menu_adapter dra_menu_adapter = new Dra_menu_adapter(menuList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dra_menu_adapter);

        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    private void getUserData() {
        String uid = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("MPay").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserData userData = documentSnapshot.toObject(UserData.class);
                        //Picasso.get().load(userData.getImageUrl()).into(profile);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private void ItemOfMenu() {
        menuList.add("Home");
        menuList.add("Profile");
        menuList.add("Check");
        menuList.add("Account");
        menuList.add("Logout");
    }
}