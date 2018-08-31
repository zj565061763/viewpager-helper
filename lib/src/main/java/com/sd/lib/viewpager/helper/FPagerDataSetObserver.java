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
package com.sd.lib.viewpager.helper;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;

/**
 * PagerAdapter数据集变化监听
 */
public abstract class FPagerDataSetObserver extends FViewPagerHolder
{
    private final InternalDataSetObserver mDataSetObserver = new InternalDataSetObserver();

    @Override
    protected void onViewPagerChanged(ViewPager oldPager, ViewPager newPager)
    {
        mDataSetObserver.register(null);

        if (oldPager != null)
            oldPager.removeOnAdapterChangeListener(mOnAdapterChangeListener);

        if (newPager != null)
        {
            mDataSetObserver.register(newPager.getAdapter());
            newPager.addOnAdapterChangeListener(mOnAdapterChangeListener);
        }
    }

    private final ViewPager.OnAdapterChangeListener mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener()
    {
        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter)
        {
            mDataSetObserver.register(newAdapter);
        }
    };

    private final class InternalDataSetObserver extends DataSetObserver
    {
        private WeakReference<PagerAdapter> mPagerAdapter;

        private PagerAdapter getPagerAdapter()
        {
            return mPagerAdapter == null ? null : mPagerAdapter.get();
        }

        public final void register(PagerAdapter adapter)
        {
            final PagerAdapter old = getPagerAdapter();
            if (old != adapter)
            {
                if (old != null)
                    old.unregisterDataSetObserver(this);

                mPagerAdapter = adapter == null ? null : new WeakReference<>(adapter);

                if (adapter != null)
                {
                    adapter.registerDataSetObserver(this);
                    // 注册后，立即通知一次
                    this.onChanged();
                }
            }
        }

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
    }

    protected abstract void onDataSetChanged();

    protected void onInvalidated()
    {
    }
}
