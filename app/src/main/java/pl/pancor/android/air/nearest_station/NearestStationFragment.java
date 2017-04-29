package pl.pancor.android.air.nearest_station;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
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
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.find_location.FindLocationActivity;
import pl.pancor.android.air.models.Station;
import pl.pancor.android.air.nearest_station.recycler_adapter.StationRecyclerAdapter;

public class NearestStationFragment extends Fragment implements NearestStation.View{

    private static final String TAG = NearestStationFragment.class.getSimpleName();
    private static final int REQUEST_LOCATION = 12;

    private GridLayoutManager mLayoutManager;

    @BindView(R.id.progressBar)
        protected ProgressBar mProgressBar;
    @BindView(R.id.recyclerView)
        protected RecyclerView mRecyclerView;

    private NearestStation.Presenter mPresenter;

    private Station mStation;

    public NearestStationFragment(){}

    public static NearestStationFragment newInstance(){

        return new NearestStationFragment();
    }

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

        mPresenter.findNearestStation(getString(R.string.air_token));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mPresenter.onStop();
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
    public void setStation(Station station) {

        mStation = station;
        mRecyclerView.setAdapter(new StationRecyclerAdapter(getActivity(), mStation));
    }

    @Override
    public void onConnectionError() {

        showErrorSnackBar(R.string.internet_error);
    }

    @Override
    public void userRefusedToGiveLocation() {

        showDialog(R.string.dialog_need_localization);
    }

    @Override
    public void couldNotObtainLocalization() {

        showDialog(R.string.dialog_could_not_get_localization);
    }

    private void showErrorSnackBar(int text){

        Snackbar.make(getView(), text, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, view ->
                        mPresenter.findNearestStation(getString(R.string.air_token)))
                .show();
    }

    private void showDialog(int text){

        new AlertDialog.Builder(getContext())
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialogInterface, i) ->
                        startActivityForResult(new Intent(getActivity(),
                            FindLocationActivity.class), REQUEST_LOCATION))
                .setNegativeButton(R.string.no, (dialogInterface, i) ->
                        dialogInterface.dismiss())
                .show();
    }

    private void handleOnActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == REQUEST_LOCATION){
            if (resultCode == Activity.RESULT_OK){

                LatLng latLng = data.getParcelableExtra(FindLocationActivity.KEY_POSITION);
                mPresenter.getStation(latLng.latitude, latLng.longitude);
            } else if (resultCode == Activity.RESULT_CANCELED){

                userRefusedToGiveLocation();
            }
        }
    }

    public interface AirQuality{

        void onAirQualitySet(int airQuality);
    }
}
