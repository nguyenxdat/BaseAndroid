package vn.datnx.demo.network;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import vn.datnx.demo.network.helper.NetworkConstant;

public class TestRequestTask extends BaseNetwork<Boolean> {

    public TestRequestTask(Context context) {
        super(context);
    }

    @Override
    protected HashMap<String, String> getParamMetersFormData() {
        return null;
    }

    @Override
    protected Boolean genDataFromJSON(JSONObject data) throws JSONException {

        return Boolean.TRUE;
    }

    @Override
    protected String getUrl() {
        return NetworkConstant.API.LOGIN;
    }

    @Override
    protected int getMethod() {
        return Request.Method.GET;
    }
}
