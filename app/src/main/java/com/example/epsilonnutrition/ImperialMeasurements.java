package com.example.epsilonnutrition;

import java.util.HashMap;

/*
Maybe I need to make a seperate class for each measurement unit,
because I need to take into account the conversion factors
 */
public class ImperialMeasurements {
    HashMap<String, String> measurements;

    ImperialMeasurements(){
        //Create the hashtable
        measurements = new HashMap<String, String>();

        //Insert appropriate data
        measurements.put("teaspoon", "tsp");
        measurements.put("tablespoon", "tbsp");
        measurements.put("fluid ounce", "fl oz");
    }

}
