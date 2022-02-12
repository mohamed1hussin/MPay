package com.example.mpay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mpay.databinding.ActivityAdminAndUserBinding;
import com.example.mpay.databinding.ActivityRegisterBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    Uri imageUri = null;
    String name;
    String phone;
    String email;
    String password;

    ImageView imageView;
    EditText editTextName,editTextPhone,editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imageView = findViewById(R.id.imageProfileRegister);
        editTextName = findViewById(R.id.registerName);
        editTextPhone = findViewById(R.id.registerPhone);
        editTextEmail = findViewById(R.id.registerEmail);
        editTextPassword = findViewById(R.id.registerPassword);
    }

    public void imagePick(View view) {
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
        else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(RegisterActivity.this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {

        if(imageUri==null){
            Toast.makeText(this, "Image required", Toast.LENGTH_SHORT).show();
            return;
        }

        name = editTextName.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(this, "Name Required", Toast.LENGTH_SHORT).show();
            return;
        }
        phone = editTextPhone.getText().toString();
        if(phone.isEmpty()){
            Toast.makeText(this, "Phone Required", Toast.LENGTH_SHORT).show();
            return;
        }
        email = editTextEmail.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(this, "Email Required", Toast.LENGTH_SHORT).show();
            return;
        }
        password = editTextPassword.getText().toString();
        if(password.isEmpty()){
            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateUserAccount();
    }

    private void CreateUserAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UploadImageInStorage();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadImageInStorage() {
        storageRef.child("MPayUsers")
                .child(firebaseAuth.getUid())
                .putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getImageUrl();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImageUrl() {
        storageRef.child("MPayUsers")
                .child(firebaseAuth.getUid())
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                UploadDataToFirestore(imageUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadDataToFirestore(String imageUrl) {
        Map<String,Object>map = new HashMap<>();
        map.put("name",name);
        map.put("phone",phone);
        map.put("email",email);
        map.put("ImageUrl",imageUrl);
       
        firebaseFirestore.collection("MPay").document(firebaseAuth.getUid())
                .set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                finish();
                Intent intent = new Intent(RegisterActivity.this,UserActivity.class);
                startActivity(intent);
                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}