package com.example.mpay;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Dra_menu_adapter extends RecyclerView.Adapter<Dra_menu_adapter.MenuHolder> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ArrayList<String>list_menu;
    Activity activity;

    public Dra_menu_adapter(ArrayList<String> list_menu, Activity activity) {
        this.list_menu = list_menu;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu,parent,false);
        MenuHolder menuHolder = new MenuHolder(view);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.textView.setText(list_menu.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                switch (position){
                    case 0:
                        activity.startActivity(new Intent(activity,UserActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:
                        activity.startActivity(new Intent(activity,ProfileActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        activity.startActivity(new Intent(activity,CheckActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 3:
                        activity.startActivity(new Intent(activity,AccountActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 4:
                        firebaseAuth.signOut();
                        if(firebaseAuth.getCurrentUser()==null){
                            activity.startActivity(new Intent(activity,AdminAndUser.class));
                            activity.finish();
                            }

                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_menu.size();
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        TextView textView;
         public MenuHolder(@NonNull View itemView) {
             super(itemView);
             textView = itemView.findViewById(R.id.menu_textView);
         }
     }
}
