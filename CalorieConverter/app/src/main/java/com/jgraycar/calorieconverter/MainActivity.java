package com.jgraycar.calorieconverter;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.net.Uri;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements ConvertActivity.OnFragmentInteractionListener,
        ConvertCalories.OnFragmentInteractionListener {

    public static final HashMap<String, Double> calorieConversions;
    static {
        calorieConversions = new HashMap<String, Double>();
        calorieConversions.put("Pushups", 100.0 / 350.0);
        calorieConversions.put("Situps", 0.5);
        calorieConversions.put("Squats", 100.0 / 225.0);
        calorieConversions.put("Leg Lifts", 4.0);
        calorieConversions.put("Planks", 4.0);
        calorieConversions.put("Jumping Jacks", 10.0);
        calorieConversions.put("Pullups", 1.0);
        calorieConversions.put("Cycling", 100.0 / 12.0);
        calorieConversions.put("Walking", 5.0);
        calorieConversions.put("Jogging", 100.0 / 12.0);
        calorieConversions.put("Swimming", 100.0 / 13.0);
        calorieConversions.put("Stair Climbing", 100.0 / 15.0);
    }

    public static final HashMap<String, String> activityUnits;
    static {
        activityUnits = new HashMap<String, String>();
        activityUnits.put("Pushups", "reps");
        activityUnits.put("Situps", "reps");
        activityUnits.put("Squats", "reps");
        activityUnits.put("Leg Lifts", "minutes");
        activityUnits.put("Planks", "minutes");
        activityUnits.put("Jumping Jacks", "minutes");
        activityUnits.put("Pullups", "reps");
        activityUnits.put("Cycling", "minutes");
        activityUnits.put("Walking", "minutes");
        activityUnits.put("Jogging", "minutes");
        activityUnits.put("Swimming", "minutes");
        activityUnits.put("Stair Climbing", "minutes");
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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

}
