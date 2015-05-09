package com.llllz.letscdf.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.llllz.letscdf.R;
import com.llllz.letscdf.views.CardFrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import legency.graphic.widgets.ResideMenu;
import legency.graphic.widgets.ResideMenuItem;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    @ViewById
    CardFrameLayout cardcontent;

    @ViewById
    View acbg;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

    @Click
    void acbgClicked() {
        if (resideMenu.isOpened()) {
            resideMenu.closeMenu();
        }
    }

    @Click
    void likeClicked() {
        cardcontent.like(true);
    }

    @Click
    void hateClicked() {
        cardcontent.hated(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.PinkTheme);
    }

    @AfterViews
    void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemProfile = new ResideMenuItem(this, R.drawable.icon_profile,
                "Profile");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar,
                "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
                "Settings");

        resideMenu.addMenuItem(itemHome);
        resideMenu.addMenuItem(itemProfile);
        resideMenu.addMenuItem(itemCalendar);
        resideMenu.addMenuItem(itemSettings);
    }

    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    @Override
    public void onBackPressed() {
        if (resideMenu.isOpened())
            resideMenu.closeMenu();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            resideMenu.openMenu();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.onInterceptTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

}
