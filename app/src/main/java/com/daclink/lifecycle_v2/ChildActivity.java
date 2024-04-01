/**
 * Title: Lab - Android LifeCycles and the Factory Design Pattern - ChildActivity
 * Abstract: ChildActivity file for app used to demo intent factories defined in two activities.
 * Author: Guillermo Zendejas
 * Date: 3/31/2024
 */

package com.daclink.lifecycle_v2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.daclink.lifecycle_v2.databinding.ActivityChildBinding;

public class ChildActivity extends AppCompatActivity {
    private static final String SHOW_MESSAGE_ONE = "com.daclink.lifecycle_v2.extraValue" ;
    ActivityChildBinding binding;
    private boolean showMessage1Child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showMessage1Child = getIntent().getBooleanExtra(SHOW_MESSAGE_ONE, false);
        binding.ChildActivityButton.setText(showMessage1Child ? "TRUE" : "FALSE");

        binding.ChildActivityButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Determines behavior when button is clicked on.
             * @param v reference to view that is clicked on.
             */
            @Override
            public void onClick(View v) {
                showMessage1Child = !showMessage1Child;
                binding.ChildActivityButton.setText(showMessage1Child ? "TRUE" : "FALSE");
            }
        });

        binding.ChildActivityButton.setOnLongClickListener(new View.OnLongClickListener() {

            /**
             * Determines behavior when button is long-clicked.
             * @param v The view that is clicked on.
             * @return false boolean.
             */
            @Override
            public boolean onLongClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), showMessage1Child);
                startActivity(intent);
                return false;
            }
        });
    }

    /**
     * Static class function that can return an intent initialized within an activity other than this one.
     * @param context The context of the activity where the function is called.
     * @param messageValue The information being passed from one activity to another.
     * @return An intent from another activity, with Extra value updated with the data to be received by this activity.
     */
    static Intent intentFactory(Context context, boolean messageValue) {
        Intent  intent = new Intent(context, ChildActivity.class);
        intent.putExtra(SHOW_MESSAGE_ONE, messageValue);
        return intent;
    }
}