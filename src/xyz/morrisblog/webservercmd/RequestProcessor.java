package xyz.morrisblog.webservercmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

enum ResponseCode {
    _200(200, "OK"),
    _404(404, "Not Found");

    private int code;
    private String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return code + " " + description;
    }
}

public class RequestProcessor implements Runnable {
    private StringBuilder request;
    private InputStream requestStream;
    private OutputStream responseStream;
    private int requestLength;
    private String workingDir;
    private String uri;

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

        parseUri();

//        System.out.println(uri == null ? "" : uri);
        sendResponse();
    }

    private void sendResponse() {
        byte[] buffer = new byte[2048];
        try {
            String message = getResponseMessage(
                    "HTTP/1.1",
                    ResponseCode._404,
                    "text/html",
                    "<h1>File Not Found</h1>");
            responseStream.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponseMessage(String httpVersion, ResponseCode responseCode, String contentType, String content) {
        int contentLength = content.length();
        String message
                = httpVersion + responseCode.toString() + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "\r\n"
                + content;

        return message;
    }

    private void parseUri() {
        int firstSpace = request.indexOf(" ");
        if (firstSpace != -1) {
            int secondSpace = request.indexOf(" ", firstSpace + 1);
            if (secondSpace > firstSpace) {
                uri = request.substring(firstSpace, secondSpace);
            }
        }
    }
}
