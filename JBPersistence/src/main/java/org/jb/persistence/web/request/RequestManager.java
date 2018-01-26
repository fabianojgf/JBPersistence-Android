package org.jb.persistence.web.request;

import android.net.Uri;

import org.jb.persistence.web.annotation.Request;
import org.jb.persistence.web.xml.XMLRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by fabiano on 19/04/17.
 */

public class RequestManager {
    public String sendRequest(XMLRequest request, String content) throws IOException {
        return sendRequest(request, content, null, null, null, null);
    }

    public String sendRequest(XMLRequest request, String content,
                              String[] pathParams, Object[] pathValues,
                              String[] queryParams, Object[] queryValues) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        URL url = new URL(request.getPath());

        String textURL = request.getPath();//.replace("http://192.168.25.7:8080", "http://10a421c0.ngrok.io");
        url = new URL(textURL);

        if(pathParams != null && pathParams.length > 0) {
            String path = new String(textURL);
            for(int i = 0; i < pathParams.length; i++) {
                path = path.replace(pathParams[i], String.valueOf(pathValues[i]));
            }
            url = new URL(path);
        }

        if(queryParams != null && queryParams.length > 0) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(url.getProtocol());
            builder.encodedAuthority(url.getAuthority());
            builder.encodedPath(url.getPath());

            for(int i = 0; i < queryParams.length; i++) {
                builder.appendQueryParameter(queryParams[i], String.valueOf(queryValues[i]));
            }
            String queryURL = builder.build().toString();
            url = new URL(queryURL);
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod(request.getMethod().name());
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", request.getConsumeType().description());

            if(content != null && !content.isEmpty()) {
                byte[] postDataBytes = content.getBytes("UTF-8");

                connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                connection.setDoOutput(true);
                connection.getOutputStream().write(postDataBytes);
            }

            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            stream = connection.getInputStream();
            if (stream != null) {
                result = convertStreamToString(stream);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
