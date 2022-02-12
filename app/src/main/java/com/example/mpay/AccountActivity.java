package com.example.mpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

public class AccountActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView bt_menu;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        drawerLayout = findViewById(R.id.drawer_layout);
        bt_menu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.menu_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Dra_menu_adapter dra_menu_adapter = new Dra_menu_adapter(UserActivity.menuList,this);
        recyclerView.setAdapter(dra_menu_adapter);
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        UserActivity.closeDrawer(drawerLayout);
    }
}