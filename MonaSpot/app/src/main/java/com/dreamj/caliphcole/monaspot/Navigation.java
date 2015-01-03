package com.dreamj.caliphcole.monaspot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CaliphCole on 12/30/2014.
 */
public class Navigation extends Fragment {
    public static final String ARG_CATEGORY_NUMBER = "category_number";


    //private Google
    public Navigation() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview, container, false);
        return rootView;
    }
        @Override
        public void onAttach (Activity activity){
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_CATEGORY_NUMBER));
        }

}


