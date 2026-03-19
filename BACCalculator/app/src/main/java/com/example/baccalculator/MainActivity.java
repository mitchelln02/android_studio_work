package com.example.baccalculator;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = "BACCalculator";
    EditText editTextWeight;
    TextView textViewWeight, textViewGender, textView3, textViewWeightFinal, textViewDrinkSizes, textViewAlcPercentage, textViewPercentage, textViewNumDrinks, textViewBACLevel, textViewStatus, textViewStatusResult;
    RadioGroup radioGroupGenders, radioGroupDrinkTypes;
    RadioButton radioButtonFemale, radioButtonMale, radioButton1oz, radioButton5oz, radioButton12oz;
    Button buttonSetWeight, buttonReset, buttonAddDrink;
    SeekBar seekBarPercent;
    ConstraintLayout rootView;
    String gender = "female";
    int total_drink, total_ounces;
    double BAClevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextWeight = findViewById(R.id.editTextWeight);
        textViewWeight = findViewById(R.id.textViewWeight);
        textViewGender = findViewById(R.id.textViewGender);
        textView3 = findViewById(R.id.textView3);
        textViewWeightFinal = findViewById(R.id.textViewWeightFinal);
        textViewDrinkSizes = findViewById(R.id.textViewDrinkSizes);
        textViewAlcPercentage = findViewById(R.id.textViewAlcPercentage);
        textViewPercentage = findViewById(R.id.textViewPercentage);
        textViewNumDrinks = findViewById(R.id.textViewNumDrinks);
        textViewBACLevel = findViewById(R.id.textViewBACLevel);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewStatusResult = findViewById(R.id.textViewStatusResult);

        radioGroupGenders = findViewById(R.id.radioGroupGenders);
        radioGroupDrinkTypes = findViewById(R.id.radioGroupDrinkTypes);

        findViewById(R.id.radioButtonFemale).setOnClickListener(this);
        findViewById(R.id.radioButtonMale).setOnClickListener(this);
        findViewById(R.id.radioButton1oz).setOnClickListener(this);
        findViewById(R.id.radioButton5oz).setOnClickListener(this);
        findViewById(R.id.radioButton12oz).setOnClickListener(this);

        buttonAddDrink = findViewById(R.id.buttonAddDrink);
        findViewById(R.id.buttonSetWeight).setOnClickListener(this);
        findViewById(R.id.buttonReset).setOnClickListener(this);
        findViewById(R.id.buttonAddDrink).setOnClickListener(this);

        seekBarPercent = findViewById(R.id.seekBarPercent);
        seekBarPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(TAG, "onProgressChanged: ");
                textViewPercentage.setText(String.valueOf(i) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.radioButtonFemale) {
            Log.d(TAG, "onClick: female");
            gender = "female";
        } else if (view.getId() == R.id.radioButtonMale) {
            Log.d(TAG, "onClick: male");
            gender = "male";
        }
        else if(view.getId() == R.id.buttonSetWeight){
            String weightStr = editTextWeight.getText().toString();
            int weight = 0;
            try {
                weight = Integer.parseInt(weightStr);
                if (weight <= 0) {
                    editTextWeight.setError("Enter a positive number");
                    return;
                }
            } catch (NumberFormatException e) {
                editTextWeight.setError("Enter a valid number");
                return;
            }
            textViewWeightFinal.setText(weight + " lbs" + " (" + gender + ")");
        } else if (view.getId() == R.id.buttonReset) {
            editTextWeight.setText("");
            radioGroupGenders.check(R.id.radioButtonFemale);
            gender = "female";
            radioGroupDrinkTypes.clearCheck();
            seekBarPercent.setProgress(12);
            textViewPercentage.setText("12%");
            textViewWeightFinal.setText("");
            textViewNumDrinks.setText("# Drinks: 0");
            textViewBACLevel.setText("BAC Level: 0.000%");
            textViewStatusResult.setText(R.string.safe);
            textViewStatusResult.setBackgroundColor(getColor(R.color.safe_color));
            total_drink = 0;
            total_ounces = 0;
            BAClevel = 0;
        } else if (view.getId() == R.id.buttonAddDrink) {
            Log.d(TAG, "onClick: add drink");
            // After updating BAC and UI in buttonAddDrink handler

            int drink_size = 0;
            if (radioGroupDrinkTypes.getCheckedRadioButtonId() == R.id.radioButton1oz) {
                drink_size = 1;
            } else if (radioGroupDrinkTypes.getCheckedRadioButtonId() == R.id.radioButton5oz) {
                drink_size = 5;
            } else if (radioGroupDrinkTypes.getCheckedRadioButtonId() == R.id.radioButton12oz) {
                drink_size = 12;
            } else {
                return;
            }

            double alcohol_percentage = (double) seekBarPercent.getProgress() / 100;
            double alcohol_for_this_drink = drink_size * alcohol_percentage;

            total_drink += 1;
            textViewNumDrinks.setText("# Drinks: " + total_drink);

            if (Double.isNaN(BAClevel)) BAClevel = 0;
            BAClevel += alcohol_for_this_drink;

            String weightStr = editTextWeight.getText().toString();
            if (weightStr.isEmpty()) return;
            double weight = Double.parseDouble(weightStr);

            double r = gender.equals("male") ? 0.73 : 0.66;

            double BAC = (BAClevel * 5.14) / (weight * r);
            textViewBACLevel.setText(String.format("BAC Level: %.3f%%", BAC));

            if (BAC <= 0.08) {
                textViewStatusResult.setText(R.string.safe);
                textViewStatusResult.setBackgroundColor(getColor(R.color.safe_color));
            } else if (BAC <= 0.20) {
                textViewStatusResult.setText(R.string.careful);
                textViewStatusResult.setBackgroundColor(getColor(R.color.be_careful_color));
            } else {
                textViewStatusResult.setText(R.string.overLimit);
                textViewStatusResult.setBackgroundColor(getColor(R.color.overlimit_color));
            }
            if (BAClevel >= 0.25) {
                buttonAddDrink.setEnabled(false);
                Toast.makeText(this, "No more drinks for you.", Toast.LENGTH_SHORT).show();
            } else {
                buttonAddDrink.setEnabled(true);
            }
        }
    }
}
