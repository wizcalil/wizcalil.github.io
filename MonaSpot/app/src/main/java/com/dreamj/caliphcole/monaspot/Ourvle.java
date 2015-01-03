package com.dreamj.caliphcole.monaspot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by CaliphCole on 12/30/2014.
 */
public class Ourvle extends Fragment {
    public static final String ARG_CATEGORY_NUMBER = "category_number";

    public Ourvle() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview, container, false);

        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        String searchURL = "http://ourvle.mona.uwi.edu/";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(searchURL);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_CATEGORY_NUMBER));
    }
}
