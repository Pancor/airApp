package pl.pancor.android.air.nearest_station;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.base.App;
import pl.pancor.android.air.base.BaseActivity;
import pl.pancor.android.air.data.RealmModule;
import pl.pancor.android.air.find_location.FindLocationActivity;
import pl.pancor.android.air.utils.ActivityUtils;
import pl.pancor.android.air.utils.HouseView;
import pl.pancor.android.air.utils.location.LocationModule;
import pl.pancor.android.air.utils.location.LocationService;
import retrofit2.Retrofit;

public class NearestStationActivity extends BaseActivity{

    private static final String TAG = NearestStationActivity.class.getSimpleName();

    @Inject NearestStationPresenter mPresenter;
    @Inject Retrofit mRetrofit;
    @Inject LocationService mLocationService;

    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_nearest_station);
        ButterKnife.bind(this);
        setupFragment();

        mNavigationView.setCheckedItem(R.id.nearest_station);
        if (mSideNavigationView != null)
            mSideNavigationView.setCheckedItem(R.id.nearest_station);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode);
    }

    private void setupFragment(){

        NearestStationFragment stationFragment = (NearestStationFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);

        if (stationFragment == null){

            stationFragment = NearestStationFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), stationFragment, R.id.container);
        }

        DaggerNearestStationComponent.builder()
                .nearestStationModule(new NearestStationModule(stationFragment))
                .locationModule(new LocationModule(this))
                .realmModule(new RealmModule())
                .netComponent(((App)getApplication()).getNetComponent())
                .build().inject(this);
    }

    @Override
    protected int getNavItem(){
        return NAV_ITEM_NEAREST_STATION;
    }
}
