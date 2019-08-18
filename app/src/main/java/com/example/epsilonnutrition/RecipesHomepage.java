package com.example.epsilonnutrition;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


public class RecipesHomepage extends Fragment {
    //Declare local variables/objects
    HashMap<String, Recipes> loadFile_hashMap = new HashMap<String, Recipes>();
    private static final String FILE_NAME = "epsilonNutritionApp_USER_RECIPES.txt";
    RecyclerView recyclerView_object;
    recipesHomepage_recyclerViewer_searchBar_ADAPTER recipesList_Adapter;
    RecyclerView.LayoutManager recipesList_layoutManager;
    List<Recipes> recipesList_arrayList = new ArrayList<>();
    SearchView searchBarRecipesHomepage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Calls XML Layout File
        return inflater.inflate(R.layout.recipes_home_page, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Epsilon Nutrition - Recipes");
        //This prevents the keyboard from automatically opening up upon creation of activity
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Load all the recipes for viewing and into various data structures
        load_Recipes();

        setUp_scrollableRecipesRecyclerViewer();
        setUp_recipesSearchBarFunctionality();

        //Create button object for activity 2
        Button addRecipe_buttonObject = getActivity().findViewById(R.id.addRecipeButton);
        addRecipe_buttonObject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view_object)
            {
                openAddRecipePage();
            }
        });

    } // End on Create

    //Sets up the recycler viewer
    private void setUp_scrollableRecipesRecyclerViewer(){
        recyclerView_object = getActivity().findViewById(R.id.recipesRecyclerView);
        recyclerView_object.setHasFixedSize(true);

        //By default this is vertical recyclerView in LinearLayout
        recipesList_layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_object.setLayoutManager(recipesList_layoutManager);

        recipesList_Adapter = new recipesHomepage_recyclerViewer_searchBar_ADAPTER(getActivity(), recipesList_arrayList);
        recyclerView_object.setAdapter(recipesList_Adapter);
    }

    private void setUp_recipesSearchBarFunctionality(){
        //Connect to searchBar in Recipes Homepage
        searchBarRecipesHomepage = getActivity().findViewById(R.id.searchRecipesView);

        searchBarRecipesHomepage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do nothing here since we are filtering our search results in real time
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipesList_Adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchBarRecipesHomepage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view_objectRecipesSearchBar)
            {
                /*
                This makes it so user can click anywhere on the search
                bar and it will be clickable, instead of having to click
                the icon to start the search
                 */
                searchBarRecipesHomepage.setIconified(false);
            }

        });

    }

    public void openAddRecipePage()
    {
//        Intent intent = new Intent(getActivity(), AddRecipePage.class);
//        startActivity(intent);
        Fragment fragment = new AddRecipePage();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /*
       @author Kamaljot Saini
       Load the recipes into input HashMap
       PS Remember objects are passed by reference, so
       technically the object itself is being mutated
    */
    public void load_Recipes()
    {
        //Declare local variables/objects
        FileInputStream fileInputStream_object = null;
        int nextEntity_counter = 1;
        boolean flag_nextEntity = false;

        //Temporary empty string holders
        String temp_nameRecipe = "";
        String temp_nameIngredient = "";
        String temp_numServings = "";
        int temp_intNumServings;
        double temp_doubleQuantityIngredient;
        String temp_veganFlagSTRING = "";
        String temp_vegetarianFlagSTRING = "";
        String temp_glutenFreeFLAG = "";
        String temp_optionalNutritionalInformationFlag = "";
        boolean temp_veganFlagBOOLEAN, temp_vegetarianFlagBOOLEAN, temp_glutenFreeFlagBOOLEAN, temp_optionalNutritionalInformationBOOLEAN;
        String temp_caloriesSTRING, temp_fatSTRING, temp_carbsSTRING, temp_proteinSTRING;
        int temp_caloriesINT, temp_fatINT, temp_carbsINT, temp_proteinINT;

        try {
            fileInputStream_object = getActivity().openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader_object = new InputStreamReader(fileInputStream_object);
            BufferedReader bufferedReader_object =  new BufferedReader(inputStreamReader_object);
            StringBuilder stringBuilder_object = new StringBuilder();
            String text_fromFile;

            //Appending the contents of file line by line to string format
            //Basically preparing it for the next step of output to screen
            //Now we have to break up the contents of the file and store into objects properly
            while((text_fromFile = bufferedReader_object.readLine()) != null){
                char[] text_fromFileARRAY = null; //Reset the char array
                text_fromFileARRAY = text_fromFile.toCharArray();

                //Reset Temp Holders
                temp_nameRecipe = "";
                temp_nameIngredient = "";
                temp_numServings = "";
                temp_intNumServings = 0;
                temp_doubleQuantityIngredient = 0.0;
                temp_veganFlagSTRING = "";
                temp_vegetarianFlagSTRING = "";
                temp_glutenFreeFLAG = "";
                temp_optionalNutritionalInformationFlag = "";
                temp_caloriesSTRING = "";
                temp_fatSTRING = "";
                temp_carbsSTRING = "";
                temp_proteinSTRING = "";
                temp_caloriesINT = 0;
                temp_fatINT = 0;
                temp_carbsINT = 0;
                temp_proteinINT= 0;

                for(int index = 0; index < text_fromFile.length(); index++) //Shuffle through the string as an array
                {
                    if(text_fromFileARRAY[index] == '/')
                        nextEntity_counter++;

                    flag_nextEntity = false;
                    switch(nextEntity_counter){
                        case 1:
                            //Name of Recipe
                            temp_nameRecipe = temp_nameRecipe + text_fromFileARRAY[index];
                            break;
                        case 2:
                            //Name of Ingredient
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_nameIngredient = temp_nameIngredient + text_fromFileARRAY[index];
                            break;
                        case 3:
                            //Number of Servings
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_numServings = temp_numServings + text_fromFileARRAY[index];
                            break;
                        case 4:
                            //veganFlag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_veganFlagSTRING = temp_veganFlagSTRING + text_fromFileARRAY[index];

                            break;
                        case 5:
                            //vegetarianFlag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_vegetarianFlagSTRING = temp_vegetarianFlagSTRING + text_fromFileARRAY[index];

                            break;
                        case 6:
                            //glutenFreeFlag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_glutenFreeFLAG = temp_glutenFreeFLAG + text_fromFileARRAY[index];

                            break;
                        case 7:
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_optionalNutritionalInformationFlag = temp_optionalNutritionalInformationFlag + text_fromFileARRAY[index];

                            break;
                        case 8:
                            //Calories
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_caloriesSTRING = temp_caloriesSTRING + text_fromFileARRAY[index];


                            break;
                        case 9:
                            //Total Fat
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_fatSTRING = temp_fatSTRING + text_fromFileARRAY[index];

                            break;
                        case 10:
                            //Total Carbs
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_carbsSTRING = temp_carbsSTRING + text_fromFileARRAY[index];

                            break;
                        case 11:
                            //Total Protein
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_proteinSTRING = temp_proteinSTRING + text_fromFileARRAY[index];
                            break;
                        default:
                            break;

                    } //End Switch


                    if(nextEntity_counter == 11 && (index == text_fromFile.length() - 1)) //index == text_fromFile.length() because we want to double check that we read the entire line so input is correct
                        nextEntity_counter = 1; //Reset the counter for the next line

                } //End for loop

                //Convert to appropriate data values
                if(temp_numServings != "")
                    temp_intNumServings = Integer.parseInt(temp_numServings);

                if(temp_caloriesSTRING != "")
                    temp_caloriesINT = Integer.parseInt(temp_caloriesSTRING);

                if(temp_fatSTRING != "")
                    temp_fatINT = Integer.parseInt(temp_fatSTRING);

                if(temp_carbsSTRING != "")
                    temp_carbsINT = Integer.parseInt(temp_carbsSTRING);

                if(temp_proteinSTRING != "")
                    temp_proteinINT = Integer.parseInt(temp_proteinSTRING);

                //What if the user does not check one of these boxes? Then by default it is false
                if(temp_veganFlagSTRING.equals("true"))
                    temp_veganFlagBOOLEAN = true;
                else
                    temp_veganFlagBOOLEAN = false;

                if(temp_vegetarianFlagSTRING.equals("true"))
                    temp_vegetarianFlagBOOLEAN = true;
                else
                    temp_vegetarianFlagBOOLEAN = false;

                if(temp_glutenFreeFLAG.equals("true"))
                    temp_glutenFreeFlagBOOLEAN = true;
                else
                    temp_glutenFreeFlagBOOLEAN = false;

                if(temp_optionalNutritionalInformationFlag.equals("true"))
                    temp_optionalNutritionalInformationBOOLEAN = true;
                else
                    temp_optionalNutritionalInformationBOOLEAN = false;


                //Create a temporary recipe object and store it into the HashMap
                Recipes temp_recipeObject = new Recipes(temp_nameRecipe, temp_nameIngredient, temp_intNumServings,
                        temp_veganFlagBOOLEAN, temp_vegetarianFlagBOOLEAN, temp_glutenFreeFlagBOOLEAN, temp_optionalNutritionalInformationBOOLEAN,
                        temp_caloriesINT, temp_fatINT, temp_carbsINT, temp_proteinINT);
                loadFile_hashMap.put(temp_recipeObject.getName_recipe(), temp_recipeObject);
                recipesList_arrayList.add(temp_recipeObject);

                //Show the user input from file
                stringBuilder_object.append(text_fromFile).append("\n");
            }

        } catch (FileNotFoundException e) {
            Log.e("KAMAL_ERROR", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("KAMAL_ERROR", e.toString());
        }finally
        {
            if(fileInputStream_object != null) {
                try {
                    fileInputStream_object.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("KAMAL_ERROR", e.toString());
                }
            }
        }
    } //End Method "Load File"

} //End class "RecipesHomepage"
