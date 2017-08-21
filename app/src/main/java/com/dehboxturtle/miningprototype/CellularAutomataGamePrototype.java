package com.dehboxturtle.miningprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SeekBar;

import com.dehboxturtle.miningprototype.util.Constants;
import com.dehboxturtle.miningprototype.util.MyPerlin;
import com.dehboxturtle.miningprototype.widgets.AutomataRuleView;
import com.dehboxturtle.miningprototype.widgets.MiningGridView;

import java.util.ArrayList;
import java.util.Random;

public class CellularAutomataGamePrototype extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 10;
    public static final int MINERAL_THRESHOLD = 33;
    public static final int PERLIN_OCTAVES = 6;
    public static final double PERLIN_EXPONENT = 2.3;
    public static final int PERLIN_PERSISTENCE = 1;

    private MiningGridView mMiningGridView;
    private AppCompatSeekBar mStartingCell;
    private AppCompatButton mDigButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellular_automata_game_prototype);

        // set up start button
        mDigButton = (AppCompatButton) findViewById(R.id.dig_button);
        mDigButton.setOnClickListener(this);

        // set up start point seeker
        mStartingCell = (AppCompatSeekBar) findViewById(R.id.start_cell);
        mStartingCell.setMax(GRID_WIDTH - 1);
        mStartingCell.setOnSeekBarChangeListener(this);

        // set up the mining grid and parameters
        mMiningGridView = (MiningGridView) findViewById(R.id.grid);
        mMiningGridView.setGridHeight(GRID_HEIGHT);
        mMiningGridView.setGridWidth(GRID_WIDTH);
        mMiningGridView.setThreshold(MINERAL_THRESHOLD);
        MyPerlin perlin = mMiningGridView.getPerlin();
        perlin.setOctaves(PERLIN_OCTAVES);
        perlin.setExp(PERLIN_EXPONENT);
        perlin.setPersistence(PERLIN_PERSISTENCE);
        mMiningGridView.setThresholdEnabled(true);
        mMiningGridView.setStartingLocation(0);

        // set up rules for the automata
        ArrayList<boolean[]> rules = new ArrayList<>();
        Random random = new Random();
        AutomataRuleView ruleView = (AutomataRuleView) findViewById(R.id.rule1);
        ruleView.setRule(new boolean[] {
                random.nextBoolean(),
                random.nextBoolean(),
                random.nextBoolean()}
        );
        rules.add(ruleView.getRule());
        ruleView = (AutomataRuleView) findViewById(R.id.rule2);
        ruleView.setRule(new boolean[] {
                random.nextBoolean(),
                random.nextBoolean(),
                random.nextBoolean()}
        );
        rules.add(ruleView.getRule());
        mMiningGridView.setRules(rules);

        mMiningGridView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mMiningGridView.setStartingLocation(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View v) {

    }
}
