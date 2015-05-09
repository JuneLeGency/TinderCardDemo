package com.llllz.letscdf.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.llllz.letscdf.R;
import com.llllz.letscdf.listener.CardListener;
import com.llllz.letscdf.listener.SecondCardBgListener;
import com.llllz.letscdf.model.Recommend;
import com.llllz.letscdf.model.Sex;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import legency.graphic.drawable.TransitionDrawable;

public class CardFrameLayout extends FrameLayout implements CardListener,
        SecondCardBgListener {

    static List<Recommend> recommends = new ArrayList<Recommend>();

    private Handler handler_ = new Handler(Looper.getMainLooper());

    static {
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/1c3aa587b19bcf10265a94a3652f44211da473a9117c1-5gsfIa_fw658",
                        "梁冰洁", 23, Sex.FeMale));

        recommends
                .add(new Recommend(
                        "http://h.hiphotos.baidu.com/image/w%3D2048/sign=ee5d17e0cd11728b302d8b22fcc4c2ce/d833c895d143ad4bee4edeb182025aafa40f065a.jpg",
                        "林峰", 25, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://h.hiphotos.baidu.com/image/w%3D2048/sign=4ee924006509c93d07f209f7ab05f9dc/d50735fae6cd7b8919af548d0d2442a7d8330ef8.jpg",
                        "付辛博", 25, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/c62687ef14a35418efcd2ae1612aa3f43afb6cf822319-xONtrI_fw658",
                        "王媛媛", 25, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://d.hiphotos.baidu.com/image/w%3D2048/sign=bb286929347adab43dd01c43bfecb21c/503d269759ee3d6d16ca5dca41166d224f4adea7.jpg",
                        "钟汉良", 25, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/917cfd578c5a9162e3f7ce86f7c01436e7c449c110da6-5mVW63_fw658",
                        "杜小娟", 22, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/62ee472a1246194453b1305a5ffa01fe4a684fa711754-9JWyy1_fw658",
                        "琳可爱", 26, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=1ec5b79cc9ef76093c0b9e9f1ae5a2cc/2cf5e0fe9925bc314e51b2995cdf8db1cb1370a6.jpg",
                        "李敏镐", 30, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=cb1da37d4dc2d562f208d7edd32990ef/cdbf6c81800a19d8ce1b891831fa828ba61e4696.jpg",
                        "吴彦祖", 25, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/e3eb4d45511cdadf119b544ebfe18c1b00f5da9213e95-swfS2y_fw658",
                        "刘璐", 26, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/63119509d36ca51394777b02c78b69a9573136751a30f-on8cFK_fw658",
                        "方爱华", 26, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/5106a594badfd08e14aa369d5004fff37feea84cb52d8-6ROKtN_fw658",
                        "江心可", 33, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=1e0a7c2135a85edffa8cf9237d6c0823/3ac79f3df8dcd100189aed50738b4710b9122fb8.jpg",
                        "黄晓明", 25, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/6d09a260af6e61ec4ebbe6ef6ad984f1e0272f2c229af-JvZW0q_fw658",
                        "Linda", 18, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=09400ab58e5494ee8722081919cde1fe/241f95cad1c8a7863389c1046509c93d70cf501d.jpg",
                        "路秋水", 26, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=1ec5b79cc9ef76093c0b9e9f1ae5a2cc/2cf5e0fe9925bc314e51b2995cdf8db1cb1370a6.jpg",
                        "李敏镐", 30, Sex.Male));
        /*recommends
				.add(new Recommend(
						"http://d.hiphotos.baidu.com/image/w%3D2048/sign=c4734d1540a7d933bfa8e3739973d013/8718367adab44aede5b3c9e5b11c8701a18bfb36.jpg",
						"张欣", 25, Sex.FeMale));
		recommends
				.add(new Recommend(
						"http://g.hiphotos.baidu.com/image/w%3D2048/sign=5709fdc080025aafd33279cbcfd5ab64/8601a18b87d6277f183589382a381f30e924fc8f.jpg",
						"王小琴", 26, Sex.FeMale));*/
        recommends
                .add(new Recommend(
                        "http://img.hb.aicdn.com/63119509d36ca51394777b02c78b69a9573136751a30f-on8cFK_fw658",
                        "方爱华", 26, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=1c1b01c39252982205333ec3e3f27acb/11385343fbf2b211c92bb84ac88065380cd78ea8.jpg",
                        "林冰凉", 33, Sex.FeMale));
        recommends
                .add(new Recommend(
                        "http://c.hiphotos.baidu.com/image/w%3D2048/sign=1e0a7c2135a85edffa8cf9237d6c0823/3ac79f3df8dcd100189aed50738b4710b9122fb8.jpg",
                        "黄晓明", 25, Sex.Male));
        recommends
                .add(new Recommend(
                        "http://ent.iyaxin.com/attachement/jpg/site2/20110805/0019667873730fa5f68753.jpg",
                        "章子怡", 25, Sex.FeMale));
		/*recommends
				.add(new Recommend(
						"http://a.hiphotos.baidu.com/image/w%3D2048/sign=a5906bede51190ef01fb95dffa239c16/bd3eb13533fa828b86bb26e3ff1f4134970a5a11.jpg",
						"赵林芳", 18, Sex.FeMale));*/
    }

    LinkedList<Card> cards = new LinkedList<Card>();

    /**
     * �����ں���Ĳ���ʾ����Ч���ĸ���
     */
    public static final int noanimationmax = 1;

    private static final int cardscount = 5;

    private int noanimationsize;

    static CardState offset = new CardState();

    static {
        offset.mScaleX = -0.02f;
        offset.mScaleY = -0.02f;
        // offset.my=5;
        offset.mTranslationY = 25;
    }

    private Context mContext;
    private Animation mAnimFadeIn;

    private Card mCurrent;

    private SensorManager mSensorManager;

    private Sensor mAccelerometer;

    private CardState state;

    private AnimatorSet animatorSet;

    private boolean processing;

    private Thread addcard;

    private Drawable currentbg;

    private Drawable secbg;

    public CardFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public CardFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        Log.i("init", "init");
        mContext = context;
        initSensor(context);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mAnimFadeIn = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_quick_fade_in);
        addcard = new Thread(new CardAdd());
        addcard.start();
    }

    class CardAdd implements Runnable {

        @Override
        public void run() {
            while (true) {
                Recommend card = getCard();
                if (card != null) {
                    // TODO add lock
                    if (cards.size() < cardscount)
                        addCard(card);
                } else {

                }
            }
        }

    }

    Recommend getCard() {
        if (recommends != null && recommends.size() > 0)
            return recommends.get(0);
        else
            return null;
    }

    public void addCard(Recommend recommend) {
        //
        Log.i("new Card added", recommend.toString());
        Card card = new Card(mContext);
        card.setGravity(Gravity.CENTER);
        cards.addLast(card);
        int index = cards.indexOf(card);
        if (index == 0) {
            mCurrent = card;
            cardStateChanged();
        }
        if (index == 1) {
            card.setBgChangeListener(this);
        }
        card.bind(recommend);
        AnimatorSet animatorSet = new AnimatorSet();
        Collection<Animator> items = new ArrayList<Animator>();
        if (state == null) {
            state = card.reserveCardState();
            items.addAll(state.getAnimation(card));
        } else {
            Collection<? extends Animator> a = getCarStateofIndex(index)
                    .getAnimation(card);
            items.addAll(a);
        }
        animatorSet.playTogether(items);
        runAnimation(animatorSet, card);
        recommends.remove(recommend);
    }

    private void cardStateChanged() {
        mCurrent.setChoiceListener(this);
    }

    void runAnimation(final AnimatorSet animatorSet, final Card card) {
        handler_.post(new Runnable() {

            @Override
            public void run() {
                addView(card, 0);
                animatorSet.setDuration(300L).start();
            }
        });
    }

    Collection<Animator> animationTonew() {
        Collection<Animator> items = new ArrayList<Animator>();
        for (Card card : cards) {
            int index = cards.indexOf(card);
            Collection<? extends Animator> a = getCarStateofIndex(index)
                    .getAnimation(card);
            items.addAll(a);
        }
        return items;
    }

    CardState getCarStateofIndex(int index) {
        CardState s = state.clone();
        int divindex = cardscount - noanimationsize - 1;
        float rate;
        if (index <= divindex) {
            rate = getrate(index, cardscount);
        } else {
            rate = getrate(divindex, cardscount);
        }
        s.addoffset(offset, rate);
        return s;
    }

    boolean isAnimationStarted() {
        return animatorSet != null && animatorSet.isStarted();
    }

    void initSensor(Context context) {
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener a = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                    return;
                // low-pass filter to make the movement more stable
                if (mCurrent != null && !mCurrent.isTouching()
                        && !mCurrent.isAnimating() && !isAnimationStarted()) {
                    mCurrent.sensorEventChange(event);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        // mSensorManager.registerListener(a, mAccelerometer,
        // SensorManager.SENSOR_DELAY_UI);
    }

    private float getrate(int i, int showsize) {
        if (i == 0)
            return 0;
        return (1 - i / showsize) * (i + 1);
    }

    void removeFirst() {
        Card a = cards.poll();
        if (a != null) {
            removeView(a);
        }
        animationTonew();
    }

    public AnimatorListener getCardRemovedListener(final Card p) {
        return new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(p);
                mCurrent = cards.peek();
                if (mCurrent != null) {
                    mCurrent.reserveCardState();
                    cardStateChanged();
                    if (cards.size() > 1 && cards.get(1) != null) {
                        cards.get(1).setBgChangeListener(CardFrameLayout.this);
                    }
                }
                processing = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        };
    }

    public Animation.AnimationListener getStampAnimationListener(
            final AnimatorSet animatorset) {
        return new Animation.AnimationListener() {
            public void onAnimationEnd(Animation paramAnimation) {
                animatorset.start();
                getBg().startTransition(300L);
            }

            public void onAnimationRepeat(Animation paramAnimation) {
            }

            public void onAnimationStart(Animation paramAnimation) {
            }
        };
    }

    @Override
    public void likeswiped() {
        like(false);
    }

    public void like(boolean withStamp) {
        animateOut(true, withStamp);
    }

    void animateOut(boolean like, boolean withStamp) {
        if (processing)
            return;
        processing = true;
        Card current = cards.poll();// should not release lock
        if (current != null) {
            animatorSet = new AnimatorSet();
            Collection<Animator> animatorsa = animationTonew();
            int i = 800 * (like ? 1 : -1);
            ObjectAnimator toright = ObjectAnimator.ofFloat(current,
                    "translationX", i);
            animatorsa.add(toright);
            animatorSet.addListener(getCardRemovedListener(current));
            animatorSet.setDuration(300L).playTogether(animatorsa);
            if (withStamp) {
                View stamp = (like ? mCurrent.getLike() : mCurrent.getHate());
                stamp.setVisibility(View.VISIBLE);
                ViewHelper.setAlpha(stamp, 0.8f);
                mAnimFadeIn
                        .setAnimationListener(getStampAnimationListener(animatorSet));
                stamp.startAnimation(mAnimFadeIn);

            } else {
                animatorSet.start();
            }
            // TODO release lock
        }
    }

    public void hated(boolean withStamp) {
        animateOut(false, withStamp);
    }

    @Override
    public void hateswiped() {
        hated(false);
    }

    public void show() {

    }

    @Override
    public void onmove(float x) {
        float rate = Math.min(Math.abs(x) / 220.0F, 1.0F);
        for (int i = 1; i < cards.size() - noanimationsize; i++) {
            Card animationcard = cards.get(i);
            if (animationcard == null)
                break;
            animateFormTo(animationcard, i, i - 1, rate);
        }
        if (getBackground() != null
                && getBackground() instanceof TransitionDrawable)
            ((TransitionDrawable) getBackground()).setRate(rate);
    }

    private void animateFormTo(View view, int orgindex, int target, float rate) {
        CardState.animateFormTo(view, getCarStateofIndex(orgindex),
                getCarStateofIndex(target), rate);
    }

    @Override
    public void resetPosition() {
        animatorSet = new AnimatorSet();
        Collection<Animator> items = new ArrayList<Animator>();
        for (int i = 1; i < cards.size() - noanimationsize; i++) {
            Card animationcard = cards.get(i);
            if (animationcard == null)
                break;
            items.addAll(getCarStateofIndex(i).getAnimation(animationcard));
        }
        animatorSet.playTogether(items);
        animatorSet.setDuration(300L).start();
        if (getBg() != null)
            getBg().reverse(300L);
    }

    @Override
    public void ImageLoaded(Drawable drawable) {
        currentbg = drawable;
        buindTrD();
    }

    TransitionDrawable buindTrD() {
        boolean anim = false;
        if (currentbg == null)
            return null;
        Drawable[] layers = new Drawable[2];
        if (secbg != null) {
            layers[0] = currentbg;
            layers[1] = secbg;
        } else {
            layers[0] = getResources().getDrawable(R.drawable.wall_bg);
            layers[1] = currentbg;
            anim = true;
        }
        TransitionDrawable t = new TransitionDrawable(layers);
        setBackground(t);
        t.setCrossFadeEnabled(true);
        if (anim)
            t.startTransition(1500);
        return t;
    }

    @Override
    public void secbgloaded(Drawable drawable) {
        secbg = drawable;
        buindTrD();
    }

    TransitionDrawable getBg() {
        return ((TransitionDrawable) getBackground());
    }

}
