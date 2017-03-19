package pl.pancor.android.air.nearest_station;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.models.station.Data;
import pl.pancor.android.air.nearest_station.recycler_adapter.StationRecyclerAdapter;
import pl.pancor.android.air.utils.location.LocationService;

public class NearestStationFragment extends Fragment implements NearestStation.View{

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

    @Override
    public void onDetach(){
        super.onDetach();

        mPresenter.onStop();
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

        mPresenter.onStart();

        mRecyclerView.setHasFixedSize(true);

        int columns = getResources().getBoolean(R.bool.is840dp) ? 2 : 1;

        mLayoutManager = new GridLayoutManager(getContext(), columns);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mPresenter.findNearestStation(getString(R.string.aqicn_token));//TODO make it call only when bundle is null
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

        setAdapter(station);
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

    private void setAdapter(Data station){

        mRecyclerView.setAdapter(new StationRecyclerAdapter(getActivity(), station));
    }
}
