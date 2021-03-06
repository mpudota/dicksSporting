package venues.dickssportinggoods.com.dicksvenues;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String VENUE_STORE_URL = "storeURL";
    public static final String VENUE_RATING = "rating";
    public static final String VENUE_PHOTO_URL = "photoURL";
    public static final String VENUE_RATING_COLOR = "rating Color";
    public static final String VENUE_ADDRESS = "address";
    public static final String VENUE_LONG_LATS = "long and lats";
    public static final String VENUE_CONTACT_DETAILS = "Contacts";
    private String[] imageNotAvailable = {"http://vollrath.com/ClientCss/images/VollrathImages/No_Image_Available.jpg"};

    TextView storeUrlView, ratingView, addressView, phoneNumberView, twitterView, facebookView;
    GridView photosGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        storeUrlView = (TextView) findViewById(R.id.store_url_value);
        ratingView = (TextView) findViewById(R.id.venue_rating_value);
        photosGrid = (GridView) findViewById(R.id.gridview);
        phoneNumberView = (TextView) findViewById(R.id.phone_value);
        twitterView = (TextView) findViewById(R.id.twitter_value);
        facebookView = (TextView) findViewById(R.id.facebook_value);
        addressView = (TextView) findViewById(R.id.store_address_value);


        final String storeUrl = getIntent().getStringExtra(VENUE_STORE_URL);
        float rating = getIntent().getFloatExtra(VENUE_RATING, 0);
        final String[] contactDetails = getIntent().getStringArrayExtra(VENUE_CONTACT_DETAILS);
        String[] photoURL = getIntent().getStringArrayExtra(VENUE_PHOTO_URL);
        final String[] address = getIntent().getStringArrayExtra(VENUE_ADDRESS);
        String ratingColor = getIntent().getStringExtra(VENUE_RATING_COLOR);
        final double[] longlats = getIntent().getDoubleArrayExtra(VENUE_LONG_LATS);

        storeUrlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(storeUrl));
                startActivity(intent);
            }
        });

        if (photoURL.length > 0) {
            photosGrid.setAdapter(new ImageAdapter(photoURL, this));
        } else {
            photosGrid.setAdapter(new ImageAdapter(imageNotAvailable, this));
        }

        if (storeUrl != null) {
            storeUrlView.setText(storeUrl);
        } else { storeUrlView.setText("Not available"); }

        if (rating != -1f) {
            ratingView.setText(String.valueOf(rating));
        } else { ratingView.setText("Rating is not available"); }

        if (address != null) {

            addressView.setText(address[0] + ", " + "\n" + address[1] + ", " + address[2] + ", " + " \n" + address[3] + " - " + address[4]);
            addressView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("Long and lats", String.valueOf(longlats[1]));

                    String gmmIntent = "http://maps.google.com/maps?q=loc:" + longlats[1] + "," + longlats[0];
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntent));
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        } else {
            addressView.setText("Address is not available");
        }

        if (contactDetails != null) {
            phoneNumberView.setText("Ph no : " + contactDetails[0]);
            phoneNumberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactDetails[0]));
                    startActivity(intent);
                }
            });
        } else {
            phoneNumberView.setText("Phone number not available");
        }
        if (contactDetails != null) {
            twitterView.setText("Twitter handle : " + contactDetails[1]);

            twitterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    try {
                        getPackageManager().getPackageInfo("com.twitter.android", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id="+contactDetails[1]));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+contactDetails[1]));
                    }
                    startActivity(intent);
                }
            });
        } else { twitterView.setText("Twitter handle not available"); }
        if (contactDetails != null) {
            facebookView.setText("Facebook page name : " + contactDetails[3]);
            facebookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    try {
                        getPackageManager().getPackageInfo("com.facebook.katana", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+contactDetails[1]));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+contactDetails[1]));
                    }
                    startActivity(intent);
                }

            });
        } else { facebookView.setText("Facebook page is not available");}

        //TODO implement hexa to rgb
        //ratingView.setTextColor(Color.);

    }

    class ImageAdapter extends BaseAdapter {

        String[] photourls;
        Context mContext;

        ImageAdapter(String[] photourls, Context mContext) {
            this.photourls = photourls;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return photourls.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(R.layout.single_image_view, viewGroup, false);
            android.widget.ImageView imageView = view.findViewById(R.id.imageView);
            Picasso.with(mContext).load(photourls[i]).into(imageView);
            return view;
        }
    }
}