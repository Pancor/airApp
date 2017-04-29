package pl.pancor.android.air.map;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pl.pancor.android.air.R;

public class MapFragment extends Fragment implements MapContract.View {

    private MapContract.Presenter presenter;

    public MapFragment(){}

    public static MapFragment newInstance(){

        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {

        this.presenter = presenter;
    }
}
