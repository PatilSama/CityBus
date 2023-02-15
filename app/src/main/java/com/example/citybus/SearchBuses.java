package com.example.citybus;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchBuses extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbuses_layout);
        context = getApplicationContext();

        String stopFrom = getIntent().getStringExtra("stopFrom");
        String stopTo = getIntent().getStringExtra("stopTo");
        if(!stopFrom.isEmpty() && !stopTo.isEmpty())
        {
            Toast.makeText(this, stopFrom+" = "+ stopTo, Toast.LENGTH_SHORT).show();
        }
    }
}
