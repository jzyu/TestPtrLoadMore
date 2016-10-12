package github.jzyu.com.testptrloadmore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import github.jzyu.com.testptrloadmore.util.L;
import github.jzyu.com.testptrloadmore.util.SpaceItemDecoration;
import github.jzyu.com.testptrloadmore.view.LoadMoreFooterView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class PtrDemoActivity extends AppCompatActivity {

    public static final String TAG = PtrDemoActivity.class.getSimpleName();
    public static final int GRID_COUNT = 2;
    public static final int PAGE_COUNT = 5;
    public static final int MAX_COUNT = 18;

    @Bind(R.id.recyclerView)
    RecyclerViewFinal recyclerView;
    @Bind(R.id.ptrFrame)
    PtrFrameLayout ptrFrame;

    private LayoutType layoutType;
    private List<String> dataList;

    public enum LayoutType {LIST, GRID, STAGGER_GRID}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_demo);
        ButterKnife.bind(this);

        layoutType = LayoutType.values() [getIntent().getIntExtra(EXTRA_LAYOUT_TYPE, 0)];

        dataList = new ArrayList<>();
        refreshData();

        initView();
    }

    private void initView() {
        initPtrFrame();
        initRecyclerView();
    }

    private void initPtrFrame() {
        //PtrFrameLayout.DEBUG = true;

        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        //header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, dp2px(15), 0, dp2px(10));
        header.setPtrFrameLayout(ptrFrame);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);

        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                L.e(TAG, "onRefreshBegin");

                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();

                        ptrFrame.refreshComplete();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 500);
            }
        });
    }

    private void refreshData() {
        dataList.clear();
        for (int i = 0; i < PAGE_COUNT; i++) {
            dataList.add(String.format(Locale.getDefault(), "item %d", i));
        }
    }

    private boolean appendData() {
        boolean hasMore = true;
        int tail = dataList.size();

        for (int i = tail; i < tail + PAGE_COUNT; i++) {
            if (dataList.size() >= MAX_COUNT) {
                hasMore = false;
                break;
            }

            dataList.add(String.format(Locale.getDefault(), "item %d", i));
        }

        return hasMore;
    }

    private int dp2px(int valueInDp) {
        return (int) getResources().getDisplayMetrics().density * valueInDp;
    }

    private void initRecyclerView() {
        CommonAdapter<String> rvAdapter = new CommonAdapter<String>(this, R.layout.row_card_view, dataList) {
            final int SmallCardHeight = dp2px(100);
            final int LargeCardHeight = dp2px(200);

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.textView, s);

                if (layoutType == LayoutType.STAGGER_GRID) {
                    CardView cardView = holder.getView(R.id.cardView);
                    cardView.getLayoutParams().height = position % GRID_COUNT == 0 ? SmallCardHeight : LargeCardHeight;
                }
            }
        };
        recyclerView.setAdapter(rvAdapter);

        recyclerView.setLoadMoreView(new LoadMoreFooterView(this));
        //recyclerView.setLoadMoreView(new DefaultLoadMoreView(this));
        recyclerView.setHasLoadMore(true);
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                L.e(TAG, "loadMore");

                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean hasMore = appendData();

                        recyclerView.setHasLoadMore(hasMore);
                        recyclerView.onLoadMoreComplete();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 500);
            }
        });

        RecyclerView.LayoutManager layoutManager;
        switch (layoutType) {
            default:
            case LIST:
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                break;
            case GRID:
                layoutManager = new GridLayoutManager(this, GRID_COUNT);
                break;
            case STAGGER_GRID:
                layoutManager = new StaggeredGridLayoutManager(GRID_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(6), false));
    }
    
    public static final String EXTRA_LAYOUT_TYPE = "layout-type";

    public static Intent newIntent(Context context, LayoutType layoutType) {
        Intent intent = new Intent(context, PtrDemoActivity.class);
        intent.putExtra(EXTRA_LAYOUT_TYPE, layoutType.ordinal());
        return intent;
    }
}
