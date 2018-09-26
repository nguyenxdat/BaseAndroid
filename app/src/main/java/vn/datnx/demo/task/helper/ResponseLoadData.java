package vn.datnx.demo.task.helper;

public class ResponseLoadData {

    public interface Listener<RESULT> {
        void onResponse(RESULT result);
    }

}
