package com.example.myrecipebook;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class ItemData extends ArrayAdapter {

    private Activity context;
    String TAG="click";
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> id= new ArrayList<String>();

    public ItemData(Activity context, ArrayList<String> names, ArrayList<String> id) {
        super(context, R.layout.cardlist,names);

        this.context = context;
        this.names = names;
        this.id = id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.cardlist,null,true);

        TextView name = (TextView) rowView.findViewById(R.id.itemName);

        name.setText(names.get(position));

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "you clicked"+id.get(position));
//            }
//        });


        return rowView;
    }
}
