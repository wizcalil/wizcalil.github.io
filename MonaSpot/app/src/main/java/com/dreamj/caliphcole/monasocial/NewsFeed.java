package com.dreamj.caliphcole.monasocial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dreamj.caliphcole.monasocial.adapter.FeedListAdapter;
import com.dreamj.caliphcole.monasocial.app.AppController;
import com.dreamj.caliphcole.monasocial.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CaliphCole on 12/28/2014.
 */
public class NewsFeed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

public static final String ARG_CATEGORY_NUMBER = "newsfeed";
    View rootView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://test123calil.co.nf/monaspot/spotbackend.php";//http://api.androidhive.info/feed/feed.json";

    private FragmentActivity faActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        faActivity  = (FragmentActivity)    super.getActivity();
        View View = inflater.inflate(R.layout.homescreen, container, false);

        listView = (ListView) View.findViewById(R.id.listView);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(faActivity , feedItems);
        listView.setAdapter(listAdapter);

        View.setVisibility(android.view.View.VISIBLE);





        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }



        return View;
    }

    public static NewsFeed newInstance(int someInt, String someTitle) {
        NewsFeed nfragment = new NewsFeed();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_NUMBER, someInt);
        nfragment.setArguments(args);
        return nfragment;
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onRefresh() {
        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);
    }


}





        /*implements AbsListView.OnScrollListener {
        public static final String ARG_CATEGORY_NUMBER = "category_number";
        private ListView listView;
        private ProgressBar progressBar;
        private InfiniteScrollAdapter<String> adapter;
        private Handler mHandler;
        View rootView;

        public HomeScreen() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            mHandler = new Handler();

            View footer = View.inflate(getActivity().getApplicationContext(),R.layout.progress_bar_footer,null);
            progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
            rootView = inflater.inflate(R.layout.homescreen, container, false);
            listView = (ListView)rootView.findViewById(R.id.listView);


            listView.addFooterView(footer);



            String[] vals = fibonacciCalculator.getfibonacci();
            adapter = new InfiniteScrollAdapter<String>(getActivity(), vals, 20, 10);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(this); //listen for a scroll movement to the bottom
            rootView.setVisibility((20 < vals.length)? View.VISIBLE : View.GONE);

            return rootView;
        }
        @Override
        public void onAttach (Activity activity){
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_CATEGORY_NUMBER));
        }


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        private boolean hasCallback;
        private Runnable showMore = new Runnable(){
            public void run(){
                boolean noMoreToShow = adapter.showMore(); //show more views and find out if
                rootView.setVisibility(noMoreToShow? View.GONE : View.VISIBLE);
                hasCallback = false;
            }
        };
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            if(firstVisibleItem + visibleItemCount == totalItemCount && !adapter.endReached() && !hasCallback){ //check if we've reached the bottom
                mHandler.postDelayed(showMore, 300);
                hasCallback = true;
            }
        }*/



