package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by ReadBoy on 2015/3/6.
 */
public class SeekIndicator extends SeekBar implements PageIndicator {

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    public SeekIndicator(Context context) {
        super(context);
    }

    public SeekIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setViewPager(ViewPager view) {
        setViewPager(view, 0);
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {

        if (mViewPager == view) {
            return;
        }

        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }

        mViewPager = view;
        mViewPager.setOnPageChangeListener(this);

        this.setMax(mViewPager.getAdapter().getCount() - 1);
        this.setCurrentItem(initialPosition);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mViewPager.setCurrentItem(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setCurrentItem(int item) {
        this.setProgress(item);
        mViewPager.setCurrentItem(item);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        this.setMax(mViewPager.getAdapter().getCount() - 1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }
}
