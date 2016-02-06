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

    public double numCalories(double numActivityDone, int weight) {
        double newConversion = calorieConversion;
        newConversion += 0.001 * (weight - 150);
        newConversion = Math.max(0.1, newConversion);
        return newConversion * numActivityDone;
    }

    public double numActivity(double numCalories, int weight) {
        double newConversion = calorieConversion;
        newConversion += 0.001 * (weight - 150);
        newConversion = Math.max(0.1, newConversion);
        return numCalories / newConversion;
    }

    public double convertActivity(String activity, double amount) {
        PhysicalActivity other = MainActivity.activities.get(activity);
        double numCalories = other.calorieConversion * amount;
        return numCalories / calorieConversion;
    }
}
