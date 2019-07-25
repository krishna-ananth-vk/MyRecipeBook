package com.example.myrecipebook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Ingredients  extends ArrayAdapter {

    Activity context;
    ArrayList<String> ingredients = new ArrayList<>();
    public Ingredients(Activity context, ArrayList<String> ingredients) {
        super(context, R.layout.ingredients_list,ingredients);

        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.ingredients_list,null,true);


        TextView ingredient = (TextView)rowView.findViewById(R.id.ingredient);
        ingredient.setText(ingredients.get(position));



        return rowView ;
    }
}
