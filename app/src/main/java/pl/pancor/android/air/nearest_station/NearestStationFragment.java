package pl.pancor.android.air.nearest_station;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;

public class NearestStationFragment extends Fragment implements NearestStation.View {

    @BindView(R.id.progressBar)
        protected ProgressBar mProgressBar;
    @BindView(R.id.nearestStationLayout)
        protected RelativeLayout mNearestStationLayout;

    private NearestStation.Presenter mPresenter;

    public NearestStationFragment(){}

    public static NearestStationFragment newInstance(){

        return new NearestStationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearest_station, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.findNearestStation();
    }

    @Override
    public void setPresenter(@NonNull NearestStation.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        mNearestStationLayout.setVisibility(active ? View.GONE : View.VISIBLE);
    }
}
