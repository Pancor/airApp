package pl.pancor.android.air.utils.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;

public class MapSearchToolbar extends FrameLayout{

    private static final String SUPER_STATE = "state_super";
    private static final String IS_SHOWED_STATE = "sate_is_showed";

    private OnMapSearchClickListener onMapSearchClickListener;

    @BindView(R.id.searchTextView) protected TextView searchTextView;
    @BindView(R.id.toolbar)        protected Toolbar toolbar;

    private boolean isShowed = true;

    public MapSearchToolbar(Context context) {
        super(context);
        init();
    }

    public MapSearchToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapSearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        LayoutInflater.from(getContext()).inflate(R.layout.view_map_search_toolbar, this, true);
        ButterKnife.bind(this);
        setupSearchPlace();
    }

    private void setupSearchPlace(){

        searchTextView.setOnClickListener(view -> onMapSearchClickListener.onSearchViewClicked());
    }

    public Toolbar getToolbar(){

        return toolbar;
    }

    public void animateView(){

        if (isShowed){

            isShowed = false;
            animate().translationY(-getHeight()).alpha(0.0f);
        } else {

            isShowed = true;
            animate().translationY(0).alpha(1.0f);
        }
    }

    private void updateView(){

        if (!isShowed)
            animate().translationY(-getHeight()).alpha(0.0f);
        else
            animate().translationY(0).alpha(1.0f);
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState());
        bundle.putBoolean(IS_SHOWED_STATE, isShowed);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {

        if (parcelable instanceof Bundle){

            Bundle bundle = (Bundle) parcelable;
            isShowed = bundle.getBoolean(IS_SHOWED_STATE);
            updateView();
            parcelable = bundle.getParcelable(SUPER_STATE);
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void setOnMapSearchClickListener(OnMapSearchClickListener listener){

        onMapSearchClickListener = listener;
    }

    public interface OnMapSearchClickListener {

        void onSearchViewClicked();
    }
}
