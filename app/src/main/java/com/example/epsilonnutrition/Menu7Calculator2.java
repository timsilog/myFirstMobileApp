package com.example.epsilonnutrition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Menu7Calculator2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Calls XML Layout File
        return inflater.inflate(R.layout.fragment_menu_7_sub_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Epsilon Nutrition - BMR Calculator");
        height2 = (EditText) getActivity().findViewById(R.id.height2);
        weight2 = (EditText) getActivity().findViewById(R.id.weight2);
        age2 = (EditText) getActivity().findViewById(R.id.age2);

        resultFemale = (TextView) getActivity().findViewById(R.id.resultFemale);
        resultMale = (TextView) getActivity().findViewById(R.id.resultMale);

        bmrFemale = (Button) getActivity().findViewById(R.id.bmrFemale);
        bmrMale = (Button) getActivity().findViewById(R.id.bmrMale);

        bmrMale.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v1){
                double weightBMR;
                double heightBMR;
                double ageBMR;
                double bmr1;

                weightBMR = Double.parseDouble(weight2.getText().toString());
                heightBMR = Double.parseDouble(height2.getText().toString());
                ageBMR = Double.parseDouble(age2.getText().toString());

                bmr1 = ((6.24 * weightBMR) + (12.7 * heightBMR) - (6.755 * ageBMR) + 66.47);

                resultMale.setText("BMR = " +bmr1);
            }
        });

        bmrFemale.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v1){
                double weightBMR;
                double heightBMR;
                double ageBMR;
                double bmr2;

                weightBMR = Double.parseDouble(weight2.getText().toString());
                heightBMR = Double.parseDouble(height2.getText().toString());
                ageBMR = Double.parseDouble(age2.getText().toString());

                bmr2 = ((4.35 * weightBMR) + (4.7 * heightBMR) - (4.7 * ageBMR) + 655.1);

                resultFemale.setText("BMR = " +bmr2);
            }
        });

    }

    private EditText height2;
    private EditText weight2;
    private EditText age2;

    private TextView resultFemale;
    private TextView resultMale;

    private Button bmrFemale;
    private Button bmrMale;

}