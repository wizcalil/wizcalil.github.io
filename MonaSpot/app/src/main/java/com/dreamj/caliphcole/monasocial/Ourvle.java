package com.dreamj.caliphcole.monasocial;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by CaliphCole on 12/30/2014.
 */
public class Ourvle extends Fragment {
    public static final String ARG_CATEGORY_NUMBER = "ourvle";

    WebView webView;
    View rootView;

    public Ourvle() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.webview, container, false);

        webView = (WebView) rootView.findViewById(R.id.webView);
        String searchURL = "http://ourvle.mona.uwi.edu/";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(searchURL);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.v("TEST", url);
                if(url.substring(url.length() - 3).equals("pdf")){
                    view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
                }
                else{
                    view.loadUrl(url);
                }
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_CATEGORY_NUMBER));
    }

    public static Ourvle newInstance(int someInt, String someTitle) {
        Ourvle ofragment = new Ourvle();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_NUMBER, someInt);
        ofragment.setArguments(args);
        return ofragment;
    }

}
