package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by zhengjun on 2018/3/1.
 */
public abstract class FPercentPageChangeListener implements ViewPager.OnPageChangeListener
{
    private static final String TAG = FPercentPageChangeListener.class.getSimpleName();

    private int mScrollState = ViewPager.SCROLL_STATE_IDLE;
    private float mLastOffset = -1;

    private int mLastPosition = -1;

    private void notifyShowPercent(int position, float percent, boolean isEnter, boolean isMoveLeft)
    {
        if (position < 0)
        {
            return;
        }
        onShowPercent(position, percent, isEnter, isMoveLeft);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        Log.i(TAG, position + " " + positionOffset);
        final float currentOffset = position + positionOffset;
        if (mLastOffset < 0)
        {
            mLastOffset = currentOffset;
        }

        if (mScrollState != ViewPager.SCROLL_STATE_IDLE)
        {
            if (currentOffset == mLastOffset)
            {
                // 已经拖动到边界，继续拖动不处理
                return;
            }
        }

        final boolean isMoveLeft = currentOffset >= mLastOffset; //内容是否向左滑动

        int leavePosition = 0;
        int enterPosition = 0;
        if (isMoveLeft)
        {
            if (positionOffset == 0)
            {
                //刚好滑动到新的一页
                leavePosition = position - 1;
                enterPosition = position;
                positionOffset = 1.0f;
            } else
            {
                leavePosition = position;
                enterPosition = position + 1;
            }
        } else
        {
            leavePosition = position + 1;
            enterPosition = position;
        }

        if (mScrollState != ViewPager.SCROLL_STATE_IDLE)
        {
            final int diff = position - mLastPosition;
            if (Math.abs(diff) > 1)
            {
                for (int i = mLastPosition + 1; i < position; i++)
                {
                    notifyShowPercent(i, 0, false, isMoveLeft);
                }
            }
        }

        if (isMoveLeft)
        {
            notifyShowPercent(leavePosition, 1 - positionOffset, false, isMoveLeft);
            notifyShowPercent(enterPosition, positionOffset, true, isMoveLeft);
        } else
        {
            notifyShowPercent(leavePosition, positionOffset, false, isMoveLeft);
            notifyShowPercent(enterPosition, 1 - positionOffset, true, isMoveLeft);
        }

        mLastOffset = currentOffset;
        mLastPosition = position;
    }

    @Override
    public void onPageSelected(int position)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
        mScrollState = state;
    }

    /**
     * ViewPager页面显示的百分比回调
     *
     * @param position    第几页
     * @param showPercent 显示的百分比[0-1]
     * @param isEnter     true-当前页面处于进入状态，false-当前页面处于离开状态
     * @param isMoveLeft  true-ViewPager内容向左移动，false-ViewPager内容向右移动
     */
    public abstract void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft);
}
