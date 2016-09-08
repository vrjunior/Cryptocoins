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

    public class RequestFail extends Exception {

    }
    private final URL requestUrl;
    public RequestHttp(String url) throws MalformedURLException {

        this.requestUrl = new URL(url);
    }

    public String getTicker(int limit) throws RequestFail {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) this.requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Accept", "application/json");

            String parameters = new StringBuilder("limit=").append(limit).toString();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + requestUrl.toString());
            System.out.println("Post parameters : " + parameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            throw new RequestFail();
        }

        return null;
    }

}
