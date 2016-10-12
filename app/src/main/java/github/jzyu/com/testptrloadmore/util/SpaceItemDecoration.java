package github.jzyu.com.testptrloadmore.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    private final boolean hasHeaderItem;

    public SpaceItemDecoration(int space) {
        this.space = space;
        this.hasHeaderItem = false;
    }

    public SpaceItemDecoration(int space, boolean hasHeaderItem) {
        this.space = space;
        this.hasHeaderItem = hasHeaderItem;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (hasHeaderItem && 0 == parent.getChildAdapterPosition(view)) {
            outRect.left = 0;
            outRect.top = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            return;
        }

        outRect.left = space;
        outRect.top = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}

