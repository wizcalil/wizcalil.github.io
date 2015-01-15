package com.dreamj.caliphcole.monasocial;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;


/**
 * Created by CaliphCole on 12/28/2014.
 */
public class NewsFeed extends Fragment  {

    public static final String ARG_CATEGORY_NUMBER = "newsfeed";

    private static int REFRESH_TIME_IN_SECONDS = 5;
    public  SwipeRefreshLayout swipeRefreshLayout;


    private Cache cache;
    private Cache.Entry entry = null;



    private static final String TAG = MainActivity.class.getSimpleName();

    public View rootView;
    private ListView listView;
    private FeedListAdapter listAdapter;
    public FeedListAdapter oldlistAdapter = null;
    private List<FeedItem> feedItems;
    private FragmentActivity faActivity;
    private String status;


    private String URL_FEED = "http://test123calil.co.nf/monaspot/spotbackend.php";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        faActivity  = (FragmentActivity)    super.getActivity();


        rootView = inflater.inflate(R.layout.homescreen, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(faActivity , feedItems);
        listView.setAdapter(listAdapter);

        rootView.setVisibility(android.view.View.VISIBLE);


         try {
              cache = AppController.getInstance().getRequestQueue().getCache();
              Calendar calendar = Calendar.getInstance();
              entry = cache.get(URL_FEED);
              long serverDate = entry.serverDate;

                if (entry != null) {
                    if (getMinutesDifference(serverDate, calendar.getTimeInMillis()) >= 30) {

                        AppController.getInstance().getRequestQueue().getCache().invalidate(URL_FEED, true);
                        update();
                        status = "Updated";

                    } else {
                        cache();
                        status = "No Updates";
                    }
                } else {

                    update();
                    status = "Updated";
                }
            }catch (NullPointerException e){
                update();
                status = "Updated";
                e.printStackTrace();
            }

        try {
            initUI();

        }catch(NullPointerException e){

            Toast.makeText(getActivity().getApplicationContext()," Internet Connection Issues",
                    Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        return rootView;
    }

    public static NewsFeed newInstance(int someInt, String someTitle) {
        NewsFeed nfragment = new NewsFeed();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_NUMBER, someInt);
        nfragment.setArguments(args);
        return nfragment;
    }

    public void update(){

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
    private void cache(){


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
    }

    public static long getMinutesDifference(long timeStart,long timeStop){
        long diff = timeStop - timeStart;
        long diffMinutes = diff / (60 * 1000);

        return  diffMinutes;
    }

    public void upButton(){
        listView.setSelection(0);
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

                feedItems.add(0,item);



            }

            if (feedItems.isEmpty() || listAdapter.isEmpty()){

                Toast.makeText(getActivity().getApplicationContext(),"Internet Issues",
                        Toast.LENGTH_SHORT).show();
            }else if(entry!=null && status.equals("No Updates")) {

                Toast.makeText(getActivity().getApplicationContext()," No Updates",
                        Toast.LENGTH_SHORT).show();

            }else if(status.equals("Updated")) {
                Toast.makeText(getActivity().getApplicationContext()," Updated",
                        Toast.LENGTH_SHORT).show();
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



   /* @Override
    public void onRefresh() {
        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);
        Log.d(TAG, "onRefresh SwipeRefreshLayout");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSwipeRefresh();
            }
        }, REFRESH_TIME_IN_SECONDS * 1000);
    }
    private void stopSwipeRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }*/

    private void initUI() {

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.container);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

        /*   public void update(){
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                        URL_FEED, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {
                            parseJsonFeed(response);
                        }else{

                            Toast.makeText(getActivity().getApplicationContext(),"Internet Issues",
                                    Toast.LENGTH_SHORT).show();

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

            }*/
            @Override
            public void onRefresh() {
                feedItems = new ArrayList<FeedItem>();
                try {
                    cache = AppController.getInstance().getRequestQueue().getCache();
                    Calendar calendar = Calendar.getInstance();
                    entry = cache.get(URL_FEED);
                    long serverDate = entry.serverDate;

                    if (entry != null) {
                        if (getMinutesDifference(serverDate, calendar.getTimeInMillis()) >= 0.5) {

                            AppController.getInstance().getRequestQueue().getCache().invalidate(URL_FEED, true);
                            update();
                            status = "Updated";

                        } else {
                            cache();
                            status = " No Updates";
                        }
                    } else {

                        update();
                        status = "Updated";
                    }
                }catch (NullPointerException e){
                    update();
                    status = "Updated";
                    e.printStackTrace();
                }


                listAdapter = new FeedListAdapter(getActivity(), feedItems);
                listView.setAdapter(listAdapter);


                Log.d(TAG, "onRefresh SwipeRefreshLayout");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopSwipeRefresh();



                    }
                }, REFRESH_TIME_IN_SECONDS * 1000);
            }
            private void stopSwipeRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_light,
                android.R.color.white);





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



