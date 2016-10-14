# TestPtrLoadMore

### RecyclerViewFinal 增加的Api 
* setOnLoadMoreListener 设置loadMore监听器  
* onLoadMoreComplete 在loadMore结束后调用，第一个参数hasMore表示是否还有更多数据，第二个参数是加载出错的错误信息(显示在LoadMore视图上)  
* setEmptyView 设置空视图，布局要求：1.与recyclerView同级 2.外层为FrameLayout  
* setLoadMoreView 设置加载更多视图，不设置则显示内置的默认视图  
