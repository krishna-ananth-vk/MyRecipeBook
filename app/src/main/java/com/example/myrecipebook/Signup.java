package com.example.myrecipebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Signup extends Activity {
    EditText email,password;
    FirebaseAuth mAuth;


    String e,p,n,c,result,uid;
    Button signUP;
    TextView loginHere;
    Locale[] locales = Locale.getAvailableLocales();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> countries = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        mAuth = FirebaseAuth.getInstance();


    }



    @Override
    protected void onStart() {
        super.onStart();


        email = findViewById(R.id.email);

        password = findViewById(R.id.password);
        signUP = findViewById(R.id.signup);
        loginHere = findViewById(R.id.login);


        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e = email.getText().toString();
                p = password.getText().toString();

                if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
                    Toast.makeText(Signup.this, "email or password not provided",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    signUpUser(e,p,n,c);

                }

            }
        });

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });



    }

    void signUpUser(final String e, String p, final String na, String  c){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.login_dialog);
        final AlertDialog Signupdialog = builder.create();
        Signupdialog.show();


        mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = mAuth.getCurrentUser();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                            .setDisplayName(na)
                            .build();

                    user.updateProfile(request)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        System.out.println("updated data");
                                    }
                                    else {
                                        System.out.println("failed");
                                    }
                                }
                            });
                    startActivity(new Intent(Signup.this,MainActivity.class));
                    finish();
                }
                else {
                    Signupdialog.dismiss();
                    Toast.makeText(Signup.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String validateName(String name){
        if(TextUtils.isEmpty(name))
            return "Empty String as name";
        if(name.length()<=5)
            return "String Length must be greater than 5";
        return "true";
    }

}
