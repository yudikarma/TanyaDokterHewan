package com.example.jon_snow.tanyadokterhewan.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.R;

/**
 * Created by jon_snow on 4/14/2018.
 */

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;

    }

    //Array image
    public int[] slideImages= {
            R.drawable.chat_126,
            R.drawable.hospital_icon_126,
            R.drawable.rekammedis_126
    };

    //Array Heading
    public String[] slideHeading = {
            "Konsultasi",
            "Daftar Secara Online",
            "Rekam Medis Digital"
                };
     public String[] slideDesc = {
             "ini adalah description singkat yang sama sekali gak ada manfaatnya. sumpah bukan gua yang buat,ini adalah description singkat yang sama sekali gak ada manfaatnya. sumpah bukan gua yang buat",
             "ini adalah description singkat yang sama sekali gak ada manfaatnya. sumpah bukan gua yang buat,ini adalah description singkat yang sama sekali gak ada manfaatnya. sumpah bukan gua yang buat",
             "ini adalah description singkat yang sama sekali gak ada manfaatnya. sumpah bukan gua yang buat,ini adalah description singkat yang sama sekali gak ada manfaatnya. sumpah bukan gua yang buat"
     };


    @Override
    public int getCount() {
        return slideHeading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container, false);

        ImageView slideImageview = (ImageView) view.findViewById(R.id.slideImage);
        TextView slideTextviewHeading = (TextView) view.findViewById(R.id.slideHeading);
        TextView slideTextviewDesc = (TextView) view.findViewById(R.id.slideDesc);

        slideImageview.setImageResource(slideImages[position]);
        slideTextviewHeading.setText(slideHeading[position]);
        slideTextviewDesc.setText(slideDesc[position]);

        container.addView(view);
        return view;
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);
    }
}
