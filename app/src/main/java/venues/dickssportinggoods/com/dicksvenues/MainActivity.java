package venues.dickssportinggoods.com.dicksvenues;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
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

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {

    private static final int VENUE_LOADER_ID = 1;
    DataParser dataParser = new DataParser();
    final String PAY_LOAD_URL = "https://movesync-qa.dcsg.com/dsglabs/mobile/api/venue/";
    ArrayAdapter<String> adapter;
    static String venuesData;
    ListView listView;
    private FusedLocationProviderClient mFusedLocationClient;
    double currentLatitude, currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    Log.d("current long and lats", String.valueOf(currentLatitude) + "and" + String.valueOf(currentLongitude));
                }
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(VENUE_LOADER_ID, null, this);

        listView = (ListView) findViewById(R.id.venue_names);

        shareDetails();
    }

    public void shareDetails () {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String[] storeaddress = dataParser.venueAddress(venuesData, i);
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

        List<String> venueArray = new ArrayList(Arrays.asList(strings));
        adapter = new ArrayAdapter(getApplicationContext(), R.layout.venue_names, R.id.list_item_venue_name, venueArray);
        listView = (ListView) findViewById(R.id.venue_names);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

        adapter.clear();

    }


}
