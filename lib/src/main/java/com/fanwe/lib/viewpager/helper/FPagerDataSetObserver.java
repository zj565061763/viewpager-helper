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

        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null)
        {
            adapter.registerDataSetObserver(mDataSetObserver);
            // 手动通知一次
            mDataSetObserver.onChanged();
        }
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnAdapterChangeListener(mOnAdapterChangeListener);

        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null)
            adapter.unregisterDataSetObserver(mDataSetObserver);
    }

    private final ViewPager.OnAdapterChangeListener mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener()
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

    private final DataSetObserver mDataSetObserver = new DataSetObserver()
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
