package com.jgraycar.calorieconverter;

/**
 * Created by Joel on 2/5/16.
 */
public class PhysicalActivity {
    public double calorieConversion;
    public String unitType;

    public PhysicalActivity(double calorieConversion, String unitType) {
        this.calorieConversion = calorieConversion;
        this.unitType = unitType;
    }

    public double numCalories(double numActivityDone) {
        return calorieConversion * numActivityDone;
    }

    public double numActivity(double numCalories) {
        return numCalories / calorieConversion;
    }

    public double convertActivity(String activity, double amount) {
        PhysicalActivity other = MainActivity.activities.get(activity);
        double numCalories = other.calorieConversion * amount;
        return numActivity(numCalories);
    }
}
