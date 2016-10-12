package github.jzyu.com.testptrloadmore.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.finalteam.loadingviewfinal.ILoadMoreView;
import github.jzyu.com.testptrloadmore.R;

/**
 * 参考 CubeSdk: LoadMoreDefaultFooterView.java
 */
//奇怪: 如继承自 LinearLayout 则视图宽度变为wrap_content,即使在layout中是match_parent
public class LoadMoreFooterView extends RelativeLayout implements ILoadMoreView {
    public static final String TAG = LoadMoreFooterView.class.getSimpleName();

    private TextView textView;
    private ProgressBar pbLoading;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews(context);
    }

    private void setupViews(Context context) {
        inflate(context, R.layout.load_more_footer_view, this);
        textView = (TextView) findViewById(R.id.tv_tip);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        //setVisibility(GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /*@Override
    public void onUIReset(PtrFrameLayout frame) {
        L.d(TAG, "onUIReset");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        L.d(TAG, "onUIRefreshPrepare");

        setVisibility(VISIBLE);
        pbLoading.setVisibility(GONE);
        textView.setText("上拉加载更多...");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        L.d(TAG, "onUIRefreshBegin");

        setVisibility(VISIBLE);
        pbLoading.setVisibility(VISIBLE);
        textView.setText("加载中...");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        L.d(TAG, "onUIRefreshComplete");

        setVisibility(GONE);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        L.v(TAG, "onUIPositionChange");
    }*/

    @Override
    public void showNormal() {
        setVisibility(VISIBLE);
        pbLoading.setVisibility(GONE);
        textView.setText("上拉加载更多...");
    }

    @Override
    public void showNoMore() {
        setVisibility(VISIBLE);
        pbLoading.setVisibility(GONE);
        textView.setText("数据加载完毕");
    }

    @Override
    public void showLoading() {
        setVisibility(VISIBLE);
        pbLoading.setVisibility(VISIBLE);
        textView.setText("加载中...");
    }

    @Override
    public void showFail() {
        setVisibility(VISIBLE);
        pbLoading.setVisibility(GONE);
        textView.setText("加载失败");
    }

    @Override
    public View getFooterView() {
        return this;
    }
}
