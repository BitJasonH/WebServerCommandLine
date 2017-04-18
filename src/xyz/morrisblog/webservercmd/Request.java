package xyz.morrisblog.webservercmd;

import java.io.IOException;
import java.io.InputStream;

public class Request implements Runnable {
    private StringBuilder request;
    private InputStream requestStream;
    private int requestLength;
    private String workingDir;

    private Request(InputStream inputStream) {
        requestStream = inputStream;
    }

    static Request getNewRequestContainer(InputStream inputStream) {
        return new Request(inputStream);
    }

    void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @Override
    public void run() {
        request = new StringBuilder(2048);
        byte[] buffer = new byte[2048];

        try {
            requestLength = requestStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < requestLength; i++) {
            request.append((char) buffer[i]);
        }

        System.out.println(request);
    }
}
