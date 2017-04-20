package xyz.morrisblog.webservercmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestProcessor implements Runnable {
    private InputStream requestStream;
    private OutputStream responseStream;
    private String workingDir;

    static RequestProcessor getNewRequestContainer() {
        return new RequestProcessor();
    }

    void setRequestStream(InputStream requestStream) {
        this.requestStream = requestStream;
    }

    void setResponseStream(OutputStream responseStream) {
        this.responseStream = responseStream;
    }

    void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @Override
    public void run() {
        String uri = null;
        try {
            uri = Request.parseUri(requestStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(uri);

        Response response = Response.getNewResponseInstance();
        response.getResource(uri);
        response.send(responseStream);
    }
}
