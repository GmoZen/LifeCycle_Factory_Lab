/**
 * Title: Lab - Android LifeCycles and the Factory Design Pattern - MainActivity
 * Abstract: MainActivity file for app used to demo intent factories defined in two activities.
 * Author: Guillermo Zendejas
 * Date: 3/31/2024
 */

package com.daclink.lifecycle_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.daclink.lifecycle_v2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LifeCycleDemo";
    private static final String BUTTON_STATE = "ButtonState";
    ActivityMainBinding binding;
    Button button;
    TextView mTextView;
    boolean messageOne = false;

    /**
     * Static class function that can return an intent initialized within an activity other than this one.
     * @param applicationContext The context of the activity where the function is called.
     * @param showMessage1Child The information being passed from one activity to another.
     * @return An intent from another activity, with Extra value updated with the data to be received by this activity.
     */
    static Intent intentFactory(Context applicationContext, boolean showMessage1Child) {
        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.putExtra(BUTTON_STATE, showMessage1Child);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        messageOne = getIntent().getBooleanExtra(BUTTON_STATE, false);

        if (savedInstanceState != null) {
            messageOne = savedInstanceState.getBoolean(BUTTON_STATE, true);
        }

        button = binding.button;
        mTextView = binding.textView;
        getMessage();

        button.setOnClickListener(new View.OnClickListener() {

            /**
             * Determines behavior when button is clicked on.
             * @param view reference to view that is clicked on.
             */
            @Override
            public void onClick(View view) {
                messageOne = !messageOne;
                getMessage();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {

            /**
             * Determines behavior when button is long-clicked.
             * @param v The view that is clicked on.
             * @return false boolean.
             */
            @Override
            public boolean onLongClick(View v) {
                Intent intent  = ChildActivity.intentFactory(getApplicationContext(), messageOne);
                startActivity(intent);
                return false;
            }
        });
    }

    private void getMessage(){
        if (messageOne){
            mTextView.setText(R.string.message2);
        } else {
            mTextView.setText(R.string.message1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState called");
        savedInstanceState.putBoolean(BUTTON_STATE, messageOne);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
