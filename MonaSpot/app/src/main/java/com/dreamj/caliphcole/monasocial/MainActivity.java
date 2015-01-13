package com.dreamj.caliphcole.monasocial;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private boolean newsfeed = true,ourvle = true,sas = true,navigate = true;

    private NewsFeed newsfeedFragment;
    private Ourvle ourvleFragment;
    private Sas sasFragment;
    private UwiEmail emailFragment;
    private Navigation navFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            newsfeedFragment = NewsFeed.newInstance(0,getString(R.string.title_section1));
            FragmentManager fragmentManager0 = getSupportFragmentManager();
            fragmentManager0.beginTransaction()
                    .replace(R.id.container,newsfeedFragment)
                    .commit();

            ourvleFragment = Ourvle.newInstance(1,getString(R.string.title_section2));
            sasFragment = Sas.newInstance(2,getString(R.string.title_section3));
            emailFragment = UwiEmail.newInstance(3,getString(R.string.title_section4));
            navFragment = Navigation.newInstance(4,getString(R.string.title_section5));
        }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //Log.w("myApp", "oncreate");


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch(position){

            case 0:

                   mTitle = getString(R.string.title_section1);
                  /*  newsfeedFragment = new NewsFeed();
                    Bundle args0 = new Bundle();
                    args0.putInt(NewsFeed.ARG_CATEGORY_NUMBER, position);
                    newsfeedFragment.setArguments(args0);
                    // update the main content by replacing fragments


                    FragmentManager fragmentManager0 = getSupportFragmentManager();
                     fragmentManager0.beginTransaction()
                            .replace(R.id.container,newsfeedFragment)
                            .commit();*/


                try{
                FragmentManager fm0 = getSupportFragmentManager();
                FragmentTransaction ft0 = fm0.beginTransaction();
                if (newsfeedFragment.isAdded()) { // if the fragment is already in container
                    ft0.show(newsfeedFragment);
                } else { // fragment needs to be added to frame container
                    ft0.add(R.id.container, newsfeedFragment, NewsFeed.ARG_CATEGORY_NUMBER);
                }

                if (ourvleFragment.isAdded()) {  ft0.hide(ourvleFragment); }
                if (sasFragment.isAdded()) {  ft0.hide(sasFragment); }
                if (emailFragment.isAdded()) {  ft0.hide(emailFragment); }
                if (navFragment.isAdded()) {  ft0.hide(navFragment); }


                ft0.commit();}catch(NullPointerException e){
                    e.printStackTrace();
                }

                break;

            case 1:



               mTitle = getString(R.string.title_section2);
              /*  ourvleFragment = new Ourvle();
                Bundle args1 = new Bundle();
                args1.putInt(Ourvle.ARG_CATEGORY_NUMBER, position);
                ourvleFragment.setArguments(args1);
                // update the main content by replacing fragments


                    FragmentManager fragmentManager1 = getSupportFragmentManager();

                    fragmentManager1.beginTransaction()
                            .replace(R.id.container, ourvleFragment)
                            .commit();*/


                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                if (ourvleFragment.isAdded()) { // if the fragment is already in container
                    ft1.show(ourvleFragment);
                } else { // fragment needs to be added to frame container
                    ft1.add(R.id.container, ourvleFragment, Ourvle.ARG_CATEGORY_NUMBER);
                }
                if (emailFragment.isAdded()) { ft1.hide(emailFragment); }
                if (sasFragment.isAdded()) { ft1.hide(sasFragment); }
               if (newsfeedFragment.isAdded()) { ft1.hide(newsfeedFragment); }
                if (navFragment.isAdded()) { ft1.hide(navFragment); }

                ft1.commit();
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                /*sasFragment = new Sas();
                Bundle args2 = new Bundle();
                args2.putInt(Sas.ARG_CATEGORY_NUMBER, position);
                sasFragment.setArguments(args2);
                // update the main content by replacing fragments

                FragmentManager fragmentManager2 = getSupportFragmentManager();
                fragmentManager2.beginTransaction()
                        .replace(R.id.container, sasFragment)
                        .commit();*/


                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                if (sasFragment.isAdded()) { // if the fragment is already in container
                    ft2.show(sasFragment);
                } else { // fragment needs to be added to frame container
                    ft2.add(R.id.container, sasFragment, Sas.ARG_CATEGORY_NUMBER);
                }
                if (ourvleFragment.isAdded()) { ft2.hide(ourvleFragment); }
                if (emailFragment.isAdded()) { ft2.hide(emailFragment); }
                if (newsfeedFragment.isAdded()) { ft2.hide(newsfeedFragment); }
                if (navFragment.isAdded()) { ft2.hide(navFragment); }

                ft2.commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                /*Fragment emailFragment = new UwiEmail();
                Bundle args3 = new Bundle();
                args3.putInt(UwiEmail.ARG_CATEGORY_NUMBER, position);
                emailFragment.setArguments(args3);
                // update the main content by replacing fragments

                FragmentManager fragmentManager3 = getSupportFragmentManager();
                fragmentManager3.beginTransaction()
                        .replace(R.id.container, emailFragment)
                        .commit();*/

                FragmentManager fm3 = getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                if (emailFragment.isAdded()) { // if the fragment is already in container
                    ft3.show(emailFragment);
                } else { // fragment needs to be added to frame container
                    ft3.add(R.id.container, emailFragment, UwiEmail.ARG_CATEGORY_NUMBER);
                }
                if (ourvleFragment.isAdded()) { ft3.hide(ourvleFragment); }
                if (sasFragment.isAdded()) { ft3.hide(sasFragment); }
                if (newsfeedFragment.isAdded()) { ft3.hide(newsfeedFragment); }
                if (navFragment.isAdded()) { ft3.hide(navFragment); }

                ft3.commit();

                break;
            case 4:
                mTitle = getString(R.string.title_section5);
               /*Fragment navFragment = new Navigation();
                Bundle args4 = new Bundle();
                args4.putInt(Navigation.ARG_CATEGORY_NUMBER, position);
                navFragment.setArguments(args4);
                // update the main content by replacing fragments

                FragmentManager fragmentManager4 = getSupportFragmentManager();
                fragmentManager4.beginTransaction()
                        .replace(R.id.container, navFragment)
                        .commit();*/


                FragmentManager fm4 = getSupportFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                if (navFragment.isAdded()) { // if the fragment is already in container
                    ft4.show(navFragment);
                } else { // fragment needs to be added to frame container
                    ft4.add(R.id.container, navFragment, Navigation.ARG_CATEGORY_NUMBER);
                }
                if (ourvleFragment.isAdded()) { ft4.hide(ourvleFragment); }
                if (sasFragment.isAdded()) { ft4.hide(sasFragment); }
                //if (newsfeedFragment.isAdded()) { ft4.hide(newsfeedFragment); }
                if (emailFragment.isAdded()) { ft4.hide(emailFragment); }

                ft4.commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                break;
            case 4:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff005fbf));
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            if(mTitle.equals("Sas") || mTitle.equals("Ourvle") || mTitle.equals("Search Engine")){
                menu.findItem(R.id.backButton).setVisible(true);
                menu.findItem(R.id.homeButton).setVisible(true);
                menu.findItem(R.id.upButton).setVisible(false);
            }else if(mTitle.equals("NewsFeed") || mTitle.equals("MonaSocial")){

                menu.findItem(R.id.upButton).setVisible(true);
                menu.findItem(R.id.backButton).setVisible(false);
                menu.findItem(R.id.homeButton).setVisible(false);
            }

            else{
                menu.findItem(R.id.backButton).setVisible(false);
                menu.findItem(R.id.homeButton).setVisible(false);
                menu.findItem(R.id.upButton).setVisible(false);
            }
            return true;

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.backButton){

                try {
                    if (ourvleFragment.webView.canGoBack() == true && mTitle.equals("Ourvle")) {
                        ourvleFragment.webView.goBack();
                        return true;
                    }
                     }catch(NullPointerException e){
                        e.printStackTrace();
                    }

                try {
                    if (sasFragment.webView.canGoBack() == true && mTitle.equals("Sas")) {

                        sasFragment.webView.goBack();
                        return true;
                    }

                }catch (NullPointerException e){
                    e.printStackTrace();

                }

                 try{
                    if(emailFragment.webView.canGoBack() == true && mTitle.equals("Search Engine")){

                    emailFragment.webView.goBack();
                    return true;

                    }


                }catch(NullPointerException e){

                e.printStackTrace();
                }


        }else if(id == R.id.homeButton){


            try {
                if (ourvleFragment.webView.canGoBack() == true && mTitle.equals("Ourvle")) {

                    ourvleFragment.webView.loadUrl("http://ourvle.mona.uwi.edu/");

                    return true;
                }
            }catch(NullPointerException e){
                e.printStackTrace();
            }

            try {
                if (sasFragment.webView.canGoBack() == true && mTitle.equals("Sas")) {


                    sasFragment.webView.loadUrl("http://sas.uwimona.edu.jm:9010/pls/data_mona/twbkwbis.P_WWWLogin");

                    return true;
                }

            }catch (NullPointerException e){
                e.printStackTrace();

            }

            try{
                if(emailFragment.webView.canGoBack() == true && mTitle.equals("Search Engine")){

                    emailFragment.webView.loadUrl("http://www.google.com");

                    return true;

                }


            }catch(NullPointerException e){

                e.printStackTrace();
            }

        }else if(id == R.id.upButton){

            try{


                newsfeedFragment.upButton();



            }catch(NullPointerException e){

                e.printStackTrace();
            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }


}
