package com.trikersdev.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.trikersdev.secret.app.App;
import com.trikersdev.secret.common.ActivityBase;
import com.trikersdev.secret.dialogs.MyPostActionDialog;
import com.trikersdev.secret.dialogs.NearbySettingsDialog;
import com.trikersdev.secret.dialogs.PopularSettingsDialog;
import com.trikersdev.secret.dialogs.PostActionDialog;
import com.trikersdev.secret.dialogs.PostDeleteDialog;
import com.trikersdev.secret.dialogs.PostReportDialog;


public class MainActivity extends ActivityBase implements FragmentDrawer.FragmentDrawerListener, PostDeleteDialog.AlertPositiveListener, PostReportDialog.AlertPositiveListener, MyPostActionDialog.AlertPositiveListener, PostActionDialog.AlertPositiveListener, NearbySettingsDialog.AlertPositiveListener, PopularSettingsDialog.AlertPositiveListener {

    Toolbar mToolbar;
    Spinner mNavSpinner;

    private FragmentDrawer drawerFragment;

    // used to store app title
    private CharSequence mTitle;

    LinearLayout mContainerAdmob;

    Fragment fragment;
    Boolean action = false;
    int page = 0, navSpinnerSelection = 0;

    private Boolean restore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

            //Restore the fragment's instance
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");

            restore = savedInstanceState.getBoolean("restore");
            mTitle = savedInstanceState.getString("mTitle");
            page = savedInstanceState.getInt("page");
            navSpinnerSelection = savedInstanceState.getInt("navSpinnerSelection");

        } else {

            fragment = new FeedFragment();

            restore = false;
            mTitle = getString(R.string.app_name);
            page = 0;
            navSpinnerSelection = 0;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mTitle);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.navSpinnerItems, R.layout.layout_drop_title);
        spinnerAdapter.setDropDownViewResource(R.layout.layout_drop_list);

        mNavSpinner = new Spinner(getSupportActionBar().getThemedContext());
        mNavSpinner.setAdapter(spinnerAdapter);

        mToolbar.addView(mNavSpinner);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mNavSpinner.setVisibility(View.GONE);

        mNavSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();

                if (navSpinnerSelection != position) {

                    navSpinnerSelection = position;

                    StreamFragment p = (StreamFragment) fragment;
                    p.onCategoryChange(navSpinnerSelection);
                }

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drawerFragment = (FragmentDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        mContainerAdmob = (LinearLayout) findViewById(R.id.container_admob);

        if (App.getInstance().getAdmob() == ADMOB_ENABLED) {

            mContainerAdmob.setVisibility(View.VISIBLE);

            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        if (!restore) {

            displayView(3);
        }

        setSpinnerState(page);
    }

    public void setSpinnerState(int page) {

        switch (page) {

            case 3: {

                mNavSpinner.setSelection(navSpinnerSelection);

                getSupportActionBar().setDisplayShowTitleEnabled(false);
                mNavSpinner.setVisibility(View.VISIBLE);

                break;
            }

            default: {

                getSupportActionBar().setDisplayShowTitleEnabled(true);
                mNavSpinner.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBoolean("restore", true);

        if (getSupportActionBar().getTitle() != null) {

            outState.putString("mTitle", getSupportActionBar().getTitle().toString());
        }

        outState.putInt("page", page);
        outState.putInt("navSpinnerSelection", navSpinnerSelection);
        getSupportFragmentManager().putFragment(outState, "currentFragment", fragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onChangeDistance(int position) {

        NearbyFragment p = (NearbyFragment) fragment;
        p.onChangeDistance(position);
    }

    @Override
    public void onChangePopularCategory(int position) {

        PopularFragment p = (PopularFragment) fragment;
        p.onChangeCategory(position);
    }

    @Override
    public void onPostShare(int position) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.onPostShare(position);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.onPostShare(position);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.onPostShare(position);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.onPostShare(position);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.onPostShare(position);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onPostCreateChat(int position) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.onPostCreateChat(position);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.onPostCreateChat(position);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.onPostCreateChat(position);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.onPostCreateChat(position);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.onPostCreateChat(position);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onPostFollow(int position) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.onPostFollow(position);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.onPostFollow(position);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.onPostFollow(position);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.onPostFollow(position);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.onPostFollow(position);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onPostDelete(int position) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.onPostDelete(position);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.onPostDelete(position);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.onPostDelete(position);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.onPostDelete(position);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.onPostDelete(position);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onPostRemove(int position) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.onPostRemove(position);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.onPostRemove(position);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.onPostRemove(position);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.onPostRemove(position);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.onPostRemove(position);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onPostReportDialog(int position) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.report(position);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.report(position);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.report(position);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.report(position);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.report(position);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onPostReport(int position, int reasonId) {

        switch (page) {

            case 1: {

                NearbyFragment p = (NearbyFragment) fragment;
                p.onPostReport(position, reasonId);

                break;
            }

            case 2: {

                FeedFragment p = (FeedFragment) fragment;
                p.onPostReport(position, reasonId);

                break;
            }

            case 3: {

                StreamFragment p = (StreamFragment) fragment;
                p.onPostReport(position, reasonId);

                break;
            }

            case 4: {

                PopularFragment p = (PopularFragment) fragment;
                p.onPostReport(position, reasonId);

                break;
            }

            case 7: {

                FavoritesFragment p = (FavoritesFragment) fragment;
                p.onPostReport(position, reasonId);

                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);
    }

    private void displayView(int position) {

        action = false;

        switch (position) {

            case 0: {

                break;
            }

            case 1: {

                page = 1;

                fragment = new NearbyFragment();
                getSupportActionBar().setTitle(R.string.page_1);

                action = true;

                break;
            }

            case 2: {

                page = 2;

                fragment = new FeedFragment();
                getSupportActionBar().setTitle(R.string.page_2);

                action = true;

                break;
            }

            case 3: {

                page = 3;

                fragment = new StreamFragment();

                Bundle args = new Bundle();
                args.putInt("category", navSpinnerSelection);
                fragment.setArguments(args);

                getSupportActionBar().setTitle(R.string.page_3);

                action = true;

                break;
            }

            case 4: {

                page = 4;

                fragment = new PopularFragment();
                getSupportActionBar().setTitle(R.string.page_4);

                action = true;

                break;
            }

            case 5: {

                page = 5;

                fragment = new NotificationsFragment();
                getSupportActionBar().setTitle(R.string.page_5);

                action = true;

                break;
            }

            case 6: {

                page = 6;

                fragment = new DialogsFragment();
                getSupportActionBar().setTitle(R.string.page_6);

                action = true;

                break;
            }

            case 7: {

                page = 7;

                fragment = new FavoritesFragment();
                getSupportActionBar().setTitle(R.string.page_7);

                action = true;

                break;
            }

            default: {

                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        }

        if (action) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_body, fragment)
                    .commit();

            setSpinnerState(page);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home: {

                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerFragment.isDrawerOpen()) {

            drawerFragment.closeDrawer();

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public void setTitle(CharSequence title) {

        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
