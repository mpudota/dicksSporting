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
            return venuesInfo.getString("name") +" " + venuesInfo.getString("storeId") + position;
        } catch (JSONException e) {
            return "DICKs Sporting Goods" + position;
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
            longLat[0] = venue_address.getDouble("latitude");
            longLat[1] = venue_address.getDouble("longitude");
            return longLat;
        } catch (JSONException e) {
            return null;
        }

    }

    public String[] contacts (String jsonArray, int position) {

        try {
            JSONObject fetchedData = new JSONObject(jsonArray);
            JSONArray venues = fetchedData.getJSONArray("venues");
            JSONObject venuesInfo = venues.getJSONObject(position);
            JSONArray contacts = venuesInfo.getJSONArray("contacts");
            JSONObject contactsInfo = contacts.getJSONObject(0);
            String[] strings = new String [4];
            strings[0] = contactsInfo.getString("phone");
            strings[1] = contactsInfo.getString("twitter");
            strings[2] = contactsInfo.getString("facebook");
            strings[3] = contactsInfo.getString("facebookName");
            return strings;
        } catch (JSONException e) {
            return null;
        }

    }
}