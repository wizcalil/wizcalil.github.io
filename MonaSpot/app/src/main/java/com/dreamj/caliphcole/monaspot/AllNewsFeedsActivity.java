package com.dreamj.caliphcole.monaspot;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CaliphCole on 01/03/2015.
 */
public class AllNewsFeedsActivity extends ListActivity {

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    private static String []results;

    ArrayList<HashMap<String, String>> newsFeedList;

    // url to get all products list
    private static String url_all_newsfeed = "https://www.test123calil.co.nf/newsfeedoutput.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NEWSFEED = "newsfeed";
    private static final String TAG_ID = "id";
    private static final String TAG_USER = "user";
    private static final String TAG_TOPIC = "topic";
    private static final String TAG_DETAILS ="details";
    private static final String TAG_IMAGE = "image";

    // products JSONArray
    JSONArray newsfeed = null;

    public static void something(){

    }
    public static String[] newsFeedArray(){
        new AllNewsFeedsActivity().new LoadAllNewsFeed().execute();
        return results;

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
     class LoadAllNewsFeed extends AsyncTask<String, String, String> {



        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_newsfeed, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("News Feed: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    newsfeed = json.getJSONArray(TAG_NEWSFEED);

                    // looping through All Products
                    for (int i = 0; i < newsfeed.length(); i++) {
                        JSONObject c = newsfeed.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String user = c.getString(TAG_USER);
                        String topic = c.getString(TAG_TOPIC);
                        String details = c.getString(TAG_DETAILS);
                        String image = c.getString(TAG_IMAGE);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_USER, user);
                        map.put(TAG_TOPIC,topic);
                        map.put(TAG_DETAILS,details);
                        map.put(TAG_IMAGE,image);


                        // adding HashList to ArrayList
                        newsFeedList.add(map);
                    }
                } else {

                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            results = new String[newsFeedList.size()];
            newsFeedList.toArray(results);

            return null;
        }



        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllNewsFeedsActivity.this, newsFeedList,
                            R.layout.row_layout, new String[] { TAG_ID,
                            TAG_USER,TAG_TOPIC,TAG_DETAILS,TAG_IMAGE},
                            new int[] { R.id.textView, R.id.textView });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }

}
