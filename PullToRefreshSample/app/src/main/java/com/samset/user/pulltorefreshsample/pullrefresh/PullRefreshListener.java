package com.samset.user.pulltorefreshsample.pullrefresh;

public abstract class PullRefreshListener {
    public void onfinish(){};
    public abstract void onRefresh(PullRefreshLayout materialRefreshLayout);
    public void onRefreshLoadMore(PullRefreshLayout materialRefreshLayout){};
}
