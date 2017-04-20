package xyz.morrisblog.webservercmd;

import java.io.IOException;
import java.io.InputStream;

class Request {
    static String parseUri(InputStream requestStream) throws IOException {
        int requestLength = 0;
        while (requestLength == 0) {
            requestLength = requestStream.available();
        }

        byte[] buffer = new byte[requestLength];

        int readCount = 0; // 已经成功读取的字节的个数
        while (readCount < requestLength) {
            readCount += requestStream.read(buffer, readCount, requestLength - readCount);
        }

        String requestStr = new String(buffer);
        String uri = null;

        int firstSpace = requestStr.indexOf(" ");
        if (firstSpace != -1) {
            int secondSpace = requestStr.indexOf(" ", firstSpace + 1);
            if (secondSpace > firstSpace) {
                uri = requestStr.substring(firstSpace, secondSpace);
            }
        }

        return uri;
    }
}
