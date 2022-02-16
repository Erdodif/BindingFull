package phil.petrik.bindingfull.data;

import android.content.res.Resources;
import android.util.Pair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import phil.petrik.bindingfull.R;

public class WebDoctor {
    public static HttpURLConnection createConnection(String urlExtension, String method) throws IOException {
        //String baseUrl = Resources.getSystem().getString(R.string.database_link);
        HttpURLConnection conn = (HttpURLConnection) (new URL(/*baseUrl*/  "http:\\\\10.147.20.1\\" + "/api/" + urlExtension)).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");
        return conn;
    }

    public static InputStream getResponseContent(HttpURLConnection conn) {
        try {
            return conn.getInputStream();
        } catch (IOException e) {
            return conn.getErrorStream();
        }
    }

    public static void putJSON(HttpURLConnection conn, String json) throws IOException {
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(json);
        bw.flush();
        bw.close();
        os.close();
    }

    public static Pair<Integer, InputStream> getCall(String urlExtension) throws IOException {
        HttpURLConnection conn = createConnection(urlExtension, "GET");
        return new Pair<>(conn.getResponseCode(), getResponseContent(conn));
    }

    public static Pair<Integer, InputStream> deleteCall(String urlExtension) throws IOException {
        HttpURLConnection conn = createConnection(urlExtension, "DELETE");
        return new Pair<>(conn.getResponseCode(), getResponseContent(conn));
    }

    public static Pair<Integer, InputStream> postCall(String urlExtension, String jsonContent) throws IOException {
        HttpURLConnection conn = createConnection(urlExtension, "POST");
        conn.setRequestProperty("Content-Type", "application/json");
        putJSON(conn, jsonContent);
        return new Pair<>(conn.getResponseCode(), getResponseContent(conn));
    }

    public static Pair<Integer, InputStream> patchCall(String urlExtension, String jsonContent) throws IOException {
        HttpURLConnection conn = createConnection(urlExtension, "PATCH");
        conn.setRequestProperty("Content-Type", "application/json");
        putJSON(conn, jsonContent);
        return new Pair<>(conn.getResponseCode(), getResponseContent(conn));
    }


}