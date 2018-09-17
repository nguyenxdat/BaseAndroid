package vn.datnx.demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import vn.datnx.demo.presenter.BasePresenter;

public abstract class BaseActivity extends AppCompatActivity {

    private BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = initPresenter();
        initViews(savedInstanceState);
        initVariables(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract BasePresenter initPresenter();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initVariables(Bundle savedInstanceState);

    protected void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {

    }

    public void closeLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenter) {
            presenter.destroy();
        }
    }
}
