package vn.datnx.demo.task;

import android.content.Context;
import android.os.AsyncTask;

import vn.datnx.demo.task.helper.ResponseLoadData;

public abstract class TaskBaseAsyncTask<RESULT> extends AsyncTask<Void, Void, RESULT> {

    private Context context;
    private ResponseLoadData.Listener<RESULT> listener;

    public TaskBaseAsyncTask(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected RESULT doInBackground(Void... params) {
        return queryInBackground(params);
    }

    @Override
    protected void onPostExecute(RESULT result) {
        if (null != listener) {
            listener.onResponse(result);
        }
    }

    public void request() {
        request(null);
    }

    public void request(ResponseLoadData.Listener<RESULT> listener) {
        this.execute();
        this.listener = listener;
    }

    protected abstract RESULT queryInBackground(Void... params);

}
