package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;

/**
 * Created by zhengjun on 2018/3/2.
 */
public abstract class FViewPagerHolder
{
    private WeakReference<ViewPager> mViewPager;

    public final ViewPager getViewPager()
    {
        return mViewPager == null ? null : mViewPager.get();
    }

    public final void setViewPager(ViewPager viewPager)
    {
        final ViewPager old = getViewPager();
        if (old != viewPager)
        {
            if (old != null)
            {
                onRelease(old);
            }

            if (viewPager != null)
            {
                mViewPager = new WeakReference<>(viewPager);
                onInit(viewPager);
            } else
            {
                mViewPager = null;
            }
        }
    }

    /**
     * 位置是否合法
     *
     * @param index
     * @return
     */
    protected boolean isIndexLegal(int index)
    {
        ViewPager viewPager = getViewPager();
        if (viewPager == null)
        {
            return false;
        }
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null)
        {
            return false;
        }
        if (index < 0 || index >= adapter.getCount())
        {
            return false;
        }
        return true;
    }

    protected abstract void onInit(ViewPager viewPager);

    protected abstract void onRelease(ViewPager viewPager);
}
