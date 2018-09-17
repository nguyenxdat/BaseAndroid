package vn.datnx.demo.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.datnx.demo.network.helper.ExceptionConstant;
import vn.datnx.demo.network.helper.VolleyErrorHelper;
import vn.datnx.demo.network.helper.VolleySingleton;

public abstract class BaseNetwork<RESULT_DATA extends Object> {

    private static final String CODE = "_msg_code";
    private static final int SUCCESS = 1;

    private static final int NETWORK_TIME_OUT = 30000;
    private Context context;
    private Request<RESULT_DATA> request;

    public BaseNetwork(Context context) {
        this.context = context;
    }

    public final BaseNetwork request(final Response.Listener<RESULT_DATA> listener, final ErrorListener errorListener) {
        request = new Request<RESULT_DATA>(getMethod(), getUrl(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyErrorHelper volleyErrorHelper;
                if (error instanceof VolleyErrorHelper) {
                    volleyErrorHelper = (VolleyErrorHelper) error;
                } else {
                    volleyErrorHelper = new VolleyErrorHelper(error);
                }
                errorListener.onError(volleyErrorHelper.getErrorCode(), volleyErrorHelper.getErrorMessage());
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = getParamMetersFormData();
                return params;
            }

//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = genBodyParam();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    VolleyErrorHelper volleyErrorHepler = new VolleyErrorHelper(ExceptionConstant.PARSE_ERROR, e.getMessage());
//                    Response.error(volleyErrorHepler);
//                }
//                if (null == jsonObject) {
//                    return "".getBytes();
//                } else {
//                    return jsonObject.toString().getBytes();
//                }
//            }

            @Override
            protected Response<RESULT_DATA> parseNetworkResponse(NetworkResponse response) {
                String data = new String(response.data);
                RESULT_DATA result;
                VolleyErrorHelper volleyErrorHelper;
                try {
                    volleyErrorHelper = validData(data);
                    if (null == volleyErrorHelper) {
                        JSONObject jsonObject = new JSONObject(data);
                        result = genDataFromJSON(jsonObject);
                        return Response.success(result, getCacheEntry());
                    } else {
                        return Response.error(volleyErrorHelper);
                    }
                } catch (JSONException e) {
                    volleyErrorHelper = new VolleyErrorHelper(ExceptionConstant.PARSE_ERROR, e.getMessage());
                    return Response.error(volleyErrorHelper);
                }

            }

            @Override
            protected void deliverResponse(RESULT_DATA response) {
                listener.onResponse(response);
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(NETWORK_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
        return this;
    }

    private VolleyErrorHelper validData(String json) {
        VolleyErrorHelper volleyErrorHelper;
        try {
            JSONObject jsonObject = new JSONObject(json);
            boolean isSuccess = jsonObject.getBoolean("status");
            if (isSuccess) {
                volleyErrorHelper = null;
            } else {
                JSONObject data = jsonObject.getJSONObject("data");
                int code = data.getInt("error_code");
                String message = data.getString("error_message");
                volleyErrorHelper = new VolleyErrorHelper(code, message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            volleyErrorHelper = new VolleyErrorHelper(ExceptionConstant.PARSE_ERROR, e.getMessage());
        }
        return volleyErrorHelper;
    }

    protected boolean hasValueForKey(JSONObject jsonObject, String key) {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            return true;
        } else {
            return false;
        }
    }

//    protected abstract JSONObject genBodyParam() throws JSONException;

    protected abstract HashMap<String, String> getParamMetersFormData();

    protected abstract RESULT_DATA genDataFromJSON(JSONObject data) throws JSONException;

    protected abstract String getUrl();

    protected abstract int getMethod();

    public void cancel() {
        if (!request.isCanceled()) {
            request.cancel();
        }
    }

    public interface ErrorListener {
        void onError(int errorCode, String errorMessage);
    }

    protected Context getContext() {
        return context;
    }
}
