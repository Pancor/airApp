package pl.pancor.android.air.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import pl.pancor.android.air.R;

public class HouseView extends View {

    private static final String TAG = HouseView.class.getSimpleName();

    private Renderable[] renderables;
    private int index;
    private int airQuality;

    private Smoke smoke;
    private Chimney chimney;
    private Smog smog;

    private static long mLastTime;

    public HouseView(Context context) {
        super(context);
    }

    public HouseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HouseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (renderables == null)
            init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (renderables == null && getWidth() != 0)
            init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroyResources();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawView(canvas);
    }

    public void setAirQuality(int airQuality){

        this.airQuality = airQuality;
        destroyResources();
        init();
    }

    private void drawView(Canvas canvas){
/*TODO
        float deltaTime = timeStep();

        for (Renderable renderable : renderables){

            renderable.draw(canvas);
            if (renderable instanceof Smoke || renderable instanceof Smog)
                renderable.update(deltaTime, 0);
        }
        invalidate();*/
    }

    private void init(){
/*TODO
        renderables = new Renderable[3];

        addSmoke(getMeasuredWidth() * 0.7f, getMeasuredHeight() * 0.405f);
        addChimney(getMeasuredWidth() * 0.7f, getMeasuredHeight() * 0.4f);
        addSmog();

        setLayerType(View.LAYER_TYPE_HARDWARE, null);*/
    }

    private void addSmog(){

        Bitmap smog = BitmapFactory.decodeResource(getResources(), R.drawable.smoke);
        Bitmap foam = BitmapFactory.decodeResource(getResources(), R.drawable.foam);

        this.smog = new Smog(smog, foam, getHeight() * 0.65f, getHeight() * 1f,
                getWidth() * 1f, 6);

        renderables[index] = this.smog;
        index++;
    }

    private void addChimney(float x, float y){

        Bitmap chimney = BitmapFactory.decodeResource(getResources(), R.drawable.chimney);

        this.chimney = new Chimney(chimney, x, y);
        renderables[index] = this.chimney;
        index++;
    }

    private void addSmoke(float x, float y){

        Bitmap smoke = BitmapFactory.decodeResource(getResources(), R.drawable.smoke);
        smoke = changeBitmapColor(smoke, airQuality);

        float density = getResources().getDisplayMetrics().density;

        this.smoke = new Smoke(smoke, x, y,
                110 * density, 20 * density, 8, density);
        renderables[index] = this.smoke;
        index++;
    }

    private Bitmap changeBitmapColor(Bitmap sourceBitmap, int value) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();

        int rgb = (300 - value) / 300 * 255;

        int color = Color.rgb(rgb, rgb, rgb);
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        p.setColorFilter(filter);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);

        return resultBitmap;
    }

    private void destroyResources() {
/*TODO
        for (Renderable renderable: renderables)
            renderable.destroy();

        renderables = null;
        index = 0;*/
    }

    private float timeStep() {
        final long time = SystemClock.uptimeMillis();
        final long timeDelta = time - mLastTime;
        float timeDeltaSeconds = mLastTime > 0.0f ? timeDelta / 1000.0f : 0.0f;
        mLastTime = time;
        return Math.min(0.021f, timeDeltaSeconds);
    }
}
