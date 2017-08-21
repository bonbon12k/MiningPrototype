package com.dehboxturtle.miningprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class ModeSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);
        AppCompatButton button = (AppCompatButton) findViewById(R.id.start_resource);
        button.setOnClickListener(this);
        button = (AppCompatButton) findViewById(R.id.start_game);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.start_resource:
                intent = new Intent(this, ResourceGenerationPrototype.class);
                break;
            case R.id.start_game:
                intent = new Intent(this, CellularAutomataGamePrototype.class);
                break;
            default:
                intent = null;
        }
        startActivity(intent);
    }
}
