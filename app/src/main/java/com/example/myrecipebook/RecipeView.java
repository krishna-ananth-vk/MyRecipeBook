package com.example.myrecipebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class RecipeView extends Activity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id,title,chef,time;
    String TAG = "---------------------";
    ArrayList<String> ing = new ArrayList<>();
    ListView listView;
    TextView titleview,chefview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecipe);
        listView = (ListView)findViewById(R.id.ingredientsList);
        titleview = (TextView)findViewById(R.id.title);
        chefview = (TextView)findViewById(R.id.chef);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.loading_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();

        id = getIntent().getStringExtra("id");
        DocumentReference recipe = db.collection("recipe").document(id);
        recipe.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        title = document.getString("name");
                        chef = document.getString("chef");
                        titleview.setText(title);
                        chefview.setText("Chef : "+ chef);

                        ing = (ArrayList<String>)document.get("ingredients");
                        createList();
                        dialog.dismiss();


                    }
                }
            }
        });


    }
    public  void  createList(){
        Ingredients adapter = new Ingredients(this,ing);
        listView.setAdapter(adapter);


    }
}
