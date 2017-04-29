package pl.pancor.android.air.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.map.MapActivity;
import pl.pancor.android.air.nearest_station.NearestStationActivity;

public class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected static int NAV_ITEM_INVALID = -1;
    protected static final int NAV_ITEM_NEAREST_STATION = R.id.nearest_station;
    protected static final int NAV_ITEM_MAP = R.id.map;

    @Nullable @BindView(R.id.drawer_layout)
        protected DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.toolbar)
        protected Toolbar mToolbar;
    @Nullable @BindView(R.id.nav_view)
        protected NavigationView mNavigationView;

    //layout-w600dp
    @Nullable @BindView(R.id.side_nav_view)
        protected NavigationView mSideNavigationView;

    protected ActionBarDrawerToggle mActionBarDrawerToggle = null;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbar();
        setDrawer();
        loadDrawerHeader();
        setupSideNavigationView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setupNavigationItem();
    }

    protected int getNavItem() {
        return NAV_ITEM_INVALID;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mActionBarDrawerToggle != null)
            mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mActionBarDrawerToggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void setupNavigationItem(){

        if (mNavigationView != null)
            mNavigationView.setCheckedItem(getNavItem());

        if (mSideNavigationView != null)
            mSideNavigationView.setCheckedItem(getNavItem());
    }

    private void setToolbar(){

        if (mToolbar != null){

            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);

        } else
            Log.w(TAG, "Couldn't set toolbar");
    }

    private void setDrawer(){

        if (mDrawerLayout != null){

            mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    mToolbar, R.string.open, R.string.close){

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);

                }
            };
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
            mActionBarDrawerToggle.syncState();

        } else
            Log.w(TAG, "Couldn't set drawer");
    }

    private void loadDrawerHeader(){

        if (mNavigationView != null){

            mNavigationView.setNavigationItemSelectedListener(this);

            View drawerHeader = mNavigationView.getHeaderView(0);

            ImageView appIconView = (ImageView)
                    drawerHeader.findViewById(R.id.appIconView);
            TextView appVersionView = (TextView)
                    drawerHeader.findViewById(R.id.appVersionView);

            Glide.with(this).load(R.mipmap.ic_launcher)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(appIconView);
            try {
                String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                appVersionView.setText(versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else
            Log.w(TAG, "Couldn't load navigationView");
    }

    private void setupSideNavigationView(){

        if (mSideNavigationView != null)
            mSideNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case NAV_ITEM_NEAREST_STATION:
                if (getNavItem() != NAV_ITEM_NEAREST_STATION)
                    openActivity(NearestStationActivity.class, true);
                break;
            case NAV_ITEM_MAP:
                if (getNavItem() != NAV_ITEM_MAP)
                    openActivity(MapActivity.class);
                break;
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Starts new activity.
     * @param activity Activity to open
     */
    private void openActivity(Class activity){

        openActivity(activity, false);
    }

    /**
     * Starts new activity.
     * @param activity Activity to open
     * @param clearBackStack If it's true then clear back stack otherwise don't do it.
     */
    private void openActivity(Class activity, boolean clearBackStack){

        Intent intent = new Intent(this, activity);
        if (clearBackStack)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
