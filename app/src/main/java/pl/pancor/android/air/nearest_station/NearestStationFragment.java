package pl.pancor.android.air.nearest_station;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.models.station.Data;
import pl.pancor.android.air.nearest_station.recycler_adapter.StationRecyclerAdapter;

public class NearestStationFragment extends Fragment implements NearestStation.View {

    private static final String TAG = NearestStationFragment.class.getSimpleName();

    private GridLayoutManager mLayoutManager;

    @BindView(R.id.progressBar)
        protected ProgressBar mProgressBar;
    @BindView(R.id.recyclerView)
        protected RecyclerView mRecyclerView;

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

        mPresenter.findNearestStation(getString(R.string.aqicn_token));

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void setPresenter(@NonNull NearestStation.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setStation(Data station) {

        mRecyclerView.setAdapter(new StationRecyclerAdapter(getActivity(), station));
    }

    @Override
    public void onConnectionError() {

        Snackbar.make(getView(), R.string.internet_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPresenter.findNearestStation(getString(R.string.aqicn_token));
                    }
                }).show();
    }
}
