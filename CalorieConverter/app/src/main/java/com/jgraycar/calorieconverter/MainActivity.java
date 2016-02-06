package com.jgraycar.calorieconverter;

import android.app.Dialog;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements ConvertActivity.OnFragmentInteractionListener,
        ConvertCalories.OnFragmentInteractionListener {

    SharedPreferences settings;

    public static final HashMap<String, PhysicalActivity> activities;
    static {
        activities = new HashMap<String, PhysicalActivity>();
        activities.put("Pushups", new PhysicalActivity(100.0 / 350.0, "reps"));
        activities.put("Situps", new PhysicalActivity(0.5, "reps"));
        activities.put("Squats", new PhysicalActivity(100.0 / 225.0, "reps"));
        activities.put("Leg Lifts", new PhysicalActivity(4.0, "minutes"));
        activities.put("Planks", new PhysicalActivity(4.0, "minutes"));
        activities.put("Jumping Jacks", new PhysicalActivity(10.0, "minutes"));
        activities.put("Pullups", new PhysicalActivity(1.0, "reps"));
        activities.put("Cycling", new PhysicalActivity(100.0 / 12.0, "minutes"));
        activities.put("Walking", new PhysicalActivity(5.0, "minutes"));
        activities.put("Jogging", new PhysicalActivity(100.0 / 12.0, "minutes"));
        activities.put("Swimming", new PhysicalActivity(100.0 / 13.0, "minutes"));
        activities.put("Stair Climbing", new PhysicalActivity(100.0 / 15.0, "minutes"));
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        settings = getPreferences(Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openWeightDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Fragment mCurrentFragment;

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0) {
                return ConvertActivity.newInstance();
            }
            return ConvertCalories.newInstance();
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Convert";
                case 1:
                    return "Plan";
            }
            return null;
        }
    }

    public void onFragmentInteraction(Uri uri){
    }

    public void openWeightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_weight, null);

        builder.setView(view);

        EditText weightEditText = (EditText) view.findViewById(R.id.weight_editText);
        int weight = settings.getInt("weight", 150);
        weightEditText.setText("");
        weightEditText.append(String.valueOf(weight));

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // All the fun happens in DialogClickListener
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setTitle("Set your weight");
        AlertDialog dialog = builder.create();
        dialog.show();
        Button theButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new DialogClickListener(dialog));
    }

    private class DialogClickListener implements View.OnClickListener {
        private final Dialog dialog;
        public DialogClickListener(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            // put your code here
            EditText weightEditText = (EditText) dialog.findViewById(R.id.weight_editText);
            String inputtedWeight = weightEditText.getText().toString();
            if (inputtedWeight.length() > 0){
                Editor editor = settings.edit();
                editor.putInt("weight", Integer.parseInt(inputtedWeight));
                editor.commit();
                ((UpdateableFragment) mSectionsPagerAdapter.getCurrentFragment()).updateDisplay();
                dialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
