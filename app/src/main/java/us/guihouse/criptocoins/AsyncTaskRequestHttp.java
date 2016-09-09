package us.guihouse.criptocoins;

import android.os.AsyncTask;

import java.net.MalformedURLException;

import us.guihouse.criptocoins.coinmarketcap_api.RequestHttp;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public class AsyncTaskRequestHttp extends AsyncTask<Void, String, String> {
    private String url;

    public AsyncTaskRequestHttp(String url) {
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            RequestHttp requestHttp = new RequestHttp(this.url);

            result = requestHttp.getTicker();
        } catch (MalformedURLException e) {
            e.printStackTrace(); //URL initialize exception
        } catch (RequestHttp.RequestFail requestFail) {
            //TODO: Informar erro e exibir botão para tentar novamente!
            requestFail.printStackTrace();
        }
        return  result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
