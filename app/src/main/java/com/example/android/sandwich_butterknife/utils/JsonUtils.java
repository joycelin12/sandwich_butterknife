package com.example.android.sandwich_butterknife.utils;

import android.support.annotation.NonNull;

import com.example.android.sandwich_butterknife.R;
import com.example.android.sandwich_butterknife.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonUtils {

    public static final String JSON_NAME_KEY = "name";
    public static final String JSON_MAINNAME_KEY = "mainName";

    public static Sandwich parseSandwichJson(String json) throws JSONException {


        //create a new variable to store sandwich details
        Sandwich details = new Sandwich();

        //referencing from the url:https://www.androidhive.info/2012/01/android-json-parsing-tutorial/
        //on parsing json =  {"name":{"mainName":"Ham and cheese sandwich","alsoKnownAs":[]},"placeOfOrigin":"","description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included. Various kinds of mustard and mayonnaise are also common.","image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG","ingredients":["Sliced bread","Cheese","Ham"]}
        JSONObject jsonObj = new JSONObject(json);

        // Getting JSON Object node
        JSONObject nameObj = jsonObj.getJSONObject(JSON_NAME_KEY);
        String mainName = nameObj.optString(JSON_MAINNAME_KEY);
        // When there is square bracket have to use JSONArray
        JSONArray alsoKnownAsObj = nameObj.optJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<String>();
        for(int i =0; i<alsoKnownAsObj.length(); i++) {
            alsoKnownAs.add(alsoKnownAsObj.optString(i));

        }

        String placeOfOrigin = jsonObj.optString("placeOfOrigin");
        String description = jsonObj.optString("description");
        String image = jsonObj.optString("image");
        JSONArray ingredientsObj = jsonObj.optJSONArray("ingredients");
        List<String> ingredients= new ArrayList<String>();
        for(int j =0; j<ingredientsObj.length(); j++) {
            ingredients.add(ingredientsObj.optString(j));

        }

        // Add the details to sandwich
        details.setMainName(mainName);
        details.setAlsoKnownAs(alsoKnownAs);
        details.setPlaceOfOrigin(placeOfOrigin);
        details.setDescription(description);
        details.setImage(image);
        details.setIngredients(ingredients);

       // System.out.println(placeOfOrigin+"testing");
        return details;
    }
}
