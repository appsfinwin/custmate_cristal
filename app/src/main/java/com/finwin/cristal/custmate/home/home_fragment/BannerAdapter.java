package com.finwin.cristal.custmate.home.home_fragment;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.finwin.cristal.custmate.R;
import com.finwin.cristal.custmate.SupportingClass.ConstantClass;
import com.finwin.cristal.custmate.home.home_fragment.model.BannerData;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    private ArrayList<BannerData> bannerData;
    private LayoutInflater inflater;
    private Context context;

    public BannerAdapter(Context context, ArrayList<BannerData> bannerData) {
        this.bannerData = bannerData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.img_sliding_image);

        //imageView.setImageResource(bannerData.get(position).getImage());
        Glide.with(context).load(ConstantClass.imageUrl+bannerData.get(position).getImage()+".jpeg")
                .placeholder(R.drawable.bg_rectangle_white)
                .into(imageView);

        view.addView(imageLayout, 0);


        return imageLayout;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return bannerData.size();
    }



    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
