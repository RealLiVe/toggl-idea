package com.github.reallive.idea.timetrack.toggl.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IOUtils {
    public static String toString(InputStream stream) throws IOException {
        if (stream == null) {
            return null;
        }
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (stream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }

    public static void silentClose(URLConnection connection) {
        if (connection != null) {
            try {
                connection.getInputStream().close();
            } catch (Exception e) {
            }

            if (connection instanceof HttpURLConnection) {
                try {
                    ((HttpURLConnection) connection).getErrorStream().close();
                } catch (Exception e) {
                }
                try {
                    ((HttpURLConnection) connection).disconnect();
                } catch (Exception e) {
                }
            }
        }
    }
}