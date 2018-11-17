package com.example.alexanderpowell.dcmetrofarecalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.HashMap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String[] stations = new String[] {
            "Metro Center",
            "Farragut North",
            "Dupont Circle",
            "Woodley Park - Zoo/Adams Morgan",
            "Cleveland Park",
            "Van Ness - UDC",
            "Tenleytown - AU",
            "Friendship Heights",
            "Bethesda",
            "Medical Center",
            "Grosvenor - Strathmore",
            "White Flint",
            "Twinbrook",
            "Rockville",
            "Shady Grove",
            "Gallery Place - Chinatown",
            "Judiciary Square",
            "Union Station",
            "Rhode Island Ave - Brentwood",
            "Brookland - CUA",
            "Fort Totten",
            "Takoma",
            "Silver Spring",
            "Forest Glen",
            "Wheaton",
            "Glenmont",
            "NoMa - Gallaudet U",
            "McPherson Square",
            "Farragut West",
            "Foggy Bottom - GWU",
            "Rosslyn",
            "Arlington Cemetery",
            "Pentagon",
            "Pentagon City",
            "Crystal City",
            "Ronald Reagan Washington National Airport",
            "Braddock Road",
            "King St - Old Town",
            "Eisenhower Avenue",
            "Huntington",
            "Federal Triangle",
            "Smithsonian",
            "L'Enfant Plaza",
            "Federal Center SW",
            "Capitol South",
            "Eastern Market",
            "Potomac Ave",
            "Stadium - Armory",
            "Minnesota Ave",
            "Deanwood",
            "Cheverly",
            "Landover",
            "New Carrollton",
            "Mt Vernon Sq 7th St-Convention Center",
            "Shaw-Howard U",
            "U Street/African-Amer Civil War Memorial/Cardozo",
            "Columbia Heights",
            "Georgia Ave-Petworth",
            "West Hyattsville",
            "Prince George's Plaza",
            "College Park - U of Md",
            "Greenbelt",
            "Archives - Navy Memorial-Penn Quarter",
            "Waterfront",
            "Navy Yard - Ballpark",
            "Anacostia",
            "Congress Heights",
            "Southern Avenue",
            "Naylor Road",
            "Suitland",
            "Branch Ave",
            "Benning Road",
            "Capitol Heights",
            "Addison Road - Seat Pleasant",
            "Morgan Boulevard",
            "Largo Town Center",
            "Van Dorn Street",
            "Franconia - Springfield",
            "Court House",
            "Clarendon",
            "Virginia Square - GMU",
            "Ballston - MU",
            "East Falls Church",
            "West Falls Church - VT/UVA",
            "Dunn Loring - Merrifield",
            "Vienna / Fairfax-GMU",
            "McLean",
            "Tysons Corner",
            "Greensboro",
            "Spring Hill",
            "Wiehle - Reston East"
    };

    public static final String[] stationCodes = new String[] {
            "A01",
            "A02",
            "A03",
            "A04",
            "A05",
            "A06",
            "A07",
            "A08",
            "A09",
            "A10",
            "A11",
            "A12",
            "A13",
            "A14",
            "A15",
            "B01",
            "B02",
            "B03",
            "B04",
            "B05",
            "B06",
            "B07",
            "B08",
            "B09",
            "B10",
            "B11",
            "B35",
            "C02",
            "C03",
            "C04",
            "C05",
            "C06",
            "C07",
            "C08",
            "C09",
            "C10",
            "C12",
            "C13",
            "C14",
            "C15",
            "D01",
            "D02",
            "D03",
            "D04",
            "D05",
            "D06",
            "D07",
            "D08",
            "D09",
            "D10",
            "D11",
            "D12",
            "D13",
            "E01",
            "E02",
            "E03",
            "E04",
            "E05",
            "E07",
            "E08",
            "E09",
            "E10",
            "F02",
            "F04",
            "F05",
            "F06",
            "F07",
            "F08",
            "F09",
            "F10",
            "F11",
            "G01",
            "G02",
            "G03",
            "G04",
            "G05",
            "J02",
            "J03",
            "K01",
            "K02",
            "K03",
            "K04",
            "K05",
            "K06",
            "K07",
            "K08",
            "N01",
            "N02",
            "N03",
            "N04",
            "N06",
    };

    AutoCompleteTextView fromTextView;
    AutoCompleteTextView toTextView;
    TextView offPeakFareTextView;
    TextView peakFareTextView;
    TextView seniorDisabledFareTextView;

    String fromStationCode = null;
    String toStationCode = null;
    //final Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = getApplicationContext();

        fromTextView = (AutoCompleteTextView)findViewById(R.id.fromDestination);
        toTextView = (AutoCompleteTextView)findViewById(R.id.toDestination);
        offPeakFareTextView = (TextView)findViewById(R.id.offPeakFareTextView);
        peakFareTextView = (TextView)findViewById(R.id.peakFareTextView);
        seniorDisabledFareTextView = (TextView)findViewById(R.id.seniorDisabledFareTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stations);
        fromTextView.setAdapter(adapter);
        fromTextView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                int pos = -1;
                for (int i = 0; i < stations.length; i++) {
                    if (stations[i].equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                Toast.makeText(context, "From: " + stationCodes[pos], Toast.LENGTH_LONG).show();
                fromStationCode = stationCodes[pos];
            }
        });
        toTextView.setAdapter(adapter);
        toTextView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                int pos = -1;
                for (int i = 0; i < stations.length; i++) {
                    if (stations[i].equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                Toast.makeText(context, "To: " + stationCodes[pos], Toast.LENGTH_LONG).show();
                toStationCode = stationCodes[pos];
            }
        });

        peakFareTextView.setText("peakFareTextView");
        offPeakFareTextView.setText("offPeakFareTextView");
        seniorDisabledFareTextView.setText("seniorDisabledFareTextView");

    }

    // On button click method
    public void calculateFare(View view) {

        if (fromStationCode == null || toStationCode == null) {
            //Toast.makeText(context, "Please select stations from dropdown", Toast.LENGTH_LONG).show();
            return;
        }

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "";

        url = "https://api.wmata.com/Rail.svc/json/jSrcStationToDstStationInfo";
        url = url + "?" + "FromStationCode=" + fromStationCode + "&" + "ToStationCode=" + toStationCode;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Double peakFare = response.getJSONArray("StationToStationInfos").getJSONObject(0).getJSONObject("RailFare").getDouble("PeakTime");
                            Double offPeakFare = response.getJSONArray("StationToStationInfos").getJSONObject(0).getJSONObject("RailFare").getDouble("OffPeakTime");
                            Double seniorDisabledFare = response.getJSONArray("StationToStationInfos").getJSONObject(0).getJSONObject("RailFare").getDouble("SeniorDisabled");

                            DecimalFormat formatter = new DecimalFormat("#0.00");
                            peakFareTextView.setText("Peak Fare: $" + formatter.format(peakFare));
                            offPeakFareTextView.setText("Off Peak Fare: $" + formatter.format(offPeakFare));
                            seniorDisabledFareTextView.setText("Senior/Disabled Fare: $" + formatter.format(seniorDisabledFare));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        peakFareTextView.setText("ERROR FETCHING FARES");
                        offPeakFareTextView.setText("ERROR FETCHING FARES");
                        seniorDisabledFareTextView.setText("ERROR FETCHING FARES");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("api_key", "114c22c945cb4cf588a4fda64e5b4850"); // api key here
                return params;
            }
        };

        mQueue.add(jsonObjectRequest);
    }
}
