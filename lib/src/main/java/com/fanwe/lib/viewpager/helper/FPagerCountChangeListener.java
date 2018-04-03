package com.fanwe.lib.viewpager.helper;

/**
 * 页数变化监听
 */
public abstract class FPagerCountChangeListener extends FPagerDataSetObserver
{
    private int mCount;

    @Override
    protected void onChanged()
    {
        final int count = getAdapterCount();
        if (mCount != count)
        {
            mCount = count;
            onPageCountChanged(count);
        }
    }

    /**
     * 页数发生改变回调
     *
     * @param count
     */
    protected abstract void onPageCountChanged(int count);
}
