package com.example.alexanderpowell.dcmetrofarecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    String[] fromDestinations = {
            "Union Station1",
            "Union Station2",
            "Union Station3",
            "Judiciary Square",
            "Farragut Square",
            "China Town",
            "Noma"
    };

    String[] toDestinations = {
            "Union Station",
            "Judiciary Square",
            "Farragut Square",
            "China Town",
            "Noma"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView fromTextView = (AutoCompleteTextView)findViewById(R.id.fromDestination);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, fromDestinations);

        fromTextView.setThreshold(1);
        fromTextView.setAdapter(adapter);

        //android.R.layout.de

        //String stations[] = getResources().getStringArray(R.array.metroStations);

    }
}
