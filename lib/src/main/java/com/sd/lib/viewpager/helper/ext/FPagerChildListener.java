package com.sd.lib.viewpager.helper.ext;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;

import androidx.viewpager.widget.ViewPager;

import com.sd.lib.viewpager.helper.FPagerDataSetObserver;
import com.sd.lib.viewpager.helper.R;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 监听某个view在ViewPager中的选中和非选中状态
 */
public abstract class FPagerChildListener
{
    private final Map<View, String> mMapViewTree = new WeakHashMap<>();
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

    public final ViewPager getViewPager()
    {
        return mDataSetObserver.getViewPager();
    }

    /**
     * 开始搜寻父类，查找ViewPager，如果找到则开始监听选中状态
     *
     * @return
     */
    public final boolean start()
    {
        final View view = mView;
        if (!isAttached(view))
            return false;

        final ViewPager viewPager = findViewPager(view);
        mDataSetObserver.setViewPager(viewPager);
        checkSelected();

        return viewPager != null;
    }

    /**
     * 停止监听
     */
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
                mMapViewTree.clear();
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

        final boolean isSelected = mMapViewTree.containsKey(child);
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

        mMapViewTree.clear();
        mMapViewTree.put(view, "");

        ViewParent current = view.getParent();
        while (current != null && (current instanceof View))
        {
            if (current instanceof ViewPager)
            {
                viewPager = (ViewPager) current;
                break;
            }

            mMapViewTree.put((View) current, "");
            current = current.getParent();
        }

        if (viewPager == null)
            mMapViewTree.clear();

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
