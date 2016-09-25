package us.guihouse.criptocoins.coinmarketcap_api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aluno on 08/09/16.
 */
public class RequestHttp {
    private static final String TAG = "REQUEST_HTTP";

    public class RequestFail extends Exception {}
    public class NoConnection extends RequestFail {}

    private final URL requestUrl;

    public RequestHttp(String url) throws MalformedURLException {
        this.requestUrl = new URL(url);
    }

    public String executeRequest() throws RequestFail {
        if (!isInternetAvailable()) {
            throw new NoConnection();
        }

        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        StringBuffer response = new StringBuffer();

        try {
            Log.d(TAG, "Sending 'GET' request to " + requestUrl.toString());

            urlConnection = (HttpURLConnection) this.requestUrl.openConnection();
            urlConnection.setDefaultUseCaches(false);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Pragma", "no-cache");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");

            int responseCode = urlConnection.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            if (responseCode != 200) {
                throw new RequestFail();
            }

            try {
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
            } finally {
                if(in != null)
                    in.close();
            }
        } catch (IOException e) {
            throw new RequestFail();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return response.toString();
    }

    private boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (IOException e) {
            return false;
        }

    }
}
