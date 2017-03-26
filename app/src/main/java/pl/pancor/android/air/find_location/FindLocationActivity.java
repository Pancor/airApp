package pl.pancor.android.air.find_location;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pancor.android.air.R;
import pl.pancor.android.air.nearest_station.NearestStationActivity;
import pl.pancor.android.air.utils.ui.FindLocationFAB;
import pl.pancor.android.air.utils.ui.MapSearchToolbar;

public class FindLocationActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener{

    public static final String KEY_POSITION = "key_position";

    private static final String TAG = FindLocationActivity.class.getSimpleName();
    private static final int PLACE_AUTOCOMPLETE_REQUEST = 18;
    private static final int PLACE_AUTOCOMPLETE_RESOLUTION = 28;

    @BindView(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.mapSearchView)     MapSearchToolbar mMapSearchView;
    @BindView(R.id.fab)               FindLocationFAB mFab;

    private GoogleMap mMap;

    private LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupMapSearch();

        if (savedInstanceState == null)
            mFab.animateFab(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

    }

    @OnClick(R.id.fab) void sendPosition(){

        Intent intent = new Intent(this, NearestStationActivity.class);
        intent.putExtra(KEY_POSITION, mLatLng);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setupMapSearch(){

        mMapSearchView.setOnBackButtonClickListener(new MapSearchToolbar.OnMapSearchClickListener() {
            @Override
            public void onBackButtonClick() {

                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onSearchViewClicked() {
                try {
                    Intent i = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(FindLocationActivity.this);
                    startActivityForResult(i, PLACE_AUTOCOMPLETE_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mMapSearchView.setupBackButton();
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mMapSearchView.animateView();
        mFab.animateFab();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        setMarker(latLng);
    }

    private void setMarker(LatLng latLng){

        mLatLng = latLng;
        mFab.shouldFabBeVisible(true);
        mFab.animateFab(true);
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                    .position(latLng));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void handleResult(int requestCode, int resultCode, Intent data){

        switch (requestCode){
            case PLACE_AUTOCOMPLETE_REQUEST:
                switch (resultCode){
                    case RESULT_OK:
                        Place place = PlaceAutocomplete.getPlace(this, data);
                        setMarker(place.getLatLng());
                        break;
                    case PlaceAutocomplete.RESULT_ERROR:
                        Status status = PlaceAutocomplete.getStatus(this, data);

                        if (status.hasResolution()) {
                            try {
                                status.startResolutionForResult(this, PLACE_AUTOCOMPLETE_RESOLUTION);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                }
                break;
            case PLACE_AUTOCOMPLETE_RESOLUTION:
                switch (resultCode){
                    case RESULT_OK:


                        break;
                }
                break;
        }
    }
}
