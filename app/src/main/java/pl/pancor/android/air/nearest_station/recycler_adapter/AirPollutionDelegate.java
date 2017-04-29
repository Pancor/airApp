package pl.pancor.android.air.nearest_station.recycler_adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;
import pl.pancor.android.air.models.Polluter;
import pl.pancor.android.air.models.Station;
import pl.pancor.android.air.utils.OtherUtils;

class AirPollutionDelegate extends AdapterDelegate<Station> {

    private LayoutInflater inflater;
    private Activity mActivity;

    AirPollutionDelegate(Activity activity){

        inflater = activity.getLayoutInflater();
        mActivity = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull Station items, int position) {
        return position >= 2;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AirPollutionHolder(inflater.inflate(R.layout.holder_air_pollution,
                parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull Station station, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        AirPollutionHolder h = (AirPollutionHolder) holder;
        Polluter polluter = station.getPolluters().get(position - 2);

        setupHorizontalChart(h.mChart);
        setData(h.mChart, polluter);

        h.mAirElementView.setText(OtherUtils.getSpannedWithSubscriptNumbers(polluter.getName()));

        String percentage =
                Math.round(polluter.getValue() / polluter.getAlertValue() * 100) +
                mActivity.getResources().getString(R.string.acceptable_standard);
        h.mPercentageView.setText(percentage);
    }

    static class AirPollutionHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.airElementView) TextView mAirElementView;
        @BindView(R.id.chart) HorizontalBarChart mChart;
        @BindView(R.id.percentageView) TextView mPercentageView;

        AirPollutionHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    private void setupHorizontalChart(HorizontalBarChart chart){

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(false);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawLabels(false);
        yl.setAxisMinimum(0f);

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);
    }

    private void setData(HorizontalBarChart chart, Polluter polluter){

        LimitLine ll = new LimitLine(polluter.getAlertValue().floatValue(), "");
        ll.setLineWidth(4f);
        chart.getAxisRight().addLimitLine(ll);

        ArrayList<BarEntry> yVals = new ArrayList<>();
        yVals.add(new BarEntry(1f, Float.parseFloat(polluter.getValue()
                .toString())));

        BarDataSet set1 = new BarDataSet(yVals, "");
        set1.setColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
        set1.setHighlightEnabled(false);

        BarData data = new BarData(set1);
        chart.setData(data);
    }
}
