package com.setiawanpaiman.bakeking.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.setiawanpaiman.bakeking.android.R;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

public class AutoFitRecyclerGridView extends RecyclerView {

    private GridLayoutManager mLayoutManager;
    private int mColumnWidth = -1;
    private int mHorizontalSpacing;

    public AutoFitRecyclerGridView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoFitRecyclerGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoFitRecyclerGridView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (mColumnWidth > 0) {
            int spanCount = Math.max(1, getMeasuredWidth() / (mColumnWidth + mHorizontalSpacing));
            mLayoutManager.setSpanCount(spanCount);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoFitRecyclerGridView, 0, 0);
        int verticalSpacing;
        try {
            mColumnWidth = array.getDimensionPixelSize(R.styleable.AutoFitRecyclerGridView_columnWidth, -1);
            mHorizontalSpacing = array.getDimensionPixelSize(R.styleable.AutoFitRecyclerGridView_horizontalSpacing, 0);
            verticalSpacing = array.getDimensionPixelSize(R.styleable.AutoFitRecyclerGridView_verticalSpacing, 0);
        } finally {
            array.recycle();
        }

        mLayoutManager = new GridLayoutManager(getContext(), 1);
        setLayoutManager(mLayoutManager);
        addItemDecoration(new GridSpacingItemDecoration(mHorizontalSpacing, verticalSpacing));
    }
}
