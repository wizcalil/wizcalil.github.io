package com.dreamj.caliphcole.monaspot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
    private boolean newsfeed = false,ourvle = false,sas = false,navigate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        SharedPreferences settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();

        boolean firstRun = settings.getBoolean("firstRun", true);
        Log.d("TAG1", "firstRun: " + Boolean.valueOf(firstRun).toString());
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch(position){

            case 0:
                mTitle = getString(R.string.title_section1);
                Fragment homeFragment = new HomeScreen();
                Bundle args0 = new Bundle();
                args0.putInt(HomeScreen.ARG_CATEGORY_NUMBER, position);
                homeFragment.setArguments(args0);
                // update the main content by replacing fragments


                    FragmentManager fragmentManager0 = getSupportFragmentManager();
                    fragmentManager0.beginTransaction()
                            .replace(R.id.container, homeFragment)
                            .commit();


                break;

            case 1:
                mTitle = getString(R.string.title_section2);
                Fragment ourvleFragment = new Ourvle();
                Bundle args1 = new Bundle();
                args1.putInt(Ourvle.ARG_CATEGORY_NUMBER, position);
                ourvleFragment.setArguments(args1);
                // update the main content by replacing fragments


                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    fragmentManager1.beginTransaction()
                            .replace(R.id.container, ourvleFragment)
                            .commit();

                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                Fragment sasFragment = new Sas();
                Bundle args2 = new Bundle();
                args2.putInt(Sas.ARG_CATEGORY_NUMBER, position);
                sasFragment.setArguments(args2);
                // update the main content by replacing fragments

                FragmentManager fragmentManager2 = getSupportFragmentManager();
                fragmentManager2.beginTransaction()
                        .replace(R.id.container, sasFragment)
                        .commit();

                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                Fragment emailFragment = new UwiEmail();
                Bundle args3 = new Bundle();
                args3.putInt(UwiEmail.ARG_CATEGORY_NUMBER, position);
                emailFragment.setArguments(args3);
                // update the main content by replacing fragments

                FragmentManager fragmentManager3 = getSupportFragmentManager();
                fragmentManager3.beginTransaction()
                        .replace(R.id.container, emailFragment)
                        .commit();

                break;
            case 4:
                mTitle = getString(R.string.title_section5);
               Fragment navFragment = new Navigation();
                Bundle args4 = new Bundle();
                args4.putInt(Navigation.ARG_CATEGORY_NUMBER, position);
                navFragment.setArguments(args4);
                // update the main content by replacing fragments

                FragmentManager fragmentManager4 = getSupportFragmentManager();
                fragmentManager4.beginTransaction()
                        .replace(R.id.container, navFragment)
                        .commit();
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
        }

        return super.onOptionsItemSelected(item);
    }


}
