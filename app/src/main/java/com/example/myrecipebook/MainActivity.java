package com.example.myrecipebook;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "recipe" ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.loading_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();



            CollectionReference recipe = db.collection("recipe");

            db.collection("recipe").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                                id.add(document.getId());
                                name.add(document.getString("name"));

                        }
                        createList();
                        dialog.dismiss();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }

                }
            });




    }

    void createList(){
        ItemData adapter = new ItemData(this,name,id);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"Youclicked"+id.get(i));
                Intent intent = new Intent(getBaseContext(),RecipeView.class);
                intent.putExtra("id",id.get(i));
                startActivity(intent);
            }
        });

    }
}