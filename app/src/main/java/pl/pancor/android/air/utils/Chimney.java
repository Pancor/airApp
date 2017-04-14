package pl.pancor.android.air.utils;


import android.graphics.Bitmap;

public class Chimney extends Renderable{



    public Chimney(Bitmap bitmap, float x, float y) {
        super(bitmap, x - (bitmap.getWidth() / 2), y);
    }
}
