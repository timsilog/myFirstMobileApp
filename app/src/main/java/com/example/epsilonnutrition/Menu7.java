package com.example.epsilonnutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Menu7 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Calls XML Layout File
        View view = inflater.inflate(R.layout.fragment_menu_7, container, false);

        Button button4 = view.findViewById(R.id.button4);
        Button button5 = view.findViewById(R.id.button5);
        Button button6 = view.findViewById(R.id.button6);


        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v1) {
//                Intent in = new Intent(getActivity(),Menu7Calculator1.class);
//                startActivity(in);
                Fragment fragment = new Menu7Calculator1();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v2) {
//                Intent in = new Intent(getActivity(),Menu7Calculator2.class);
//                startActivity(in);
                Fragment fragment = new Menu7Calculator2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v3) {
//                Intent in = new Intent(getActivity(),Menu7Calculator3.class);
//                startActivity(in);
                Fragment fragment = new Menu7Calculator3();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Header Title
        getActivity().setTitle("Epsilon Nutrition - Calculator");

    }






}