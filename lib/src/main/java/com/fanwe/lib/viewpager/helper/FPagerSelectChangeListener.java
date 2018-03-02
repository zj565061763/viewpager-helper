package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * 选中非选中监听
 */
public abstract class FPagerSelectChangeListener extends FPagerChangeListener implements ViewPager.OnAdapterChangeListener
{
    private int mLastSelected;

    @Override
    protected void onInit(ViewPager viewPager)
    {
        super.onInit(viewPager);
        viewPager.addOnAdapterChangeListener(this);
        updateSelected();
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        super.onRelease(viewPager);
        viewPager.removeOnAdapterChangeListener(this);
    }

    @Override
    public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter)
    {
        updateSelected();
    }

    /**
     * 刷新选中位置，用于ViewPager对象变更，或者Adapter对象变更
     */
    private void updateSelected()
    {
        final ViewPager viewPager = getViewPager();
        if (viewPager != null)
        {
            setSelected(viewPager.getCurrentItem());
        }
    }

    private void setSelected(int index)
    {
        if (isIndexLegal(index))
        {
            notifySelectChanged(mLastSelected, false);
            notifySelectChanged(index, true);
            mLastSelected = index;
        }
    }

    private void notifySelectChanged(int index, boolean selected)
    {
        if (isIndexLegal(index))
        {
            onSelectChanged(index, selected);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }

    @Override
    public void onPageSelected(int position)
    {
        setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }

    protected abstract void onSelectChanged(int index, boolean selected);
}
