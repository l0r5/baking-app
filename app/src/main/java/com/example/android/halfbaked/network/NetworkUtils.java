package com.example.android.halfbaked.network;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String USER_PATH = "topher";
    private static final String YEAR_PATH = "2017";
    private static final String MONTH_PATH = "May";
    private static final String BAKING_ID_PATH = "59121517_baking";
    private static final String FILE_PATH = "baking.json";

   public static URL buildRecipeCollectionUrl(Context context) {
       Uri builtUri = null;
       URL url = null;

       builtUri = Uri.parse(BASE_URL).buildUpon()
               .appendPath(USER_PATH)
               .appendPath(YEAR_PATH)
               .appendPath(MONTH_PATH)
               .appendPath(BAKING_ID_PATH)
               .appendPath(FILE_PATH)
               .build();

       if (builtUri != null) {
           try {
               url = new URL(builtUri.toString());
               Log.v(TAG, "Built Recipe Collection API URL: " + url);
           } catch (MalformedURLException e) {
               e.printStackTrace();
           }
       }

       return url;
   }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
