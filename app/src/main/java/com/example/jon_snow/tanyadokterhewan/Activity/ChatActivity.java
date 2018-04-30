package com.example.jon_snow.tanyadokterhewan.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jon_snow.tanyadokterhewan.Adapter.SectionPageAdapter;
import com.example.jon_snow.tanyadokterhewan.R;

public class ChatActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private ViewPager mviewpager;
    private SectionPageAdapter msectionpageadapter;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mtoolbar = (Toolbar) findViewById(R.id.chat_app_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Chat Dokter");

        //Tabs
        mviewpager = (ViewPager) findViewById(R.id.chat_tab_pager);
        msectionpageadapter = new SectionPageAdapter(getSupportFragmentManager());
        mviewpager.setAdapter(msectionpageadapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mviewpager);




    }
}
