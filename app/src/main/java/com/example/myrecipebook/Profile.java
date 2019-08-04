package com.example.myrecipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;




public class Profile extends Activity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView name,locaton;
    Button edit;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = findViewById(R.id.user_profile_name);
        locaton = findViewById(R.id.location_text);
        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("edit profile");
                startActivity(new Intent(Profile.this,EditProfile.class));
            }
        });
        uid = user.getUid();
        getUserInfo();
    }



    void getUserInfo(){

        final DocumentReference docRef = db.collection("user").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        name.setText(document.getString("name"));
                        locaton.setText(document.getString("location"));
                    }
                }
                else {
                    name.setText("Failed get data");
                    locaton.setText("Failed get data");
                }
            }
        });



    }
}

