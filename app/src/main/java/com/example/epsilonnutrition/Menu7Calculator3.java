package com.example.epsilonnutrition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class Menu7Calculator3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Calls XML Layout File
        return inflater.inflate(R.layout.fragment_menu_7_sub_3, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Epsilon Nutrition - TDEE Calculator");
        height3 = (EditText) getActivity().findViewById(R.id.height3);
        weight3 = (EditText) getActivity().findViewById(R.id.weight3);
        age3 = (EditText) getActivity().findViewById(R.id.age3);

        resultFemale3 = (TextView) getActivity().findViewById(R.id.resultFemale3);
        resultMale3 = (TextView) getActivity().findViewById(R.id.resultMale3);

        bmrFemale3 = (Button) getActivity().findViewById(R.id.bmrFemale3);
        bmrMale3 = (Button) getActivity().findViewById(R.id.bmrMale3);

        radioButton1 = (RadioButton) getActivity().findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) getActivity().findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) getActivity().findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) getActivity().findViewById(R.id.radioButton4);



        bmrMale3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v1){

                double weightTDEE;
                double heightTDEE;
                double ageTDEE;
                double bmrTDEE1;

                weightTDEE = Double.parseDouble(weight3.getText().toString());
                heightTDEE = Double.parseDouble(height3.getText().toString());
                ageTDEE = Double.parseDouble(age3.getText().toString());


//                bmrTDEE1 = ((10 * weightTDEE) + (6.25 * heightTDEE) - (5 * ageTDEE) + 5 );
                bmrTDEE1 = ((6.24 * weightTDEE) + (12.7 * heightTDEE) - (6.755 * ageTDEE) + 66.47 ) * getMultiplier();

                resultMale3.setText("TDEE = " +bmrTDEE1);
            }
        });

        bmrFemale3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v1){

                double weightTDEE;
                double heightTDEE;
                double ageTDEE;
                double bmrTDEE2;


                weightTDEE = Double.parseDouble(weight3.getText().toString());
                heightTDEE = Double.parseDouble(height3.getText().toString());
                ageTDEE = Double.parseDouble(age3.getText().toString());

//                bmrTDEE2 = ((10 * weightTDEE) + (6.25 * heightTDEE) - (5 * ageTDEE) + 161);
                bmrTDEE2 = ((4.35 * weightTDEE) + (4.7 * heightTDEE) - (4.7 * ageTDEE) + 655.1) * getMultiplier();
                resultFemale3.setText("TDEE = " +bmrTDEE2);
            }
        });

    }

    public double getMultiplier() {
        RadioGroup rg = getActivity().findViewById(R.id.radioGroup);
        int index = rg.indexOfChild(getActivity().findViewById(rg.getCheckedRadioButtonId()));
        switch(index) {
            case 0:
                return 1.2;
            case 1:
                return 1.375;
            case 2:
                return 1.5;
            case 3:
                return 1.725;
        }
        return 1.2;
    }

    private EditText height3;
    private EditText weight3;
    private EditText age3;

    private TextView resultFemale3;
    private TextView resultMale3;

    private Button bmrFemale3;
    private Button bmrMale3;

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;


}