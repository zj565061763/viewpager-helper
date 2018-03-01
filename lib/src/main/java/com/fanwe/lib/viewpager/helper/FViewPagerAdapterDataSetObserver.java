package com.fanwe.lib.viewpager.helper;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;

import java.lang.ref.WeakReference;

/**
 * Created by zhengjun on 2018/3/1.
 */
public abstract class FViewPagerAdapterDataSetObserver extends DataSetObserver
{
    private WeakReference<PagerAdapter> mAdapter;

    private PagerAdapter getAdapter()
    {
        return mAdapter == null ? null : mAdapter.get();
    }

    /**
     * 把当前对象注册到Adapter
     *
     * @param adapter
     */
    public void register(PagerAdapter adapter)
    {
        final PagerAdapter old = getAdapter();
        if (old != adapter)
        {
            if (old != null)
            {
                old.unregisterDataSetObserver(this);
            }

            mAdapter = (adapter == null ? null : new WeakReference<>(adapter));

            if (adapter != null)
            {
                adapter.registerDataSetObserver(this);
            }
        }
    }

    /**
     * 取消当前对象注册到Adapter
     */
    public void unregister()
    {
        final PagerAdapter old = getAdapter();
        if (old != null)
        {
            old.unregisterDataSetObserver(this);
            mAdapter = null;
        }
    }
}
