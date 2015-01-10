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
public class UwiEmail  extends Fragment{

    public static final String ARG_CATEGORY_NUMBER = "email";
    private WebView webView;

    public UwiEmail() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview, container, false);

         webView = (WebView) rootView.findViewById(R.id.webView);
        String searchURL = "https://www.google.com";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Toast.makeText(getActivity().getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Log.v("TEST", url);
                if(url.substring(url.length() - 3).equals("pdf")){
                    String g = "http://docs.google.com/gview?embedded=true&url=";
                    webView.loadUrl( g + url);
                }
                else{
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webView.loadUrl(searchURL);

        return rootView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_CATEGORY_NUMBER));
    }

    public static UwiEmail newInstance(int someInt, String someTitle) {
        UwiEmail ufragment = new UwiEmail();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_NUMBER, someInt);
        ufragment.setArguments(args);
        return ufragment;
    }

}
