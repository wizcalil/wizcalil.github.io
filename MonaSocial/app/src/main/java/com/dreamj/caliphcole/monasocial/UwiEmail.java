package com.dreamj.caliphcole.monasocial;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CaliphCole on 12/30/2014.
 */
public class UwiEmail  extends Fragment{

    public static final String ARG_CATEGORY_NUMBER = "email";
    public WebView webView;
    private ProgressBar progress;
    private View rootView;
    private Thread runTime;

    public UwiEmail() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.webview, container, false);



        progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        getActivity().getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        progress.getProgressDrawable().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);




         webView = (WebView) rootView.findViewById(R.id.webView);
        String searchURL = "https://www.google.com";

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);

        runTime = new Thread() {
            public void run() {
                try {
                    synchronized (this) {
                        wait(1000);
                        progress.setProgress(20);
                        wait(2000);
                        progress.setProgress(50);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        runTime.start();
        webView.loadUrl(searchURL);
        webView.setWebViewClient(new WebViewClient() {



            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // handle different requests for different type of files
                // this example handles downloads requests for .apk and .mp3 files
                // everything else the webview can handle normally

                progress.setVisibility(View.VISIBLE);
                progress.setProgress(0);



                runTime = new Thread() {
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(1000);
                                progress.setProgress(20);
                                wait(2000);
                                progress.setProgress(50);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                runTime.start();












                if (url.endsWith(".pdf") || url.endsWith(".ppt") ||url.endsWith(".pptx") || url.endsWith(".docx")
                        || url.endsWith(".doc") ||url.endsWith(".pps") || url.endsWith(".ppsx") ) {


                    Uri source = Uri.parse(url);
                    // Make a new request pointing to the .apk url
                    DownloadManager.Request request = new DownloadManager.Request(source);


                    String cookie = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("Cookie", cookie);
                    request.addRequestHeader("User-Agent", view.getSettings().getUserAgentString());
                    request.addRequestHeader("Accept", "text/html, application/xhtml+xml, *" + "/" + "*");
                    request.addRequestHeader("Accept-Language", "en-US,en;q=0.7,he;q=0.3");
                    request.addRequestHeader("Referer", url);
                    // appears the same in Notification bar while downloading
                    request.setDescription("Description for the DownloadManager Bar");

                    URL myUrl = null;
                    try{
                        myUrl = new URL(url);
                    }catch (MalformedURLException e){
                        e.printStackTrace();
                    }

                    String[] separated = url.split("/");
                    String lasttdownload = separated[separated.length - 1];

                    request.setTitle(lasttdownload);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    // save the file in the "Downloads" folder of SDCARD

                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, lasttdownload );
                    // get download service and enqueue file
                    DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }else{

                    view.loadUrl(url);



                }
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {



                runTime = new Thread() {
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(1000);
                                progress.setProgress(70);
                                wait(2000);
                                progress.setProgress(1000);
                                wait(1500);
                                progress.setProgress(0);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                runTime.start();



            }



            });


       /* webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //start download
                DownloadPDF downloadPDF = new DownloadPDF();
                //Toast.makeText(getActivity().getApplicationContext(), url, Toast.LENGTH_LONG).show();
                downloadPDF.execute(url,userAgent,contentDisposition);

            }
        });*/

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


  /*  private class DownloadPDF extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);

                File myDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).toString()+"/myPDF");

                // create the directory if it does not exist
                if (!myDir.exists()) myDir.mkdirs();

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.connect();

               //get filename from the contentDisposition
                String filename = null;
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(sUrl[2]);


              Log.v("test", "hcvjhcfjh");
                while (m.find()) {
                    filename = m.group(1);
                }



                     File  outputFile = new File(myDir, "future.pdf");


                InputStream input   = new BufferedInputStream(connection.getInputStream());
                OutputStream output = new FileOutputStream(outputFile);

                byte data[] = new byte[1024];
                double total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                connection.disconnect();
                output.flush();
                output.close();
                input.close();

                // displayPdf();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/

}
