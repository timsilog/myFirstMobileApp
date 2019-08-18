package com.example.epsilonnutrition;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class AddRecipePage extends Fragment {
    //Declare variables and objects
    private static final String FILE_NAME = "epsilonNutritionApp_USER_RECIPES.txt";
    EditText editText_object;
    CheckBox checkBox_object;
    Switch switch_object;
    HashMap<String, Recipes> loadFile_hashMap = new HashMap<String, Recipes>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Calls XML Layout File
        return inflater.inflate(R.layout.page_add_recipes, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //***TO BE DELETED*** Drop Down Menu in Recipes Section
        /*
        //Drop Down Menu for "Add a Recipe Section"
        Spinner dropDownMenuMeasurements_addARecipe = (Spinner) getActivity().findViewById(R.id.dropDownMenuMeasurements);
        ArrayAdapter<CharSequence> arrayAdapter_object = ArrayAdapter.createFromResource(this,R.array.measurements_array, android.R.layout.simple_spinner_item);
        arrayAdapter_object.setDropDownViewResource(android.R.layout.simple_spinner_item);
        dropDownMenuMeasurements_addARecipe.setAdapter(arrayAdapter_object);

        dropDownMenuMeasurements_addARecipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                //Showcase to the user that there item has been selected
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(pos) + " has been selected!", Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });
        */

        getActivity().setTitle("Epsilon Nutrition - Add a Recipe");

        //Create save button
        Button saveButton = getActivity().findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view_object)
            {
                saveButton(view_object);
            }
        });

        //Create button object for activity 2
        Button actvity2_buttonObject = getActivity().findViewById(R.id.activity2Button);
        actvity2_buttonObject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view_object)
            {
                openActivity2();
            }
        });

        //Check to see if the optional switch is toggled
        Switch optionalNutritionalInformation_toggleButton = getActivity().findViewById(R.id.optionalNutritionalInformationEntry);
        optionalNutritionalInformation_toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Display the nutritonal information card
                    getActivity().findViewById(R.id.nutritionalInformation_cardView).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.nutritionalInformation_constraintLayout).setVisibility(View.VISIBLE);
                }
                else
                {
                    //Hide the nutritional information card
                    getActivity().findViewById(R.id.nutritionalInformation_cardView).setVisibility(View.INVISIBLE);
                    getActivity().findViewById(R.id.nutritionalInformation_constraintLayout).setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    /*
    @author Kamaljot Saini
        Purpose: This method opens recipes home page
     */
    public void openActivity2()
    {
//        Intent intent = new Intent(this, RecipesHomepage.class);
//        startActivity(intent);
        Fragment fragment = new RecipesHomepage();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    /*
    @author Kamaljot Saini
        Method invoked when "Save" button is clicked
        SIDE NOTE: The recipe name, ingredients, and serving size are all mandatory
        the rest is optional so perform appropriate exception handling
    */
    public void saveButton(View view)
    {
        try{
            //Create recipes object
            Recipes input_recipe;

            editText_object = getActivity().findViewById(R.id.addRecipeNameText);
            String temp_recipeName = editText_object.getText().toString();
            if(temp_recipeName.isEmpty())
            {
                //ERROR! User must enter "Recipe Name!"
                throw new Exception("ERROR! User must enter \"Ingredients!\"");
            }
            else
                editText_object.getText().clear();

            editText_object = getActivity().findViewById(R.id.addIngredientNameText);
            String temp_ingredientName = editText_object.getText().toString();
            if(temp_ingredientName.isEmpty())
                throw new Exception("ERROR! User must enter \"Ingredients!\"");
            else
                editText_object.getText().clear();

            editText_object = getActivity().findViewById(R.id.addServingSizeText);
            String temp_servingSizeSTRING = editText_object.getText().toString();
            int temp_servingSizeINT = Integer.parseInt(temp_servingSizeSTRING);
            if(temp_servingSizeSTRING.isEmpty())
                throw new Exception("ERROR! User must enter \"Serving Size!\"");
            else
                editText_object.getText().clear();

            checkBox_object = getActivity().findViewById(R.id.veganCheckBox);
            boolean temp_veganCheckBoxInput = checkBox_object.isChecked();

            checkBox_object = getActivity().findViewById(R.id.vegetarianCheckBox);
            boolean temp_vegetarianCheckBoxInput = checkBox_object.isChecked();

            checkBox_object = getActivity().findViewById(R.id.glutenFreeCheckBox);
            boolean temp_glutenFreeCheckBoxInput = checkBox_object.isChecked();

            switch_object = getActivity().findViewById(R.id.optionalNutritionalInformationEntry);
            boolean temp_nutritionalInformationToggle = switch_object.isChecked();

            if(switch_object.isChecked())
            {
                //Declare temporary variables
                //Initialized to default value of 0
                int tempCalories_INT = 0, tempProtein_INT = 0, tempCarbs_INT = 0, tempFat_INT = 0;

                editText_object = getActivity().findViewById(R.id.caloriesUSER_INPUT);
                String tempCalories_STRING = editText_object.getText().toString();
                //If the user entered data into this field on the app,
                //then go ahead and parse it appropriately
                if(!tempCalories_STRING.isEmpty())
                    tempCalories_INT = Integer.parseInt(tempCalories_STRING);
                editText_object.getText().clear();

                editText_object = getActivity().findViewById(R.id.totalProtein_USER_INPUT);
                String tempProtein_STRING = editText_object.getText().toString();
                if(!tempProtein_STRING.isEmpty())
                    tempProtein_INT = Integer.parseInt(tempProtein_STRING);
                editText_object.getText().clear();

                editText_object = getActivity().findViewById(R.id.totalCarbs_USER_INPUT);
                String tempCarbs_STRING = editText_object.getText().toString();
                if(!tempCarbs_STRING.isEmpty())
                    tempCarbs_INT = Integer.parseInt(tempCarbs_STRING);
                editText_object.getText().clear();

                editText_object = getActivity().findViewById(R.id.totalFat_USER_INPUT);
                String tempFat_STRING = editText_object.getText().toString();
                if(!tempFat_STRING.isEmpty())
                    tempFat_INT = Integer.parseInt(tempFat_STRING);
                editText_object.getText().clear();

                //Add to object and use appropriate parameterized constructor based on if the
                //user wants to
                input_recipe = new Recipes(temp_recipeName, temp_ingredientName, temp_servingSizeINT,
                         temp_veganCheckBoxInput, temp_vegetarianCheckBoxInput,
                        temp_glutenFreeCheckBoxInput, temp_nutritionalInformationToggle, tempCalories_INT, tempFat_INT, tempCarbs_INT, tempProtein_INT);
            }
            else
            {
                input_recipe = new Recipes(temp_recipeName, temp_ingredientName, temp_servingSizeINT,
                        temp_veganCheckBoxInput, temp_vegetarianCheckBoxInput, temp_glutenFreeCheckBoxInput, temp_nutritionalInformationToggle);
            }


            writeToFile(FILE_NAME, input_recipe);
        }
        catch(NumberFormatException invalid_quantityFormatEntered)
        {
            Toast.makeText(getActivity(), "Error: The \"Quantity\" must be numeric numbers only!", Toast.LENGTH_LONG).show();
            Log.e("KAMAL_ERROR", "Error: The \"Quantity\" must be numeric numbers only!");

        }
        catch(Exception thrown_exceptionInput) {
            Toast.makeText(getActivity(), thrown_exceptionInput.toString() , Toast.LENGTH_LONG).show();
            Log.e("KAMAL_ERROR", thrown_exceptionInput.toString());
        }

    }

    /*
    @author Kamaljot Saini
        Pre: Pre-Existing File, if no pre_existing file then the method wil create on based on "file_nameInput"
        Post: Will append given data to end of "FILE_NAME" text file
        PS: Passing view object to this method allows it to become clickable in UI attributes section
    */
    public void writeToFile(String input_fileName, Recipes input_recipeObject)
    {
        //String outputText_forFile = editText_object.getText().toString();
        FileOutputStream outputStream_object = null;

        try {
            outputStream_object = getActivity().openFileOutput(input_fileName, Context.MODE_APPEND);
            outputStream_object.write(input_recipeObject.toString().getBytes()); //Prints Recipe Object to file
            outputStream_object.write("\n".getBytes()); //Adds new line after inserting data

            //Clear the input in the editText object
            editText_object.getText().clear();
            Toast.makeText(getActivity(), "Saved at " + getActivity().getFilesDir() + "/" + input_fileName, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally //will be executed regardless of whether an exception is thrown or not
        {
            if(outputStream_object != null) //Meaning there is data in the file and it exists
            {
                try {
                    outputStream_object.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    } //End Method

} //End Class "AddRecipePage"
