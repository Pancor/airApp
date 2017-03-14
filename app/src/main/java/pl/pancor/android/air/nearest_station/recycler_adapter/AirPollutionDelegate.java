package pl.pancor.android.air.nearest_station.recycler_adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.models.station.Data;

public class AirPollutionDelegate extends AdapterDelegate<Data> {

    private LayoutInflater inflater;
    private Activity mActivity;

    public AirPollutionDelegate(Activity activity){

        inflater = activity.getLayoutInflater();
        mActivity = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull Data items, int position) {
        return position >= 2;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AirPollutionHolder(inflater.inflate(R.layout.holder_air_pollution,
                parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull Data items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        AirPollutionHolder h = (AirPollutionHolder) holder;

        h.mChart.getDescription().setEnabled(false);
        h.mChart.getLegend().setEnabled(false);
        h.mChart.highlightValue(null);
        h.mChart.setDoubleTapToZoomEnabled(false);


        XAxis xl = h.mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = h.mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = h.mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        setData(h.mChart);
    }

    static class AirPollutionHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.airElementView) TextView mAirElementView;
        @BindView(R.id.chart) HorizontalBarChart mChart;

        public AirPollutionHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    private void setData(HorizontalBarChart chart){

        ArrayList<BarEntry> yVals = new ArrayList<>();

        yVals.add(new BarEntry(1f, 36));

        BarDataSet set1 = new BarDataSet(yVals, "");
        set1.setColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        chart.setData(data);
    }
}
