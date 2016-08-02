package com.samset.user.pulltorefreshsample.pullrefresh;

public interface HeadListener {
    void onComlete(PullRefreshLayout materialRefreshLayout);
    void onBegin(PullRefreshLayout materialRefreshLayout);
    void onPull(PullRefreshLayout materialRefreshLayout, float fraction);
    void onRelease(PullRefreshLayout materialRefreshLayout, float fraction);
    void onRefreshing(PullRefreshLayout materialRefreshLayout);
}
