package vn.datnx.demo.presenter;

import android.content.Context;

import com.android.volley.Response;

import vn.datnx.demo.network.BaseNetwork;
import vn.datnx.demo.ui.activity.BaseActivity;

public class BasePresenter {

    private Context context;
    private BaseNetwork task;

    BasePresenter(Context context) {
        this.context = context;
    }

    Context getContext() {
        return context;
    }

    <T> void requestAPI(BaseNetwork<T> task, final Response.Listener<T> success, final BaseNetwork.ErrorListener failure) {
        showLoading();
        this.task = task.request(new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                closeLoading();
                success.onResponse(response);
            }
        }, new BaseNetwork.ErrorListener() {
            @Override
            public void onError(int errorCode, String errorMessage) {
                closeLoading();
                failure.onError(errorCode, errorMessage);
            }
        });
    }

    private void showLoading() {
        BaseActivity baseActivity = (BaseActivity) context;
        if (null != baseActivity) {
            baseActivity.showLoading();
        }
    }

    private void closeLoading() {
        BaseActivity baseActivity = (BaseActivity) context;
        if (null != baseActivity) {
            baseActivity.closeLoading();
        }
    }

    public void destroy() {
        task.cancel();
        if (null != context) {
            context = null;
        }
    }
}
