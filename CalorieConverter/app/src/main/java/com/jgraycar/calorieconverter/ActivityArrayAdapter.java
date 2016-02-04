package com.jgraycar.calorieconverter;

import java.text.DecimalFormat;
import java.util.HashMap;

import android.content.Context;
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

    public ActivityArrayAdapter(Context context, String[] values,
                                EditText numActivity, Spinner activityTypeSpinner) {
        super(context, R.layout.activity_list_row, values);
        this.context = context;
        this.values = values;
        this.numActivity = numActivity;
        this.activityTypeSpinner = activityTypeSpinner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        String currentActivity = activityTypeSpinner.getSelectedItem().toString();
        double itemActivityConversion = MainActivity.calorieConversions.get(activity);
        double currentActivityConversion = MainActivity.calorieConversions.get(currentActivity);
        double numCalories = currentActivityConversion * numActivityDone;
        String converted = new DecimalFormat("#,###.##").format(numCalories / itemActivityConversion);

        amountTextView.setText(converted + " " + MainActivity.activityUnits.get(activity));

        return rowView;
    }
}
