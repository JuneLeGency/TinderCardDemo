package com.llllz.letscdf.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEvent;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imagefilter.GaussianBlurFilter;
import com.imagefilter.Image;
import com.imagefilter.ThreeDGridFilter;
import com.llllz.letscdf.R;
import com.llllz.letscdf.listener.CardListener;
import com.llllz.letscdf.listener.SecondCardBgListener;
import com.llllz.letscdf.model.Recommend;
import com.llllz.letscdf.model.Sex;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;

import legency.graphic.drawable.TransitionDrawable;

public class Card extends RelativeLayout {

    private static final boolean SENSORENABLE = true;

    public CardState cardState;

    private ImageView avatar_im;

    private ImageView like;

    private ImageView hate;

    private Rect mBounds;
    private float mDeltaX;
    private float mPointerX;
    private VelocityTracker mVelocityTracker;
    private boolean mTapped;

    private float mVelocity;
    private float mWidthDismissTrigger;
    private float mCardWidth;
    private CardListener choiceListener;
    private SecondCardBgListener bgChangeListener;
    private int mTapSlop;
    private float mPointerY;
    private float mDeltaY;
    private AnimatorSet animatorSet;

    private TextView name;

    private TextView age;

    private int card_malecard;

    private int card_femalecard;

    private View card;

    private boolean touching;

    private float accsensorx;

    private float accsensory;

    private Recommend recommend;

    protected BitmapDrawable bgd;


    public boolean isAnimating() {
        return animatorSet != null && animatorSet.isStarted();
    }

    public boolean isTouching() {
        return touching;
    }

    public Card(Context context) {
        this(context, null);
    }

