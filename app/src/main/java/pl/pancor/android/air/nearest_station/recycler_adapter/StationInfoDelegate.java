package pl.pancor.android.air.nearest_station.recycler_adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.models.station.Data;

public class StationInfoDelegate extends AdapterDelegate<Data> {

    private LayoutInflater inflater;

    public StationInfoDelegate(Activity activity){

        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull Data items, int position) {

        return position == 0;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        return new StationInfoHolder(inflater.inflate(R.layout.holder_station_info,
                parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull Data items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {

        StationInfoHolder h = (StationInfoHolder) holder;
        String distance = items.getCity().getDistanceToStation() + " km";

        h.mStationName.setText(items.getCity().getName());
        h.mLastCheck.setText(items.getTime().getConvertedDate());
        h.mDistance.setText(distance);
    }

    static class StationInfoHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.nearestStationView) protected TextView mStationName;
        @BindView(R.id.lastCheckView)      protected TextView mLastCheck;
        @BindView(R.id.distanceView)       protected TextView mDistance;

        private StationInfoHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
