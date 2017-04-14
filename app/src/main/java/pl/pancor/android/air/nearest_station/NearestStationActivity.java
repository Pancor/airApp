package pl.pancor.android.air.nearest_station;

import android.content.Intent;
import android.os.Bundle;
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
import pl.pancor.android.air.find_location.FindLocationActivity;
import pl.pancor.android.air.utils.ActivityUtils;
import pl.pancor.android.air.utils.HouseView;
import pl.pancor.android.air.utils.location.LocationModule;
import pl.pancor.android.air.utils.location.LocationService;
import retrofit2.Retrofit;

public class NearestStationActivity extends BaseActivity implements NearestStationFragment.AirQuality {

    private static final String TAG = NearestStationActivity.class.getSimpleName();

    @BindView(R.id.coordinatorLayout)
        protected CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.houseView)
        protected HouseView houseView;

    @Inject NearestStationPresenter mPresenter;
    @Inject Retrofit mRetrofit;
    @Inject LocationService mLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                .netComponent(((App)getApplication()).getNetComponent())
                .build().inject(this);
    }

    @Override
    public void onAirQualitySet(int airQuality) {

        houseView.setAirQuality(airQuality);
    }
}
