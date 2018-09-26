package vn.datnx.demo.task;

import android.content.Context;

public class TaskGetDataInDB extends TaskBaseAsyncTask<Void> {

    public TaskGetDataInDB(Context context) {
        super(context);
    }

    @Override
    protected Void queryInBackground(Void... params) {
        return null;
    }
}
