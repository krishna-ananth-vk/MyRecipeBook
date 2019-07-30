package com.example.myrecipebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends Activity {
    EditText email,password;
    FirebaseAuth mAuth;
    String e,p;
    Button signUP;
    TextView loginHere;

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
                            Toast.LENGTH_LONG).show();
                }
                else {
                    signUpUser(e,p);

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

    void signUpUser(String e, String p){
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

}
