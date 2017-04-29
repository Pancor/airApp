package pl.pancor.android.air.find_location;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GoogleApiAvailability;
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
import com.google.android.gms.maps.model.MarkerOptions;

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
    private static final int GOOGLE_API_REPAIR = 38;
    private static final String STATE_LAT_LNG = "state_lat_lng";

    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.mapSearchView)     MapSearchToolbar mapSearchView;
    @BindView(R.id.fab)               FindLocationFAB fab;

    private GoogleMap map;

    //chosen by user
    private LatLng latLng;

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
            fab.animateFab(false);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (map != null)
            map.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(STATE_LAT_LNG, latLng);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        latLng = state.getParcelable(STATE_LAT_LNG);
    }


    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;
        this.map.setOnMapClickListener(this);
        this.map.setOnMapLongClickListener(this);

        if (latLng != null)
            updateMarker();
    }

    @OnClick(R.id.fab) void sendPosition(){

        Intent intent = new Intent(this, NearestStationActivity.class);
        intent.putExtra(KEY_POSITION, latLng);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupMapSearch(){

        setSupportActionBar(mapSearchView.getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mapSearchView.setOnMapSearchClickListener(() -> {
            try {
                Intent i = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .build(FindLocationActivity.this);
                startActivityForResult(i, PLACE_AUTOCOMPLETE_REQUEST);

            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
                GoogleApiAvailability.getInstance().getErrorDialog(FindLocationActivity.this,
                        e.getConnectionStatusCode(), GOOGLE_API_REPAIR);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mapSearchView.animateView();
        fab.animateFab();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        setMarker(latLng);
    }

    private void setMarker(LatLng latLng){

        this.latLng = latLng;
        fab.shouldFabBeVisible(true);
        fab.animateFab(true);

        map.addMarker(new MarkerOptions()
                    .position(latLng));

        animateMapCamera(latLng, 15);
    }

    private void animateMapCamera(LatLng position, int zoom){

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(zoom)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void updateMarker(){

        map.clear();
        map.addMarker(new MarkerOptions()
                .position(latLng));
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

                        //TODO handle me
                        break;
                }
                break;
        }
    }
}
