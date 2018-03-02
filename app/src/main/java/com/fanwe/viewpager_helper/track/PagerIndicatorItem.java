package com.fanwe.viewpager_helper.track;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PagerIndicatorItem extends FrameLayout
{
    public PagerIndicatorItem(Context context)
    {
        super(context);
        init();
    }

    public PagerIndicatorItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PagerIndicatorItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TextView mTextView;

    private void init()
    {
        mTextView = new TextView(getContext());
        mTextView.setGravity(Gravity.CENTER);
        addView(mTextView);

        onSelectChanged(false);
    }

    public TextView getTextView()
    {
        return mTextView;
    }

    public void onSelectChanged(boolean selected)
    {
        if (selected)
        {
            mTextView.setTextColor(Color.RED);
        } else
        {
            mTextView.setTextColor(Color.BLACK);
        }
    }

    private PositionData mPositionData;

    public PositionData getPositionData()
    {
        if (mPositionData == null)
        {
            mPositionData = new PositionData();
        }
        return mPositionData;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        getPositionData().left = getLeft();
        getPositionData().top = getTop();
        getPositionData().right = getRight();
        getPositionData().bottom = getBottom();
    }
}
