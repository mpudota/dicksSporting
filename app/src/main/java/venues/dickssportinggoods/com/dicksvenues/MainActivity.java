package venues.dickssportinggoods.com.dicksvenues;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {

    private static final int VENUE_LOADER_ID = 1;
    DataParser dataParser = new DataParser();
    final String PAY_LOAD_URL = "https://movesync-qa.dcsg.com/dsglabs/mobile/api/venue/";
    ArrayAdapter<String> adapter;
    static String venuesData;
    ListView listView;
    int[] index;
    private FusedLocationProviderClient mFusedLocationClient;
    double currentLatitude, currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();
                    }
                }
            });
        }

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(VENUE_LOADER_ID, null, this);

        listView = (ListView) findViewById(R.id.venue_names);

        shareDetails();
    }

    public void shareDetails () {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                i = index[i];

                String[] storeaddress = dataParser.venueAddress(venuesData, i);
                String [] contactInfo = dataParser.contacts(venuesData, i);
                String storeURL = dataParser.storeURL(venuesData, i);
                float rating = dataParser.storeRating(venuesData, i);
                String[] storePhotoUrl = dataParser.photoUrl(venuesData, i);
                String ratingColor = dataParser.ratingColor(venuesData, i);
                double[] longlat = dataParser.longAndlat(venuesData, i);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.VENUE_STORE_URL, storeURL);
                intent.putExtra(DetailActivity.VENUE_RATING, rating);
                intent.putExtra(DetailActivity.VENUE_PHOTO_URL, storePhotoUrl);
                intent.putExtra(DetailActivity.VENUE_RATING_COLOR, ratingColor);
                intent.putExtra(DetailActivity.VENUE_ADDRESS, storeaddress);
                intent.putExtra(DetailActivity.VENUE_LONG_LATS, longlat);
                intent.putExtra(DetailActivity.VENUE_CONTACT_DETAILS, contactInfo);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<String[]> onCreateLoader(int i, Bundle bundle) {
        return  new VenuesLoader(this, PAY_LOAD_URL );
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] strings) {

        Double[] distances = calculateDistance();

        Map<Double, String> map = new TreeMap();
        for (int i=0; i < strings.length; i++) {
            map.put(distances[i], strings[i]);
        }
        List<String> result2 = new ArrayList(map.values());
        strings = result2.toArray(new String[result2.size()]);

        trimVenueName(strings);

        List<String> venueArray = new ArrayList(Arrays.asList(strings));
        adapter = new ArrayAdapter(getApplicationContext(), R.layout.venue_names, R.id.list_item_venue_name, venueArray);
        listView = (ListView) findViewById(R.id.venue_names);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
        adapter.clear();
    }

    public Double[] calculateDistance () {
        Location myLocation = new Location("");
        Location destLocation = new Location("");
        myLocation.setLatitude(currentLatitude);
        myLocation.setLongitude(currentLongitude);
        Double[] distances = new Double[5];

        for (int i=0; i<5 ; i++) {
            double[] longlats = dataParser.longAndlat(venuesData, i);

            if (longlats != null) {

                destLocation.setLatitude(longlats[0]);
                destLocation.setLongitude(longlats[1]);
                distances[i] = Double.valueOf(myLocation.distanceTo(destLocation));
            } else {
                distances[i] = 1111111111111.0;
            }
        }
        return distances;
    }

    public String[] trimVenueName (String[] strings) {

        index = new int[strings.length];
        for (int i=0; i<strings.length; i++) {

            index[i] = Integer.parseInt(strings[i].substring(strings[i].length()-1));

            strings[i] = strings[i].substring(0, strings[i].length()-1);
        }
        return strings;
    }
}