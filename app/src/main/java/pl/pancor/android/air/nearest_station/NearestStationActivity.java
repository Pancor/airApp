package pl.pancor.android.air.nearest_station;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.base.App;
import pl.pancor.android.air.base.BaseActivity;
import pl.pancor.android.air.utils.ActivityUtils;
import retrofit2.Retrofit;

public class NearestStationActivity extends BaseActivity {

    private static final String TAG = NearestStationActivity.class.getSimpleName();

    @BindView(R.id.cityView)
        protected ImageView mCityView;
    @BindView(R.id.coordinatorLayout)
        protected CoordinatorLayout mCoordinatorLayout;

    @Inject NearestStationPresenter mPresenter;
    @Inject Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_station);
        ButterKnife.bind(this);
        setupFragment();

        Glide.with(this).load(R.drawable.house)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mCityView);
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
                .nearestStationModule(new NearestStationModule(stationFragment, mRetrofit))
                .netComponent(((App)getApplication()).getNetComponent())
                .build().inject(this);
    }
}
