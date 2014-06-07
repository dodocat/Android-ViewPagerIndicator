package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

/**
 * todo use smooth seekBar.
 * Created by cindy on 5/15/14.
 */
public class ProgressIndicator extends SeekBar implements PageIndicator {

    public static final boolean DEBUG = true;

    public static final String TAG = "ProgressIndicator";
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener listener;
    private float pageOffset;
    private int scrollState;
    private boolean centered;
    private boolean snap;
    private int touchSlop;
    private float lastMotionX;
    private boolean isDragging;

    public ProgressIndicator(Context context) {
        super(context);
    }

    public ProgressIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setViewPager(ViewPager view) {
        this.setViewPager(view, 0);
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        if (DEBUG) Log.i(TAG,
                String.format("setViewPager with children %d initial position %d",
                        view.getAdapter().getCount(), initialPosition)
        );
        if ((viewPager == view)) {
            return;
        }
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(null);
        }
        this.viewPager = view;
        this.viewPager.setOnPageChangeListener(this);

        this.setMax(viewPager.getAdapter().getCount() - 1);
        this.setCurrentItem(initialPosition);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)viewPager.setCurrentItem(progress);
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
        if (DEBUG)
            Log.i(TAG, String.format("setCurrentItem %d", item));

        this.setProgress(item);
        viewPager.setCurrentItem(item);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        if (DEBUG)
            Log.i(TAG, String.format("notifyDataSetChanged count %d currentItem %d",
                    viewPager.getAdapter().getCount(), viewPager.getCurrentItem()));

        this.setMax(viewPager.getAdapter().getCount() -1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.setProgress(position);
        if (DEBUG)
            Log.i(TAG, String.format("onPageScrolled position %d positionOffset %.2f, " +
                    "positionOffsetPixedls %d", position, positionOffset, positionOffsetPixels));

        if (listener != null) {
            listener.onPageSelected(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (DEBUG) {
            Log.i(TAG, String.format("onPageSelected %d", position));
        }

        this.setProgress(position);

        if (listener != null) {
            listener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (DEBUG) Log.d(TAG, String.format("onPageScrollStateChanged %d", state));
    }
}
