package venues.dickssportinggoods.com.dicksvenues;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by mpudota on 1/14/18.
 */

public class QueryUtils {

    DataParser dataParser = new DataParser();

    public static final String TAG = QueryUtils.class.getSimpleName();

    public String[] getVenueNames (String response, int numOfStores) {
        String[] allVenues = new String[numOfStores];
        for (int i = 0 ; i < numOfStores ; i++) {
            allVenues[i] = dataParser.ShopName(response,i);
        }
        return allVenues;
    }

    public static URL buildUrl (String rawurl) {
        URL url = null;
        if (rawurl != null) {
            try {
                url = new URL(rawurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    protected String makeHttpRequest (URL url) {
        String jsonResponse = null;
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.d(TAG, "Succesful connection");
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else
                Log.e (TAG, "Http response code" + urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {

        } finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputReader = new InputStreamReader(inputStream, Charset.defaultCharset());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            try {
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
