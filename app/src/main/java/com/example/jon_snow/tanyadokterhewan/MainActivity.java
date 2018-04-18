package com.example.jon_snow.tanyadokterhewan;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ViewPager slideview;
    private LinearLayout dotslayout;

    private TextView[] mDotslayout;

    private SliderAdapter sliderAdapter;
    private TextView txtFinish;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideview = (ViewPager) findViewById(R.id.slideview);
        dotslayout = (LinearLayout) findViewById(R.id.dotslayout);
        txtFinish = (TextView) findViewById(R.id.btnFinish);
        sliderAdapter = new SliderAdapter(this);
        slideview.setAdapter(sliderAdapter);
        addDotsIndikator(0);

        slideview.addOnPageChangeListener(viewListener);

        txtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,menu_pasien.class);
               startActivity(intent);

            }
        });

    }


    public void addDotsIndikator(int position){
        mDotslayout = new TextView[3];
        dotslayout.removeAllViews();
        for(int i=0;i<mDotslayout.length;i++){
            mDotslayout[i] = new TextView(this);
            mDotslayout[i].setText(Html.fromHtml("&#8226;"));
            mDotslayout[i].setTextSize(35);
            mDotslayout[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            dotslayout.addView(mDotslayout[i]);

        }

        if(mDotslayout.length > 0){
            mDotslayout[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndikator(position);
            mCurrentPage = position;
            if (position == mDotslayout.length -1){
                txtFinish.setEnabled(true);
                txtFinish.setVisibility(View.VISIBLE);
                txtFinish.setText("Finish");
            } else{
                txtFinish.setEnabled(false);
                txtFinish.setVisibility(View.INVISIBLE);
                //btnFinish.setText("Finish");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
