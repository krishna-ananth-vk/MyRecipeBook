package com.example.myrecipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditProfile extends Activity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText name;
    Button save;
    Spinner location;
    String uid,n,c,loc;
    Locale[] locales = Locale.getAvailableLocales();
    ArrayList<String> countries = new ArrayList<String>();
    ArrayAdapter<String> countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        location = findViewById(R.id.country);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });
//        Spinner list
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        countries.add("!! choose your country");
        Collections.sort(countries);


        countryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countries);

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(countryAdapter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = findViewById(R.id.user_profile_name);

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
                        loc = document.getString("location");
                        if (loc != null){
                            int spinnerPosition = countryAdapter.getPosition(loc);
                            location.setSelection(spinnerPosition);
                        }


                    }
                }

            }
        });

    }

    void saveUserData(){
        n = name.getText().toString();
        c = location.getSelectedItem().toString();
        Map<String, Object> user = new HashMap<>();

        user.put("name",n);
        user.put("location",c);

        db.collection("user").document(uid).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            System.out.println("data updated");
                            startActivity(new Intent(EditProfile.this,Profile.class));
                            finish();
                        }
                        else {
                            System.out.println("data update failed");
                        }
                    }
                });
    }
}
