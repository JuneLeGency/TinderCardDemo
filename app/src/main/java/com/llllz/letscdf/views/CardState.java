package com.llllz.letscdf.views;

import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Collection;

public class CardState {
    public float mAlphaState;
    public float mPivotX;
    public float mPivotY;
    public float mTranslationX;
    public float mTranslationY;
    public float mRotation;
    public float mRotationX;
    public float mRotationY;
    public float mScaleX;
    public float mScaleY;
    public float mScrollX;
    public float mScrollY;
    public float mx;
    public float my;

    public CardState() {
        super();
    }

    public CardState(float mAlphaState, float mPivotX, float mPivotY,
                     float mTranslationX, float mTranslationY, float mRotation,
                     float mRotationX, float mRotationY, float mScaleX, float mScaleY,
                     float mScrollX, float mScrollY, float mx, float my) {
        super();
        this.mAlphaState = mAlphaState;
        this.mPivotX = mPivotX;
        this.mPivotY = mPivotY;
        this.mTranslationX = mTranslationX;
        this.mTranslationY = mTranslationY;
        this.mRotation = mRotation;
        this.mRotationX = mRotationX;
        this.mRotationY = mRotationY;
        this.mScaleX = mScaleX;
        this.mScaleY = mScaleY;
        this.mScrollX = mScrollX;
        this.mScrollY = mScrollY;
        this.mx = mx;
        this.my = my;
    }

    protected CardState clone() {
        CardState cardState = new CardState();
        cardState.mAlphaState = mAlphaState;
        cardState.mPivotX = mPivotX;
        cardState.mPivotY = mPivotY;
        cardState.mTranslationX = mTranslationX;
        cardState.mTranslationY = mTranslationY;
        cardState.mRotation = mRotation;
        cardState.mRotationX = mRotationX;
        cardState.mRotationY = mRotationY;
        cardState.mScaleX = mScaleX;
        cardState.mScaleY = mScaleY;
        cardState.mScrollX = mScrollX;
        cardState.mScrollY = mScrollY;
        cardState.mx = mx;
        cardState.my = my;
        return cardState;
    }

    public void addoffset(CardState cardStateoffset, float rate) {
        mAlphaState += cardStateoffset.mAlphaState * rate;
        mPivotX += cardStateoffset.mPivotX * rate;
        mPivotY += cardStateoffset.mPivotY * rate;
        mTranslationX += cardStateoffset.mTranslationX * rate;
        mTranslationY += cardStateoffset.mTranslationY * rate;
        mRotation += cardStateoffset.mRotation * rate;
        mRotationX += cardStateoffset.mRotationX * rate;
        mRotationY += cardStateoffset.mRotationY * rate;
        mScaleX += cardStateoffset.mScaleX * rate;
        mScaleY += cardStateoffset.mScaleY * rate;
        mScrollX += cardStateoffset.mScrollX * rate;
        mScrollY += cardStateoffset.mScrollY * rate;
        mx += cardStateoffset.mx * rate;
        my += cardStateoffset.my * rate;
    }

    @Override
    public String toString() {
        return "CardState [mAlphaState=" + mAlphaState + ", mPivotX=" + mPivotX
                + ", mPivotY=" + mPivotY + ", mTranslationX=" + mTranslationX
                + ", mTranslationY=" + mTranslationY + ", mRotation="
                + mRotation + ", mRotationX=" + mRotationX + ", mRotationY="
                + mRotationY + ", mScaleX=" + mScaleX + ", mScaleY=" + mScaleY
                + ", mScrollX=" + mScrollX + ", mScrollY=" + mScrollY + ", mx="
                + mx + ", my=" + my + "]";
    }

    public Collection<? extends Animator> getAnimation(View view) {
        Collection<Animator> items = new ArrayList<Animator>();
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX",
                mTranslationX);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY",
                mTranslationY);
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat(
                "rotation", mRotation);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha",
                mAlphaState);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX",
                mScaleX);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY",
                mScaleY);
        items.add(ObjectAnimator.ofPropertyValuesHolder(view, x, y, rotation,
                alpha, scaleX, scaleY));
        return items;
    }

    static void animateFormTo(View view, CardState org, CardState target, float rate) {
        float min = Math.min(rate, 1);

        float rotated = (target.mRotation - org.mRotation);
        float rotate = org.mRotation + rotated * min;
        ViewHelper.setRotation(view, rotate);

        float alphad = (target.mAlphaState - org.mAlphaState);
        float alpha = org.mAlphaState + alphad * min;
        ViewHelper.setAlpha(view, alpha);

        float xd = (target.mTranslationX - org.mTranslationX);
        float tx = org.mTranslationX + xd * min;
        ViewHelper.setTranslationX(view, tx);

        float yd = (target.mTranslationY - org.mTranslationY);
        float ty = org.mTranslationY + yd * min;
        ViewHelper.setTranslationY(view, ty);

        float sxd = (target.mScaleX - org.mScaleX);
        float scalx = org.mScaleX + sxd * min;
        ViewHelper.setScaleX(view, scalx);

        float syd = (target.mScaleY - org.mScaleY);
        float scaly = org.mScaleY + syd * min;
        ViewHelper.setScaleY(view, scaly);
    }


}
