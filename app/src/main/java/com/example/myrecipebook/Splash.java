package com.example.myrecipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            startActivity(new Intent(this,MainActivity.class));
        }
        else {
            startActivity(new Intent(this,Login.class));
        }
        finish();
    }
}
