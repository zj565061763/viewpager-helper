/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;

/**
 * ViewPager持有类
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
            mViewPager = viewPager == null ? null : new WeakReference<>(viewPager);
            onViewPagerChanged(old, viewPager);
        }
    }

    /**
     * 返回Adapter
     *
     * @return
     */
    public final PagerAdapter getAdapter()
    {
        final ViewPager viewPager = getViewPager();
        return viewPager == null ? null : viewPager.getAdapter();
    }

    /**
     * 返回Adapter的数据量
     *
     * @return
     */
    public final int getAdapterCount()
    {
        final PagerAdapter adapter = getAdapter();
        return adapter == null ? 0 : adapter.getCount();
    }

    /**
     * 位置是否合法
     *
     * @param index
     * @return
     */
    public final boolean isIndexLegal(int index)
    {
        if (index < 0 || index >= getAdapterCount())
        {
            return false;
        } else
        {
            return true;
        }
    }

    protected abstract void onViewPagerChanged(ViewPager oldPager, ViewPager newPager);
}
