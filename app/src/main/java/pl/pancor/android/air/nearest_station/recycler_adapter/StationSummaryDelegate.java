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

public class StationSummaryDelegate extends AdapterDelegate<Data> {

    private LayoutInflater infalter;
    private Activity mActivity;

    public StationSummaryDelegate(Activity activity){

        mActivity = activity;
        infalter = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull Data items, int position) {

        return position == 1;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new StationSummaryHolder(infalter.inflate(R.layout.holder_air_summary,
                parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull Data items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        StationSummaryHolder h = (StationSummaryHolder) holder;

        h.mAirPointsView.setText(items.getAqi().toString());
        h.mAirQualityView.setText(getAirQualityText(items.getAqi()));
    }

    static class StationSummaryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.airQualityView) TextView mAirQualityView;
        @BindView(R.id.airPointsView)  TextView mAirPointsView;

        public StationSummaryHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    /**
     * @param aqi it's summary about air quality
     * @return String text, which says, how is the air quality
     */
    private String getAirQualityText(double aqi){

        String[] airQualityArray = mActivity.getResources()
                .getStringArray(R.array.air_quality_array);

        if (aqi <= 50){

            return airQualityArray[0];
        } else if (aqi <= 100){

            return airQualityArray[1];
        } else if (aqi <= 150){

            return airQualityArray[2];
        } else if (aqi <= 200){

            return airQualityArray[3];
        } else if (aqi <= 300){

            return airQualityArray[4];
        } else {

            return airQualityArray[5];
        }
    }
}
