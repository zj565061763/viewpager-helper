package com.fanwe.lib.viewpager.helper;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * PagerAdapter数据集变化监听
 */
public abstract class FPagerDataSetObserver extends FViewPagerHolder
{
    @Override
    protected void onInit(ViewPager viewPager)
    {
        viewPager.addOnAdapterChangeListener(mOnAdapterChangeListener);

        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null)
        {
            adapter.registerDataSetObserver(mDataSetObserver);
            mDataSetObserver.onChanged(); // 手动通知一次
        }
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnAdapterChangeListener(mOnAdapterChangeListener);

        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null)
        {
            adapter.unregisterDataSetObserver(mDataSetObserver);
        }
    }

    private ViewPager.OnAdapterChangeListener mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener()
    {
        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter)
        {
            if (oldAdapter != null)
            {
                oldAdapter.unregisterDataSetObserver(mDataSetObserver);
            }
            if (newAdapter != null)
            {
                newAdapter.registerDataSetObserver(mDataSetObserver);
            }

            mDataSetObserver.onChanged(); // 手动通知一次
        }
    };

    private DataSetObserver mDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            FPagerDataSetObserver.this.onDataSetChanged();
        }

        @Override
        public void onInvalidated()
        {
            FPagerDataSetObserver.this.onInvalidated();
        }
    };

    protected abstract void onDataSetChanged();

    protected void onInvalidated()
    {
    }
}
