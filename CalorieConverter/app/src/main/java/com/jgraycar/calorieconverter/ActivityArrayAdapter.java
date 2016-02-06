package com.jgraycar.calorieconverter;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

/**
 * Created by Joel on 1/31/16.
 */
public class ActivityArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final EditText numActivity;
    private final Spinner activityTypeSpinner;
    private final SharedPreferences settings;

    public ActivityArrayAdapter(Context context, String[] values, EditText numActivity,
                                 Spinner activityTypeSpinner, SharedPreferences settings) {
        super(context, R.layout.activity_list_row, values);
        this.context = context;
        this.values = values;
        this.numActivity = numActivity;
        this.activityTypeSpinner = activityTypeSpinner;
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

        String numActivityDoneStr = numActivity.getText().toString();
        double numActivityDone = 0;
        if (!numActivityDoneStr.equals("")) {
            numActivityDone = Double.parseDouble(numActivityDoneStr);
        }

        PhysicalActivity physActivity = MainActivity.activities.get(activity);

        String currentActivity = activityTypeSpinner.getSelectedItem().toString();
        double convertedNumActivity = physActivity.convertActivity(currentActivity, numActivityDone);
        String converted = new DecimalFormat("#,###.##").format(convertedNumActivity);

        amountTextView.setText(converted + " " + physActivity.unitType);

        return rowView;
    }
}
