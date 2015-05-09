package com.llllz.letscdf.listener;

import android.graphics.drawable.Drawable;


public interface CardListener {

    void likeswiped();

    void hateswiped();

    void onmove(float mDeltaX);

    void resetPosition();

    void ImageLoaded(Drawable drawable);
}
