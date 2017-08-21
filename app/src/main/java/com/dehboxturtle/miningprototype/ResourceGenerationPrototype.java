package com.dehboxturtle.miningprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SeekBar;

import com.dehboxturtle.miningprototype.widgets.MiningGridView;

public class ResourceGenerationPrototype extends AppCompatActivity implements View.OnClickListener {
    private MiningGridView mMiningGridView;
    private AppCompatTextView mThresholdAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_generation_prototype);

        final AppCompatButton thresholdButton = (AppCompatButton) findViewById(R.id.threshold_toggle);
        thresholdButton.setOnClickListener(this);
        mThresholdAmount = (AppCompatTextView) findViewById(R.id.threshold_amount);

        final AppCompatSeekBar seeker = (AppCompatSeekBar) findViewById(R.id.seeker);
        mMiningGridView = (MiningGridView) findViewById(R.id.grid);

        seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMiningGridView.setThreshold(progress);
                mThresholdAmount.setText(String.valueOf(progress));
                mMiningGridView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initializeParameterFields();

        final AppCompatButton mode = (AppCompatButton) findViewById(R.id.game_mode);
        mode.setOnClickListener(this);
    }

    private void initializeParameterFields() {
        AppCompatEditText editText = (AppCompatEditText) findViewById(R.id.exponent);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mMiningGridView.getPerlin().setExp(Double.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText = (AppCompatEditText) findViewById(R.id.octaves);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mMiningGridView.getPerlin().setOctaves(Integer.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText = (AppCompatEditText) findViewById(R.id.persistence);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mMiningGridView.getPerlin().setPersistence(Integer.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.threshold_toggle:
                mMiningGridView.setThresholdEnabled(!mMiningGridView.getThresholdEnabled());
                mMiningGridView.invalidate();
                break;
            case R.id.game_mode:
                switchToPlayMode();
                break;
            default:
        }
    }

    private void switchToPlayMode() {

    }
}
