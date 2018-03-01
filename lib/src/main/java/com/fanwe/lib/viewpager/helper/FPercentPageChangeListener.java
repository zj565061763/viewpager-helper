package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;
import android.util.SparseArray;

/**
 * Created by zhengjun on 2018/3/1.
 */
public abstract class FPercentPageChangeListener implements ViewPager.OnPageChangeListener
{
    private int mScrollState = ViewPager.SCROLL_STATE_IDLE;
    private float mLastPositionOffsetSum = -1;
    private SparseArray<Float> mArrShowPercent = new SparseArray<>();

    private int mPageCount;

    private int getPageCount()
    {
        return mPageCount;
    }

    public void setPageCount(int pageCount)
    {
        if (mPageCount != pageCount)
        {
            mPageCount = pageCount;
            initShowPercent();
        }
    }

    private void initShowPercent()
    {
        mArrShowPercent.clear();
        final int pageCount = getPageCount();
        if (pageCount <= 0)
        {
            return;
        }
        for (int i = 0; i < pageCount; i++)
        {
            mArrShowPercent.put(i, 0f);
        }
    }

    private void notifyShowPercent(int position, float percent, boolean isEnter, boolean isMoveLeft)
    {
        if (position < 0)
        {
            return;
        }
        onShowPercent(position, percent, isEnter, isMoveLeft);
        mArrShowPercent.put(position, percent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        final float currentPositionOffsetSum = position + positionOffset;
        if (mLastPositionOffsetSum < 0)
        {
            mLastPositionOffsetSum = currentPositionOffsetSum;
        }

        boolean process = true;
        if (mScrollState != ViewPager.SCROLL_STATE_IDLE)
        {
            if (currentPositionOffsetSum == mLastPositionOffsetSum)
            {
                // 已经拖动到边界，继续拖动不处理
                process = false;
            }
        }
        if (process)
        {
            final boolean isMoveLeft = currentPositionOffsetSum >= mLastPositionOffsetSum;

            int leavePosition = 0;
            int enterPosition = 0;
            if (isMoveLeft)
            {
                //手指向左
                leavePosition = position;
                enterPosition = position + 1;

                if (positionOffset == 0)
                {
                    leavePosition--;
                    enterPosition--;
                    positionOffset = 1.0f;
                }
            } else
            {
                //手指向右
                leavePosition = position + 1;
                enterPosition = position;
            }

            if (mScrollState != ViewPager.SCROLL_STATE_IDLE)
            {
                final int pageCount = getPageCount();
                for (int i = 0; i < pageCount; i++)
                {
                    if (i == leavePosition || i == enterPosition)
                    {
                        continue;
                    }
                    Float showPercent = mArrShowPercent.get(i, -1f);
                    if (showPercent != 0f)
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

            mLastPositionOffsetSum = currentPositionOffsetSum;
        }
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
