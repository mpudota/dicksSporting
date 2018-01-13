package venues.dickssportinggoods.com.dicksvenues;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity {

    DataParser dataParser = new DataParser();
    ArrayAdapter<String> adapter;
    String venuesData;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchVenues fetchVenues = new FetchVenues();
        fetchVenues.execute();

        listView = (ListView) findViewById(R.id.venue_names);

        shareDetails();
    }

    public String[] getVenueNames (int numOfStores) throws JSONException {
        String[] allVenues = new String[numOfStores];
        for (int i = 0 ; i < numOfStores ; i++) {
            allVenues[i] = dataParser.ShopName(venuesData,i);
        }
        return allVenues;
    }

    public void shareDetails () {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String storeURL = dataParser.storeURL(venuesData, i);
                    int rating = dataParser.storeRating(venuesData, i);
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    intent.putExtra(DetailsActivity.VENUE_STORE_URL, storeURL);
                    intent.putExtra(DetailsActivity.VENUE_RATING, rating);

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public class FetchVenues extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                List<String> venueArray = new ArrayList(Arrays.asList(getVenueNames(4)));
                adapter = new ArrayAdapter(getApplicationContext(), R.layout.venue_names, R.id.list_item_venue_name, venueArray);
                listView = (ListView) findViewById(R.id.venue_names);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {

            final String PAY_LOAD_URL = "https://movesync-qa.dcsg.com/dsglabs/mobile/api/venue/";

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(PAY_LOAD_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "/n");

                }
                if (stringBuffer.length() == 0) {
                    return null;
                }
                venuesData = stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;

        }
    }
}
