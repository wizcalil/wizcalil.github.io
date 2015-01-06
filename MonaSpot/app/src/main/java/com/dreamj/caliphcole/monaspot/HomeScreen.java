package com.dreamj.caliphcole.monaspot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Created by CaliphCole on 12/28/2014.
 */
public class HomeScreen extends Fragment implements AbsListView.OnScrollListener {
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
    }


}
