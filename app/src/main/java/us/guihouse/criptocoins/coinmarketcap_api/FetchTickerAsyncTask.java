package us.guihouse.criptocoins.coinmarketcap_api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinRepository;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public class FetchTickerAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "FETCH_TIKER_TASK";
    private static final String URLREQUEST = "https://api.coinmarketcap.com/v1/ticker";

    private AsyncTaskHttpResult callback;
    private CryptocoinRepository cryptoconRepository;

    public FetchTickerAsyncTask(AsyncTaskHttpResult callback, CryptocoinRepository cryptocoinRepository) {
        this.callback = callback;
        this.cryptoconRepository = cryptocoinRepository;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            RequestHttp requestHttp = new RequestHttp(URLREQUEST);
            String rawData = requestHttp.executeRequest();
            CryptoCoinParser parser = new CryptoCoinParser(rawData);
            ArrayList<CryptoCoin> result = parser.getCryptoCoinArrayList();

            //insert or update db
            this.cryptoconRepository.insertOrUpdateCryptocoins(result);

        } catch (MalformedURLException e) {
            // Unexpected. The url is hard-coded
            Log.e(TAG, e.getMessage(), e);
        } catch (RequestHttp.NoConnection ex) {
            callback.onFetchConnectionError();
        } catch (RequestHttp.RequestFail requestFail) {
            Log.e(TAG, requestFail.getMessage(), requestFail);
            callback.onServerError();
        } catch (JSONException e) {
             //Error to create JSONArray or json hash does not exist
            Log.e(TAG, e.getMessage(), e);
            callback.onServerError();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.onFetchSuccess();
        super.onPostExecute(aVoid);
    }
}
