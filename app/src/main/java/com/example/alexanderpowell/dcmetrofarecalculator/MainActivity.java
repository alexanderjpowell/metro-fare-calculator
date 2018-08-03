package com.example.alexanderpowell.dcmetrofarecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String[] stations = new String[] {"Union Station1", "Union Station2", "Union Station3", "China Town", "Noma"};

    AutoCompleteTextView fromTextView;
    AutoCompleteTextView toTextView;
    TextView offPeakFareTextView;
    TextView peakFareTextView;
    TextView seniorDisabledFareTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromTextView = (AutoCompleteTextView)findViewById(R.id.fromDestination);
        toTextView = (AutoCompleteTextView)findViewById(R.id.toDestination);
        offPeakFareTextView = (TextView)findViewById(R.id.offPeakFareTextView);
        peakFareTextView = (TextView)findViewById(R.id.peakFareTextView);
        seniorDisabledFareTextView = (TextView)findViewById(R.id.seniorDisabledFareTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stations);
        fromTextView.setAdapter(adapter);
        toTextView.setAdapter(adapter);

        offPeakFareTextView.setText("offPeakFareTextView");
        peakFareTextView.setText("peakFareTextView");
        seniorDisabledFareTextView.setText("seniorDisabledFareTextView");
    }
}
