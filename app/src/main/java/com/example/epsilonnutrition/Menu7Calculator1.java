package com.example.epsilonnutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Menu7Calculator1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Calls XML Layout File
        return inflater.inflate(R.layout.fragment_menu_7_sub_1, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Epsilon Nutrition - BMI Calculator");

        height1 = (EditText) getActivity().findViewById(R.id.height1);
        weight1 = (EditText) getActivity().findViewById(R.id.weight1);
        result1 = (TextView) getActivity().findViewById(R.id.result);


//        Button backButton1 = getActivity().findViewById(R.id.backButton1);
        Button calcButton = getActivity().findViewById(R.id.calc);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI(v);
            }
        });
//        backButton1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(getActivity().getApplicationContext(),Menu7.class);
//                startActivity(in);
//            }
//        });


    }

    private EditText height1;
    private EditText weight1;
    private TextView result1;


    public void calculateBMI(View v) {

        String heightStr = height1.getText().toString();
        String weightStr = weight1.getText().toString();

        if (heightStr != null && !"".equals(heightStr)
                && weightStr != null  &&  !"".equals(weightStr)) {
            float heightValue = Float.parseFloat(heightStr);
            float weightValue = Float.parseFloat(weightStr);

            float bmi = 730 * weightValue / (heightValue * heightValue);

            displayBMI(bmi);
        }
    }

    private void displayBMI(float bmi) {
        String bmiLabel = "";

        bmiLabel = bmi + " is your BMI";
        result1.setText(bmiLabel);

        if (bmi == 0){
            System.out.println("You are severely underweight");
        }
        if (bmi == 1) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 2) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 3){
            System.out.println("You are severely underweight");
        }
        if (bmi == 4) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 5) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 6) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 7) {
            System.out.println("You are severely underweight");
        }
        if (bmi < 8) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 9) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 10) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 11) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 12) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 13) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 14) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 15) {
            System.out.println("You are severely underweight");
        }
        if (bmi == 16) {
            System.out.println("You are underweight");
        }
        if (bmi == 17) {
            System.out.println("You are underweight");
        }
        if (bmi == 18) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 19) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 20) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 21) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 22) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 23) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 24) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 25) {
            System.out.println("You are a healthy weight");
        }
        if (bmi == 26) {
            System.out.println("You are overweight");
        }
        if (bmi == 27) {
            System.out.println("You are overweight");
        }
        if (bmi == 28) {
            System.out.println("You are overweight");
        }
        if (bmi == 29) {
            System.out.println("You are overweight");
        }
        if (bmi == 30) {
            System.out.println("You are overweight");
        }
        if (bmi == 31) {
            System.out.println("You are obese");
        }
        if (bmi == 32) {
            System.out.println("You are obese");
        }
        if (bmi == 33) {
            System.out.println("You are obese");
        }
        if (bmi == 34) {
            System.out.println("You are obese");
        }
        if (bmi == 35) {
            System.out.println("You are obese");
        }
        if (bmi >= 36) {
            System.out.println("You are morbidly obese");
        }

    }
}








