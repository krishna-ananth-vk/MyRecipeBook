package com.example.myrecipebook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class List  extends ArrayAdapter {

    Activity context;
    ArrayList<String> itemList;
    public List(Activity context, ArrayList<String> itemList) {
        super(context, R.layout.listlayout,itemList);

        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listlayout,null,true);


        TextView item = (TextView)rowView.findViewById(R.id.item);
        item.setText(itemList.get(position));



        return rowView ;
    }
}
