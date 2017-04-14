package pl.pancor.android.air.nearest_station.recycler_adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.models.Station;

public class StationSummaryDelegate extends AdapterDelegate<Station> {

    private LayoutInflater inflater;
    private Activity mActivity;

    public StationSummaryDelegate(Activity activity){

        mActivity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull Station items, int position) {

        return position == 1;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new StationSummaryHolder(inflater.inflate(R.layout.holder_air_summary,
                parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull Station items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        StationSummaryHolder h = (StationSummaryHolder) holder;

        h.mAirQualityView.setText(getAirQualityText(items.getAqIndex()));
    }

    static class StationSummaryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.airQualityView) TextView mAirQualityView;

        StationSummaryHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    /**
     * @param aqi it's summary about air quality
     * @return String text, which says, how is the air quality
     */
    private String getAirQualityText(int aqi){

        String[] airQualityArray = mActivity.getResources()
                .getStringArray(R.array.air_quality_array);

        switch (aqi){
            case 0:
                return airQualityArray[0];
            case 1:
                return airQualityArray[1];
            case 2:
                return airQualityArray[2];
            case 3:
                return airQualityArray[3];
            case 4:
                return airQualityArray[4];
            case 5:
                return airQualityArray[5];
            default:
                return airQualityArray[6];
        }
    }
}
