package pl.pancor.android.air.nearest_station.recycler_adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

import pl.pancor.android.air.R;
import pl.pancor.android.air.models.Polluter;
import pl.pancor.android.air.models.Station;

public class StationRecyclerAdapter extends RecyclerView.Adapter{

    private Context context;

    private AdapterDelegatesManager<Station> delegatesManager;

    private Station station;

    // for show up/hide animation
    private int lastPosition = -1;

    public StationRecyclerAdapter(Activity activity, Station data){

        station = data;
        context = activity;

        delegatesManager = new AdapterDelegatesManager<>();

        delegatesManager.addDelegate(new StationInfoDelegate(activity))
                        .addDelegate(new StationSummaryDelegate(activity))
                        .addDelegate(new AirPollutionDelegate(activity));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        delegatesManager.onBindViewHolder(station, position, holder);
        animateItem(holder, position);
    }

    @Override
    public int getItemViewType(int position) {

        return delegatesManager.getItemViewType(station, position);
    }

    @Override
    public int getItemCount() {

        List<Polluter> polluters = station.getPolluters();
        return polluters.size() > 0 ? 2 + polluters.size() : 0;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    private void animateItem(RecyclerView.ViewHolder holder, int position){

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }
}
