package com.sd.lib.viewpager.helper.ext;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;

import androidx.viewpager.widget.ViewPager;

import com.sd.lib.viewpager.helper.FPagerDataSetObserver;
import com.sd.lib.viewpager.helper.R;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class FPagerChildListener
{
    private final Map<View, String> mMapParent = new WeakHashMap<>();
    private final View mView;

    private boolean mSelected;

    public FPagerChildListener(View view)
    {
        if (view == null)
            throw new NullPointerException("view is null");
        mView = view;
    }

    public boolean isSelected()
    {
        return mSelected;
    }

    public final View getView()
    {
        return mView;
    }

    public final ViewPager getViewPager()
    {
        return mDataSetObserver.getViewPager();
    }

    public final boolean start()
    {
        final View view = getView();
        if (!isAttached(view))
            return false;

        final ViewPager viewPager = findViewPager(view);
        mDataSetObserver.setViewPager(viewPager);
        checkSelected();

        return viewPager != null;
    }

    public final void stop()
    {
        mDataSetObserver.setViewPager(null);
    }

    private final FPagerDataSetObserver mDataSetObserver = new FPagerDataSetObserver()
    {
        @Override
        protected void onViewPagerChanged(ViewPager oldPager, ViewPager newPager)
        {
            super.onViewPagerChanged(oldPager, newPager);

            if (oldPager != null)
                oldPager.removeOnPageChangeListener(mOnPageChangeListener);

            if (newPager != null)
            {
                newPager.addOnPageChangeListener(mOnPageChangeListener);
            } else
            {
                mMapParent.clear();
                setSelected(false);
            }
        }

        @Override
        protected void onDataSetChanged()
        {
            checkSelected();
        }
    };

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            checkSelected();
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };

    private boolean checkSelected()
    {
        final View child = getSelectedChild();
        if (child == null)
            return false;

        final boolean isSelected = mMapParent.containsKey(child);
        setSelected(isSelected);
        return isSelected;
    }

    private View getSelectedChild()
    {
        final ViewPager viewPager = getViewPager();
        if (viewPager == null)
            return null;

        final int count = viewPager.getChildCount();
        if (count <= 0)
            return null;

        final int currentItem = viewPager.getCurrentItem();
        for (int i = 0; i < count; i++)
        {
            final View child = viewPager.getChildAt(i);
            final Object position = child.getTag(R.id.view_pager_child_position);
            if (position == null)
                throw new RuntimeException("tag 'R.id.view_pager_child_position' was not specified for child:" + child);

            final int intValue = Integer.valueOf(String.valueOf(position));
            if (intValue == currentItem)
                return child;
        }
        return null;
    }

    private ViewPager findViewPager(View view)
    {
        ViewPager viewPager = null;

        mMapParent.clear();
        mMapParent.put(view, "");

        ViewParent parent = view.getParent();
        while (true)
        {
            if (parent == null)
                break;

            if (!(parent instanceof View))
                break;

            if (parent instanceof ViewPager)
            {
                viewPager = (ViewPager) parent;
                break;
            }

            mMapParent.put((View) parent, "");
            parent = parent.getParent();
        }

        if (viewPager == null)
            mMapParent.clear();

        return viewPager;
    }

    private void setSelected(boolean selected)
    {
        if (mSelected != selected)
        {
            mSelected = selected;
            onPageSelectChanged(selected);
        }
    }

    protected abstract void onPageSelectChanged(boolean selected);

    private static boolean isAttached(View view)
    {
        if (view == null)
            return false;

        if (Build.VERSION.SDK_INT >= 19)
            return view.isAttachedToWindow();
        else
            return view.getWindowToken() != null;
    }
}
