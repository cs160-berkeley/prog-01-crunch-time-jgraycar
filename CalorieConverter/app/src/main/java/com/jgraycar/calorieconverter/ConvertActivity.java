package com.jgraycar.calorieconverter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConvertActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConvertActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvertActivity extends UpdateableFragment implements OnItemSelectedListener {
    TextView activityUnitType;
    TextView calorieDisplay;
    EditText numActivity;
    Spinner activityTypeSpinner;
    ListView activityConversionsList;


    private OnFragmentInteractionListener mListener;

    public ConvertActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConertActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static ConvertActivity newInstance() {
        ConvertActivity fragment = new ConvertActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_convert_activity, container, false);
        activityTypeSpinner = (Spinner) view.findViewById(R.id.spinner);
        activityTypeSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.activities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        activityTypeSpinner.setAdapter(adapter);

        numActivity = (EditText) view.findViewById(R.id.num_activity);
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

        activityUnitType = (TextView) view.findViewById(R.id.activity_type_textView);
        calorieDisplay = (TextView) view.findViewById(R.id.calorie_count_textView);

        activityConversionsList = (ListView) view.findViewById(R.id.activity_conversions_listView);
        Resources res = getResources();
        ActivityArrayAdapter listAdapter = new ActivityArrayAdapter(view.getContext(),
                res.getStringArray(R.array.activities_array), numActivity, activityTypeSpinner,
                getActivity().getPreferences(Context.MODE_PRIVATE));
        activityConversionsList.setAdapter(listAdapter);
        return view;
    }

    public void updateDisplay() {
        String activity = activityTypeSpinner.getSelectedItem().toString();

        String numActivityDoneStr = numActivity.getText().toString();
        double numActivityDone = 0;
        try {
            numActivityDone = Double.parseDouble(numActivityDoneStr);
        } catch (NumberFormatException e) {
        }

        int weight = getActivity().getPreferences(Context.MODE_PRIVATE).getInt("weight", 150);
        double caloriesBurned = MainActivity.activities.get(activity).numCalories(numActivityDone, weight);
        String calories = new DecimalFormat("#,###.##").format(caloriesBurned);
        calorieDisplay.setText(calories);
        activityConversionsList.invalidateViews();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String activity = (String) parent.getItemAtPosition(pos);
        activityUnitType.setText(MainActivity.activities.get(activity).unitType + " of");
        updateDisplay();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
