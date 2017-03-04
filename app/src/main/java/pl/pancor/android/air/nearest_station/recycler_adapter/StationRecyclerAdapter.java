package pl.pancor.android.air.nearest_station.recycler_adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import pl.pancor.android.air.models.station.Data;


public class StationRecyclerAdapter extends RecyclerView.Adapter{

    private AdapterDelegatesManager<Data> mDelegatesManager;

    private Data mStation;

    public StationRecyclerAdapter(Activity activity, Data data){

        mStation = data;

        mDelegatesManager = new AdapterDelegatesManager<>();

        mDelegatesManager.addDelegate(new StationInfoDelegate(activity));
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
        return 1;//TODO: change me!
    }
}
