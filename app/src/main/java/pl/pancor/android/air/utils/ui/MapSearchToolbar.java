package pl.pancor.android.air.utils.ui;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pancor.android.air.R;

public class MapSearchToolbar extends FrameLayout{

    private static final String SUPER_STATE = "state_super";
    private static final String DRAWER_STATE = "state_is_drawer_open";
    private static final String IS_SHOWED_STATE = "sate_is_showed";

    private OnMapSearchClickListener mOnMapSearchClickListener;

    @BindView(R.id.actionView)     protected ImageView mActionView;
    @BindView(R.id.searchTextView) protected TextView mSearchTextView;

    private boolean isDrawerOpen = false;
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

        mSearchTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnMapSearchClickListener.onSearchViewClicked();
            }
        });
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

    public void setupBackButton(){

        mActionView.setImageResource(R.drawable.ic_arrow_back_black_24dp);
    }

    public void setOnBackButtonClickListener(OnMapSearchClickListener listener){

        mOnMapSearchClickListener = listener;
        mActionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnMapSearchClickListener.onBackButtonClick();
            }
        });
    }

    public void setMenuDrawer(final DrawerLayout drawerLayout){

        mActionView.setImageResource(R.drawable.ic_menu_black_24dp);

        mActionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isDrawerOpen){

                    isDrawerOpen = false;
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {

                    isDrawerOpen = true;
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState());
        bundle.putBoolean(IS_SHOWED_STATE, isShowed);
        bundle.putBoolean(DRAWER_STATE, isDrawerOpen);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {

        if (parcelable instanceof Bundle){

            Bundle bundle = (Bundle) parcelable;
            isShowed = bundle.getBoolean(IS_SHOWED_STATE);
            isDrawerOpen = bundle.getBoolean(DRAWER_STATE);
            updateView();
            parcelable = bundle.getParcelable(SUPER_STATE);
        }
        super.onRestoreInstanceState(parcelable);
    }

    public interface OnMapSearchClickListener {

        void onBackButtonClick();

        void onSearchViewClicked();
    }
}
