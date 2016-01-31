package com.jgraycar.calorieconverter;

import java.text.DecimalFormat;
import java.util.HashMap;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Spinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

public class ConvertCalories extends AppCompatActivity implements OnItemSelectedListener {
    TextView activityUnitType;
    TextView calorieDisplay;
    EditText numActivity;
    Spinner activityTypeSpinner;
    ListView activityConversionsList;

    public static final HashMap<String, Double> calorieConversions;
    static {
        calorieConversions = new HashMap<String, Double>();
        calorieConversions.put("Pushups", 100.0 / 350.0);
        calorieConversions.put("Situps", 0.5);
        calorieConversions.put("Jumping Jacks", 10.0);
        calorieConversions.put("Jogging", 100.0 / 12.0);
    }

    public static final HashMap<String, String> activityUnits;
    static {
        activityUnits = new HashMap<String, String>();
        activityUnits.put("Pushups", "reps");
        activityUnits.put("Situps", "reps");
        activityUnits.put("Jumping Jacks", "minutes");
        activityUnits.put("Jogging", "minutes");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_calories);

        activityTypeSpinner = (Spinner) findViewById(R.id.spinner);
        activityTypeSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        activityTypeSpinner.setAdapter(adapter);

        numActivity = (EditText) findViewById(R.id.num_activity);
        numActivity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // Update displays for other activities
                updateDisplay();
            }
        });

        activityUnitType = (TextView) findViewById(R.id.activity_type_textView);
        calorieDisplay = (TextView) findViewById(R.id.calorie_count_textView);

        activityConversionsList = (ListView) findViewById(R.id.activity_conversions_listView);
        Resources res = getResources();
        ActivityArrayAdapter listAdapter = new ActivityArrayAdapter(this,
                res.getStringArray(R.array.activities_array), numActivity, activityTypeSpinner);
        activityConversionsList.setAdapter(listAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String activity = (String) parent.getItemAtPosition(pos);
        activityUnitType.setText(activityUnits.get(activity) + " of");
        updateDisplay();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void updateDisplay() {
        String activity = activityTypeSpinner.getSelectedItem().toString();

        String numActivityDoneStr = numActivity.getText().toString();
        double numActivityDone = 0;
        if (!numActivityDoneStr.equals("")) {
            numActivityDone = Double.parseDouble(numActivityDoneStr);
        }

        double calorieConversion = calorieConversions.get(activity);
        String calories = new DecimalFormat("#,###.##").format(calorieConversion * numActivityDone);
        calorieDisplay.setText(calories);
        activityConversionsList.invalidateViews();
    }
}
