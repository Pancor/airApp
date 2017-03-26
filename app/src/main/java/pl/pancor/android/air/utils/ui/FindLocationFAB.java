package pl.pancor.android.air.utils.ui;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

public class FindLocationFAB extends FloatingActionButton {

    private static final String STATE_SUPER = "state_super";
    private static final String STATE_IS_FAB_VISIBLE = "state_is_fab_visible";
    private static final String STATE_SHOULD_FAB_BE_VISIBLE = "state_should_fab_be_visible";

    private boolean isFabVisible = false;
    private boolean shouldFabBeVisible = false;

    public FindLocationFAB(Context context) {
        super(context);
    }

    public FindLocationFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FindLocationFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        bundle.putBoolean(STATE_IS_FAB_VISIBLE, isFabVisible);
        bundle.putBoolean(STATE_SHOULD_FAB_BE_VISIBLE, shouldFabBeVisible);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            shouldFabBeVisible = bundle.getBoolean(STATE_SHOULD_FAB_BE_VISIBLE);
            isFabVisible = bundle.getBoolean(STATE_IS_FAB_VISIBLE);
            updateFab();
            parcelable = bundle.getParcelable(STATE_SUPER);
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void animateFab(boolean show){

            if (show){
                show();
                isFabVisible = true;
            } else {
                hide();
                isFabVisible = false;
            }
    }

    public void animateFab(){

        if (shouldFabBeVisible)
            if (isFabVisible){

                isFabVisible = false;
                hide();
            } else {

                isFabVisible = true;
                show();
            }
    }

    public void shouldFabBeVisible(boolean visibility){

        shouldFabBeVisible = visibility;
    }

    private void updateFab(){

        if (shouldFabBeVisible)
            if (isFabVisible)
                show();
            else
                hide();
    }
}
