package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;

/**
 * 页面数量变化监听
 */
public abstract class FPagerCountChangeListener extends FPagerDataSetObserver
{
    private int mCount;

    @Override
    protected void onInit(ViewPager viewPager)
    {
        super.onInit(viewPager);
        notifyPageCountChangedIfNeed();
    }

    @Override
    protected void onChanged()
    {
        notifyPageCountChangedIfNeed();
    }

    private void notifyPageCountChangedIfNeed()
    {
        final int count = getAdapterCount();
        if (mCount != count)
        {
            onPageCountChanged(count);
            mCount = count;
        }
    }

    /**
     * 页数发生改变回调
     *
     * @param count
     */
    protected abstract void onPageCountChanged(int count);
}
