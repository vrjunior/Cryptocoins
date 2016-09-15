package us.guihouse.criptocoins.coinmarketcap_api;

import android.os.AsyncTask;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public class RequestHttpAsyncTask extends AsyncTask<Void, ArrayList<CryptoCoin>, ArrayList<CryptoCoin>> {
    private String url;
    private AsyncTaskHttpResult callback;

    public RequestHttpAsyncTask(AsyncTaskHttpResult callback, String url) {
        this.url = url;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<CryptoCoin> doInBackground(Void... voids) {
        ArrayList<CryptoCoin> result = null;
        try {
            RequestHttp requestHttp = new RequestHttp(this.url);
            String rawData = requestHttp.getTicker();
            CryptoCoinParser parser = new CryptoCoinParser(rawData);
            result = parser.getCryptoCoinArrayList();

        } catch (MalformedURLException e) {
            callback.onFetchConnectionError();

        } catch (RequestHttp.RequestFail requestFail) {
            callback.onFetchConnectionError();

        } catch (JSONException e) {
             //Error to create JSONArray or json hash does not exist
            callback.onServerError();
        }
        return  result;
    }

    @Override
    protected void onPostExecute(ArrayList<CryptoCoin> result) {
        super.onPostExecute(result);
        callback.onFetchSuccess(result);
    }
}
