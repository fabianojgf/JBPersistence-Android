package org.jb.persistence.web.request;

import android.os.AsyncTask;

/**
 * Created by fabiano on 16/04/17.
 */

public class RequestTask extends AsyncTask<String, Integer, String> {
    private RequestHandler handler;

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    /* Defaulf methods. */

    protected String doInBackground(String... urls) {
        return "";
    }

    protected void onProgressUpdate(Integer... progress) {
        //TODO
    }

    protected void onPostExecute(String result) {
        handler.onPostExecute();
    }
}
