package venues.dickssportinggoods.com.dicksvenues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mpudota on 1/12/18.
 */

public class DataParser {

    public String ShopName (String jsonArray, int position) throws JSONException {
        JSONObject fetchedData = new JSONObject(jsonArray);
        JSONArray venues = fetchedData.getJSONArray("venues");
        JSONObject venuesInfo = venues.getJSONObject(position);
        return "DICK's Sporting Goods " + venuesInfo.getString("storeId");

    }

    public String storeURL (String jsonArray, int position) throws JSONException {
        JSONObject fetchedData = new JSONObject(jsonArray);
        JSONArray venues = fetchedData.getJSONArray("venues");
        JSONObject venuesInfo = venues.getJSONObject(position);
        return venuesInfo.getString("url");

    }

    public int storeRating (String jsonArray, int position) throws JSONException {
        JSONObject fetchedData = new JSONObject(jsonArray);
        JSONArray venues = fetchedData.getJSONArray("venues");
        JSONObject venuesInfo = venues.getJSONObject(position);
        return venuesInfo.getInt("rating");
    }

    public String photoUrl (String jsonArray, int position) throws JSONException {
        JSONObject fetchedData = new JSONObject(jsonArray);
        JSONArray venues = fetchedData.getJSONArray("venues");
        JSONObject venuesInfo = venues.getJSONObject(position);
        JSONArray photoInfo = venuesInfo.getJSONArray("photos");
        JSONObject photoIndex = photoInfo.getJSONObject(0);
        return photoIndex.getString("url");
    }


}
