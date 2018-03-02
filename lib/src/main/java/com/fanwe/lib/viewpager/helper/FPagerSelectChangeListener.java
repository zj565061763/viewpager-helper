package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * 选中非选中监听
 */
public abstract class FPagerSelectChangeListener extends FPagerChangeListener
{
    private int mLastSelected;

    @Override
    protected void onInit(ViewPager viewPager)
    {
        super.onInit(viewPager);
        viewPager.addOnAdapterChangeListener(mOnAdapterChangeListenerInternal);
        updateSelected();
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        super.onRelease(viewPager);
        viewPager.removeOnAdapterChangeListener(mOnAdapterChangeListenerInternal);
    }

    private ViewPager.OnAdapterChangeListener mOnAdapterChangeListenerInternal = new ViewPager.OnAdapterChangeListener()
    {
        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter)
        {
            updateSelected();
        }
    };

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

    /**
     * 某一页选中或者非选中回调
     *
     * @param index    第几页
     * @param selected true-选中，false-未选中
     */
    protected abstract void onSelectChanged(int index, boolean selected);
}
