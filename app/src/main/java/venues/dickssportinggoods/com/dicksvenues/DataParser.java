package venues.dickssportinggoods.com.dicksvenues;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mpudota on 1/12/18.
 */

public class DataParser {

    public String ShopName (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            return "DICK's Sporting Goods " + venuesInfo.getString("storeId");
        } catch (JSONException e) {
            return "DICKs Sporting Goods";
        }


    }

    public String storeURL (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            return venuesInfo.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return "URL not Found";
        }


    }

    public float storeRating (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            return (float) venuesInfo.getDouble("rating");
        } catch (JSONException e) {
            return -1f;
        }

    }

    public String ratingColor (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            return venuesInfo.getString("ratingColor");
        } catch (JSONException e) {
            return null;
        }
    }

    public String[] photoUrl (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            JSONArray photoInfo = venuesInfo.getJSONArray("photos");
            String[] photoUrls = new String[photoInfo.length()];
            for (int i=0; i < photoInfo.length() ; i++) {
                JSONObject photoIndex = photoInfo.getJSONObject(i);
                photoUrls[i] = photoIndex.getString("url");
            }
            return photoUrls;
        } catch (JSONException e) {
            return null;
        }
    }

    public String[] venueAddress (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            JSONObject venue_address = venuesInfo.getJSONObject("location");
            String[] address = new String[5];

            address[0] = venue_address.getString("address");
            address[1] = venue_address.getString("city");
            address[2] = venue_address.getString("state");
            address[3] = venue_address.getString("country");
            address[4] = venue_address.getString("postalCode");
            return address;

        } catch (JSONException e) {
            Log.d("NO address being passed","failed");
            return null;
        }
    }

    public double[] longAndlat (String jsonArray, int position) {
        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            JSONObject venue_address = venuesInfo.getJSONObject("location");
            double[] longLat = new double[2];
            longLat[0] = venue_address.getDouble("longitude");
            longLat[1] = venue_address.getDouble("latitude");
            return longLat;
        } catch (JSONException e) {
            return null;
        }

    }


}
