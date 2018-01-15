package venues.dickssportinggoods.com.dicksvenues;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.net.URL;

/**
 * Created by mpudota on 1/14/18.
 */

public class VenuesLoader extends AsyncTaskLoader<String[]> {

    QueryUtils queryUtils = new QueryUtils();
    private static final String TAG = VenuesLoader.class.getSimpleName();
    private String url;

    public VenuesLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    public String[] loadInBackground() {
        Log.d(TAG, "LoadInBackground");
        URL queryURL = QueryUtils.buildUrl(url);
        Log.d(TAG, "LoadInBackground");
        if (queryURL == null)
            return null;
        MainActivity.venuesData = queryUtils.makeHttpRequest(queryURL);

            return  queryUtils.getVenueNames(MainActivity.venuesData, 5);
    }
}
