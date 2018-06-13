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

import android.support.v4.view.ViewPager;

/**
 * 选中非选中监听
 */
public abstract class FPagerSelectChangeListener extends FPagerDataSetObserver
{
    private int mLastSelected;

    @Override
    protected void onInit(ViewPager viewPager)
    {
        super.onInit(viewPager);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        super.onRelease(viewPager);
        viewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int i, float v, int i1)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            setSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int i)
        {
        }
    };

    @Override
    protected void onDataSetChanged()
    {
        final ViewPager viewPager = getViewPager();
        if (viewPager != null)
            setSelected(viewPager.getCurrentItem());
    }

    private void setSelected(int index)
    {
        if (isIndexLegal(index))
        {
            notifySelectedChanged(mLastSelected, false);
            notifySelectedChanged(index, true);
            mLastSelected = index;
        }
    }

    private void notifySelectedChanged(int index, boolean selected)
    {
        if (isIndexLegal(index))
            onSelectChanged(index, selected);
    }

    /**
     * 某一页选中或者非选中回调
     *
     * @param index    第几页
     * @param selected true-选中，false-未选中
     */
    protected abstract void onSelectChanged(int index, boolean selected);
}
