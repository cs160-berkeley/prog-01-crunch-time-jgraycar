package com.jgraycar.calorieconverter;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Joel on 2/4/16.
 */
public class CalorieArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final EditText numCalories;
    private final SharedPreferences settings;

    public CalorieArrayAdapter(Context context, String[] values,
                                EditText numCalories, SharedPreferences settings) {
        super(context, R.layout.activity_list_row, values);
        this.context = context;
        this.values = values;
        this.numCalories = numCalories;
        this.settings = settings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int weight = settings.getInt("weight", 150);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list_row, parent, false);
        TextView activityTextView = (TextView) rowView.findViewById(R.id.list_item_activity_textView);
        String activity = values[position];

        activityTextView.setText(activity);

        TextView amountTextView = (TextView) rowView.findViewById(R.id.list_item_amount_textView);

        String numCalorieStr = numCalories.getText().toString();
        double numCalorie = 0;
        try {
            numCalorie = Double.parseDouble(numCalorieStr);
        } catch (NumberFormatException e) {
        }

        double numActivity= MainActivity.activities.get(activity).numActivity(numCalorie, weight);
        String converted = new DecimalFormat("#,###.##").format(numActivity);

        amountTextView.setText(converted + " " + MainActivity.activities.get(activity).unitType);

        return rowView;
    }
}