    public Card(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.femalecard);
    }

    static public Card inflateMale(Context context) {
        return new Card(context, null, R.attr.malecard);
    }

    void setCardStyleRes(Context context, int cardstyleres) {
        TypedArray cardstyle = context.getTheme().obtainStyledAttributes(
                cardstyleres, R.styleable.cardstyle);

        int card_name_color = cardstyle.getColor(
                R.styleable.cardstyle_name_color, Color.WHITE);

        int card_age_color = cardstyle.getColor(
                R.styleable.cardstyle_age_color, Color.WHITE);

        int resid = cardstyle.getResourceId(R.styleable.cardstyle_bg,
                R.drawable.card_shadow);
        card.setBackgroundResource(resid);
        name.setTextColor(card_name_color);
        age.setTextColor(card_age_color);
        cardstyle.recycle();
    }

    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray cardTypedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.card, defStyle, R.style.PinkTheme_Card);
        int sex = cardTypedArray.getInt(R.styleable.card_sex, 0);
        card_malecard = cardTypedArray.getResourceId(R.styleable.card_malecard,
                R.style.PinkTheme_Card_Male);
        card_femalecard = cardTypedArray.getResourceId(
                R.styleable.card_femalecard, R.style.PinkTheme_Card_Female);
        int layoutResourceId = cardTypedArray.getResourceId(
                R.styleable.card_layoutid, R.layout.card);
        cardTypedArray.recycle();

        Drawable avatar = cardTypedArray.getDrawable(R.styleable.card_avatar);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        setAnimationCacheEnabled(true);

        // LayoutInflater inflater = (LayoutInflater) context
        // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflater.inflate(layoutResourceId, this, true);
        inflate(context, layoutResourceId, this);

        avatar_im = (ImageView) findViewById(R.id.avatar);
        card = findViewById(R.id.card);
        like = (ImageView) findViewById(R.id.like);
        hate = (ImageView) findViewById(R.id.hate);
        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.age);
        setAvatar(avatar);
        ViewHelper.setAlpha(like, 0.0f);
        ViewHelper.setAlpha(hate, 0.0f);
        this.mCardWidth = getResources().getDimension(R.dimen.card_width);
        mWidthDismissTrigger = (mCardWidth / 2.0f);
        mTapSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        setCardStyle(sex == 0, context);
    }

    void setCardStyle(boolean ismale, Context context) {
        if (ismale)
            setCardStyleRes(context, card_malecard);
        else
            setCardStyleRes(context, card_femalecard);
    }

    void setCardStyle(boolean ismale) {
        if (ismale)
            setCardStyleRes(getContext(), card_malecard);
        else
            setCardStyleRes(getContext(), card_femalecard);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (choiceListener == null)
            return false;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("down", "down");
                requestDisallowInterceptTouchEvent(true);
                this.mPointerX = event.getRawX();
                this.mPointerY = event.getRawY();
                mVelocityTracker = VelocityTracker.obtain();
                mTapped = true;
                touching = true;
                return true;
            case MotionEvent.ACTION_UP:
                Log.i("up", "up");
                mVelocityTracker.computeCurrentVelocity(1);
                mVelocity = mVelocityTracker.getXVelocity();
                mDeltaX = (event.getRawX() - mPointerX);
                mDeltaY = (event.getRawY() - mPointerY);

                if (Math.abs(this.mVelocity) >= 2.0F) {
                    if (this.mDeltaX < cardState.mTranslationX) {
                        if ((this.animatorSet != null)
                                && (this.animatorSet.isStarted()))
                            this.animatorSet.cancel();
                        hateCard();
                        return false;
                    }
                    if (this.mDeltaX > cardState.mTranslationX) {
                        if ((this.animatorSet != null)
                                && (this.animatorSet.isStarted()))
                            this.animatorSet.cancel();
                        likeCard();
                        return false;
                    }
                }
                if (mDeltaX >= mWidthDismissTrigger) {
                    likeCard();
                    return false;
                }
                if (mDeltaX <= -mWidthDismissTrigger) {
                    hateCard();
                    return false;
                }
                resetPosition();
                touching = false;
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.i("move", "move");
                mVelocityTracker.addMovement(event);
                mDeltaX = (event.getRawX() - mPointerX);
                mDeltaY = (event.getRawY() - mPointerY);
                mBounds = new Rect();
                getDrawingRect(mBounds);
                mBounds.offset((int) mDeltaX, (int) mDeltaY);
                ViewHelper.setTranslationX(this, this.mDeltaX);
                ViewHelper.setTranslationY(this, this.mDeltaY);
                float a = mDeltaX / 30;
                // ViewHelper.setRotationX(this, a);
                ViewHelper.setRotation(this, a);
                invalidate(mBounds);
                if (this.mDeltaX < cardState.mTranslationX) {
                    like.setVisibility(View.INVISIBLE);
                    hate.setVisibility(View.VISIBLE);
                    float f2 = Math.min(Math.abs(this.mDeltaX) / 220.0F, 1.0F);
                    ViewHelper.setAlpha(hate, f2);
                } else {
                    hate.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
                    float f2 = Math.min((Math.abs(mDeltaX) / 220.0f), 1.0f);
                    ViewHelper.setAlpha(like, f2);
                }

                float cardalpha = Math.max(
                        Math.min(220.0F / Math.abs(this.mDeltaX), 1.0F), 0.5F);
                ViewHelper.setAlpha(this, cardalpha);
                boolean localconst = Math.abs(mDeltaX) <= mTapSlop;
                mTapped = localconst;
                choiceListener.onmove(mDeltaX);
                return true;
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                break;
            default:
                break;
        }
        return false;
    }

    private void likeCard() {
        choiceListener.likeswiped();
    }

    private void hateCard() {
        choiceListener.hateswiped();
    }

    private void resetPosition() {
        Log.i("reset", "position");
        ViewHelper.setAlpha(hate, 0.0F);
        ViewHelper.setAlpha(like, 0.0F);
        hate.setVisibility(View.INVISIBLE);
        like.setVisibility(View.INVISIBLE);
        animatorSet = new AnimatorSet();
        Log.i("cardstate", getCardState().toString());
        reset(SENSORENABLE);
        choiceListener.resetPosition();
        Log.i("v", this.getVisibility() + "View.VISIBLE" + View.VISIBLE);
        Log.i("cardstate", getCardState().toString());
    }

    private void reset(boolean sensorenable) {
        //
        if (sensorenable) {
            animatorSet.playTogether(ObjectAnimator.ofFloat(this,
                    "translationX", accsensorx), ObjectAnimator.ofFloat(this,
                    "translationY", accsensory), ObjectAnimator.ofFloat(this,
                    "rotation", cardState.mRotation), ObjectAnimator.ofFloat(
                    this, "alpha", cardState.mAlphaState));
            animatorSet.setDuration(300L).start();
        } else {
            reset();
        }
    }

    public void setAvatar(Drawable avatar) {
        if (avatar != null)
            avatar_im.setImageDrawable(avatar);
    }

    public ImageView getLike() {
        return like;
    }

    public void setLike(ImageView like) {
        this.like = like;
    }

    public ImageView getHate() {
        return hate;
    }

    public void setHate(ImageView hate) {
        this.hate = hate;
    }

    public void setChoiceListener(final CardListener choiceListener) {
        this.choiceListener = choiceListener;
        if (bgd != null) {
            choiceListener.ImageLoaded(bgd);
        }
        // if (recommend != null) {
        // Target arg0 = new Target() {
        //
        // @Override
        // public void onPrepareLoad(Drawable arg0) {
        // }
        //
        // @Override
        // public void onBitmapLoaded(Bitmap arg0, LoadedFrom arg1) {
        // FadeDrawable bitmapbg = new FadeDrawable(getResources(),
        // arg0);
        // choiceListener.ImageLoaded(bitmapbg);
        // }
        //
        // @Override
        // public void onBitmapFailed(Drawable arg0) {
        //
        // }
        // };
        // Picasso.with(getContext()).load(recommend.getAvatorurl())
        // .transform(new Transformation() {
        //
        // @Override
        // public Bitmap transform(Bitmap arg0) {
        //
        // GaussianBlurFilter filter = new GaussianBlurFilter();
        // filter.Sigma = 10.0F;
        // return Image.doWithImage(arg0).addFilter(filter)
        // .ok();
        // }
        //
        // @Override
        // public String key() {
        // return "GaussianBlurFilter";
        // }
        // }).into(arg0);
        // }
    }

    public CardState reserveCardState() {
        return this.cardState = getCardState();
    }

    private CardState getCardState() {
        CardState s = new CardState();
        s.mAlphaState = ViewHelper.getAlpha(this);
        s.mPivotX = ViewHelper.getPivotX(this);
        s.mPivotY = ViewHelper.getPivotY(this);
        s.mTranslationX = ViewHelper.getTranslationX(this);
        s.mTranslationY = ViewHelper.getTranslationY(this);
        s.mRotation = ViewHelper.getRotation(this);
        s.mRotationX = ViewHelper.getRotationX(this);
        s.mRotationY = ViewHelper.getRotationY(this);
        s.mScaleX = ViewHelper.getScaleX(this);
        s.mScaleY = ViewHelper.getScaleY(this);
        s.mScrollX = ViewHelper.getScrollX(this);
        s.mScrollY = ViewHelper.getScrollY(this);
        return s;
    }

    void reset() {
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(this, "translationX",
                        Math.abs(cardState.mTranslationX)),
                ObjectAnimator.ofFloat(this, "translationY",
                        Math.abs(cardState.mTranslationY)),
                ObjectAnimator.ofFloat(this, "rotation", cardState.mRotation),
                ObjectAnimator.ofFloat(this, "alpha", cardState.mAlphaState));
        animatorSet.setDuration(300L).start();
    }

    public void animateToCard(Card target, float x) {
        float doffset = Math.abs(x) / 220.0F;

        float rotated = (target.cardState.mRotation - cardState.mRotation);
        float rotate = cardState.mRotation + rotated * Math.min(doffset, 1);
        ViewHelper.setRotation(this, rotate);

        float alphad = (target.cardState.mAlphaState - cardState.mAlphaState);
        float alpha = cardState.mAlphaState + alphad * Math.min(doffset, 1);
        ViewHelper.setAlpha(this, alpha);

        float xd = (target.cardState.mTranslationX - cardState.mTranslationX);
        float tx = cardState.mTranslationX + xd * Math.min(doffset, 1);
        ViewHelper.setTranslationX(this, tx);

        float yd = (target.cardState.mTranslationY - cardState.mTranslationY);
        float ty = cardState.mTranslationY + yd * Math.min(doffset, 1);
        ViewHelper.setTranslationY(this, ty);

        float sxd = (target.cardState.mScaleX - cardState.mScaleX);
        float scalx = cardState.mScaleX + sxd * Math.min(doffset, 1);
        ViewHelper.setScaleX(this, scalx);

        float syd = (target.cardState.mScaleY - cardState.mScaleY);
        float scaly = cardState.mScaleY + syd * Math.min(doffset, 1);
        ViewHelper.setScaleY(this, scaly);
    }

    public void setCardState(CardState state) {
        ViewHelper.setAlpha(this, state.mAlphaState);
        // ViewHelper.setPivotX(this, this.getWidth() / 2);
        // ViewHelper.setPivotY(this, this.getHeight() / 2);
        ViewHelper.setTranslationX(this, state.mTranslationX);
        ViewHelper.setTranslationY(this, state.mTranslationY);
        ViewHelper.setRotation(this, state.mRotation);
        ViewHelper.setRotationX(this, state.mRotationX);
        ViewHelper.setRotationY(this, state.mRotationY);
        ViewHelper.setScaleX(this, state.mScaleX);
        ViewHelper.setScaleY(this, state.mScaleY);
        ViewHelper.setScrollX(this, (int) state.mScrollX);
        ViewHelper.setScrollY(this, (int) state.mScrollY);
        this.cardState = state;
    }

    public Recommend getRecommend() {
        return recommend;
    }

    public void bind(final Recommend recommend) {
        this.recommend = recommend;
        int placeholderResId = R.drawable.no_avatar_femle;

        if (recommend.getSex() == Sex.Male) {
            placeholderResId = R.drawable.no_avatar_male;
            setCardStyle(true);
        } else {
            setCardStyle(false);
        }

        Callback ca = new Callback() {

            @Override
            public void onSuccess() {
                new BgTask2(recommend).execute();
            }

            @Override
            public void onError() {

            }
        };
        Picasso.with(getContext()).load(recommend.getAvatorurl())
                .resize(400, 800).placeholder(placeholderResId)
                .transform(new Transformation() {

                    @Override
                    public Bitmap transform(Bitmap arg0) {
                        return Image.doWithImage(arg0)
                                .addFilter(new ThreeDGridFilter(10, 10))
                                .finish(true);
                    }

                    @Override
                    public String key() {
                        return "as";
                    }
                }).centerCrop().into(avatar_im, ca);


        name.setText(recommend.getName());
        age.setText(recommend.getAgeString());
    }

    public void sensorEventChange(SensorEvent event) {

        // ������ ���� �ƶ�

        // final float FILTERING_FACTOR = 0.7f;
        // float x = ViewHelper.getTranslationX(this);
        // float y = ViewHelper.getTranslationY(this);
        // accsensorx = (float) (-event.values[0] * FILTERING_FACTOR * 10 + x
        // * (1.0 - FILTERING_FACTOR));
        // accsensory = (float) (event.values[1] * FILTERING_FACTOR * 10 + y
        // * (1.0 - FILTERING_FACTOR));
        // ViewHelper.setTranslationX(this, accsensorx);
        // ViewHelper.setTranslationY(this, accsensory);

        // ����
        // float scalx = ViewHelper.getScaleX(this);
        // float scalxn = (float) (event.values[2] * 0.05 + scalx
        // * (1.0 - 0.05));
        // ViewHelper.setScaleX(this, scalxn);
        //
        // float scaly = ViewHelper.getScaleY(this);
        // float scalyn = (float) (event.values[2] * 0.05 + scaly
        // * (1.0 - 0.05));
        // ViewHelper.setScaleY(this, scalyn);
    }


    public class BgTask2 extends AsyncTask<Void, Void, BitmapDrawable> {

        private Recommend recommend;

        public BgTask2(Recommend recommend) {
            this.recommend = recommend;
        }

        @Override
        protected BitmapDrawable doInBackground(Void... params) {
            Bitmap as = null;
            try {
                as = Picasso.with(getContext()).load(recommend.getAvatorurl()).resize(100, 100)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GaussianBlurFilter filter = new GaussianBlurFilter();
            filter.Sigma = 5.0F;
            Bitmap a = Image.doWithImage(as).addFilter(filter)
                    .finish(false);
            return new BitmapDrawable(getResources(), a);
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            super.onPostExecute(result);
            bgd = result;
            if (bgChangeListener != null)
                bgChangeListener.secbgloaded(bgd);
            if (choiceListener != null)
                choiceListener.ImageLoaded(bgd);
        }

    }


    public TransitionDrawable buildbg(Drawable currentbg) {
        Drawable[] layers = new Drawable[2];
        layers[0] = currentbg;
        layers[1] = bgd;
        return new TransitionDrawable(layers);
    }

    public SecondCardBgListener getBgChangeListener() {
        return bgChangeListener;
    }

    public void setBgChangeListener(SecondCardBgListener bgChangeListener) {
        this.bgChangeListener = bgChangeListener;
        if (bgd != null) {
            bgChangeListener.secbgloaded(bgd);
        }
    }

}
