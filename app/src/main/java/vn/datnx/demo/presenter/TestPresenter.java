package vn.datnx.demo.presenter;

import android.content.Context;

import com.android.volley.Response;

import vn.datnx.demo.network.BaseNetwork;
import vn.datnx.demo.network.TestRequestTask;

public class TestPresenter extends BasePresenter {

    public interface TestView {
        void didTestViewSuccess();
        void didTestViewFailure(int errorCode, String errorMessage);
    }

    private TestView view;

    public TestPresenter(Context context, TestView view) {
        super(context);
        this.view = view;
    }

    public void testRequestApi() {
        TestRequestTask task = new TestRequestTask(getContext());
        requestAPI(task, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                view.didTestViewSuccess();
            }
        }, new BaseNetwork.ErrorListener() {
            @Override
            public void onError(int errorCode, String errorMessage) {
                view.didTestViewFailure(errorCode, errorMessage);
            }
        });
    }
}
