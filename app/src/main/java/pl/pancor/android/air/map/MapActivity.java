package pl.pancor.android.air.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.base.BaseActivity;
import pl.pancor.android.air.utils.ActivityUtils;
import pl.pancor.android.air.utils.ui.MapSearchToolbar;

public class MapActivity extends BaseActivity {

    @Inject MapPresenter presenter;

    @BindView(R.id.mapSearchView)
        MapSearchToolbar mapSearchToolbar;

    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setupFragment();

        mNavigationView.setCheckedItem(NAV_ITEM_MAP);
        if (mSideNavigationView != null)
            mSideNavigationView.setCheckedItem(NAV_ITEM_MAP);
    }

    private void setupFragment(){

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mapFragment, R.id.container);
        }

        DaggerMapComponent.builder()
                .mapModule(new MapModule(mapFragment))
                .build().inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getNavItem(){
        return NAV_ITEM_MAP;
    }
}
