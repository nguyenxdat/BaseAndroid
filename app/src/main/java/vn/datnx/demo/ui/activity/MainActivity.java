package vn.datnx.demo.ui.activity;

import android.os.Bundle;

import vn.datnx.demo.R;
import vn.datnx.demo.presenter.BasePresenter;
import vn.datnx.demo.presenter.TestPresenter;

public class MainActivity extends BaseActivity implements TestPresenter.TestView {

    private TestPresenter testPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresenter() {
        testPresenter = new TestPresenter(this, this);
        return testPresenter;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        requestApi();
    }

    private void requestApi() {
        testPresenter.testRequestApi();
    }

    @Override
    public void didTestViewSuccess() {

    }

    @Override
    public void didTestViewFailure(int errorCode, String errorMessage) {
        showToast(errorMessage);
    }

}
