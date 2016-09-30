package us.guihouse.criptocoins.coinmarketcap_api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.TickerRepository;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public class FetchTickerAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "FETCH_TIKER_TASK";
    private static final String URLREQUEST = "https://api.coinmarketcap.com/v1/ticker";

    private AsyncTaskHttpResult callback;
    private TickerRepository tickerRepository;
    private Exception executionError;

    public FetchTickerAsyncTask(AsyncTaskHttpResult callback, TickerRepository tickerRepository) {
        this.callback = callback;
        this.tickerRepository = tickerRepository;
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
            ArrayList<Ticker> result = parser.getTickerArrayList();

            //insert or update db
            this.tickerRepository.insertOrUpdateTicker(result);

        } catch (MalformedURLException e) {
            // Unexpected. The url is hard-coded
            Log.e(TAG, e.getMessage(), e);
        } catch (RequestHttp.NoConnection ex) {
            executionError = ex;
        } catch (RequestHttp.RequestFail requestFail) {
            Log.e(TAG, requestFail.getMessage(), requestFail);
            executionError = requestFail;
        } catch (JSONException e) {
            // Error to create JSONArray or json hash does not exist
            Log.e(TAG, e.getMessage(), e);
            executionError = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (executionError == null) {
            callback.onFetchSuccess();
        } else if (executionError instanceof RequestHttp.NoConnection) {
            callback.onFetchConnectionError();
        } else if (executionError instanceof RequestHttp.RequestFail) {
            callback.onServerError();
        } else if (executionError instanceof JSONException) {
            callback.onServerError();
        }

        super.onPostExecute(aVoid);
    }
}
