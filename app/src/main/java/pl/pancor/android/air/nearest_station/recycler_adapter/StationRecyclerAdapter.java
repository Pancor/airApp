package pl.pancor.android.air.nearest_station.recycler_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import pl.pancor.android.air.models.Polluter;
import pl.pancor.android.air.models.Station;

public class StationRecyclerAdapter extends RecyclerView.Adapter{

    private AdapterDelegatesManager<Station> mDelegatesManager;

    private Station mStation;

    public StationRecyclerAdapter(Activity activity, Station data){

        mStation = data;

        mDelegatesManager = new AdapterDelegatesManager<>();

        mDelegatesManager.addDelegate(new StationInfoDelegate(activity))
                         .addDelegate(new StationSummaryDelegate(activity))
                         .addDelegate(new AirPollutionDelegate(activity));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return mDelegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        mDelegatesManager.onBindViewHolder(mStation, position, holder);
    }

    @Override
    public int getItemViewType(int position) {

        return mDelegatesManager.getItemViewType(mStation, position);
    }

    @Override
    public int getItemCount() {

        List<Polluter> polluters = mStation.getPolluters();
        return polluters.size() > 0 ? 2 + polluters.size() : 0;
    }
}
