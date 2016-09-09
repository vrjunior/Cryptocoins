package us.guihouse.criptocoins.coinmarketcap_api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aluno on 08/09/16.
 */
public class RequestHttp {

    public class RequestFail extends Exception {}
    private final URL requestUrl;

    public RequestHttp(String url) throws MalformedURLException {
        this.requestUrl = new URL(url);
    }

    public String getTicker() throws RequestFail {
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            urlConnection = (HttpURLConnection) this.requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Accept", "application/json");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + requestUrl.toString());
            System.out.println("Response Code : " + responseCode);

            try {
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
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
    }
}
