package com.erkutdemirhan.bugunneyesek.recipes;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.erkutdemirhan.bugunneyesek.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Erkut Demirhan on 03.06.2015.
 */
public class RecipeActivity extends AppCompatActivity {

    public static final String RECIPE_TEXT_KEY = "Recipe_Text";

    private ImageView mRecipeImage;
    private Toolbar mToolbar;
    private TextView mRecipeTitle;
    private TextView mAvailableIngr;
    private TextView mUnavailableIngr;
    private TextView mRecipeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        String[] data = getIntent().getExtras().getStringArray(RECIPE_TEXT_KEY);

        mRecipeImage = (ImageView) findViewById(R.id.recipe_activity_image);
        mToolbar = (Toolbar) findViewById(R.id.recipe_activity_tool_bar);
        mRecipeTitle = (TextView) findViewById(R.id.recipe_activity_header);
        mAvailableIngr = (TextView) findViewById(R.id.recipe_activity_available_ingredients);
        mUnavailableIngr = (TextView) findViewById(R.id.recipe_activity_unavailable_ingredients);
        mRecipeContent = (TextView) findViewById(R.id.recipe_activity_content_text);

        mRecipeTitle.setText(data[0]);
        mRecipeContent.setText(data[1]);
        Uri uri = Uri.parse(data[2]);
        Picasso.with(this).load(uri)
                .error(R.drawable.recipe_image)
                .placeholder(R.drawable.recipe_image)
                .into(mRecipeImage);

        mAvailableIngr.setText("Mevcut malzemeler");
        mUnavailableIngr.setText("Mevcut olmayan malzemeler");

        if(mToolbar != null) {
            mToolbar.setTitle("Tarif Listesi");
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

}
