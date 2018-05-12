package com.example.android.sandwich_butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.example.android.sandwich_butterknife.model.Sandwich;
import com.example.android.sandwich_butterknife.utils.JsonUtils;

import org.json.JSONException;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.sandwich_butterknife.utils.JsonUtils.parseSandwichJson;

public class DetailActivity extends AppCompatActivity {


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //get all the textview by their id
        @BindView(R.id.also_known_tv) TextView alsoKnownAsTextView;

        //TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);



        //set the textview with the text
        //to convert arrayliststring to charseqence https://stackoverflow.com/questions/3032342/arrayliststring-to-charsequence
        //remove the brackets using the url: https://stackoverflow.com/questions/7536154/remove-brackets-from-a-list-set-to-a-textview
       // alsoKnownAsTextView.setText(Arrays.toString(sandwich.getAlsoKnownAs().toArray(new CharSequence[sandwich.getAlsoKnownAs().size()])).replace("[", "").replace("]", ""));
        alsoKnownAsTextView.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        originTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());
        ingredientsTextView.setText(Arrays.toString(sandwich.getIngredients().toArray(new CharSequence[sandwich.getIngredients().size()])).replace("[", "").replace("]", ""));



    }
}
