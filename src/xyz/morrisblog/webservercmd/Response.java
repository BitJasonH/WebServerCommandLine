package xyz.morrisblog.webservercmd;

import java.io.IOException;
import java.io.OutputStream;

class Response {

    static Response getNewResponseInstance() {
        return new Response();
    }

    void getResource(String uri) {

    }

    void send(OutputStream responseStream) {
        byte[] buffer = new byte[2048];
        try {
            String message = getResponseMessage(
                    "HTTP/1.1",
                    404,
                    "text/html",
                    "<h1>File Not Found</h1>");
            responseStream.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponseMessage(String httpVersion, int responseCode, String contentType, String content) {
        int contentLength = content.length();

        return httpVersion + responseCode + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "\r\n"
                + content;
    }
}
