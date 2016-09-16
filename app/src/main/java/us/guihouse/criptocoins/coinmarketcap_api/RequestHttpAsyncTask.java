package us.guihouse.criptocoins.coinmarketcap_api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import us.guihouse.criptocoins.MainActivity;
import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinRepository;
import us.guihouse.criptocoins.repositories.RepositoryManager;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public class RequestHttpAsyncTask extends AsyncTask<Void, Void, Void> {
    private String url;
    private AsyncTaskHttpResult callback;
    private CryptocoinRepository cryptoconRepository;

    public RequestHttpAsyncTask(AsyncTaskHttpResult callback, String url, CryptocoinRepository cryptocoinRepository) {
        this.url = url;
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
            RequestHttp requestHttp = new RequestHttp(this.url);
            String rawData = requestHttp.getTicker();
            CryptoCoinParser parser = new CryptoCoinParser(rawData);
            ArrayList<CryptoCoin> result = parser.getCryptoCoinArrayList();

            //insert or update db
            this.cryptoconRepository.insertOrUpdateCryptocoins(result);

        } catch (MalformedURLException e) {
            callback.onFetchConnectionError();

        } catch (RequestHttp.RequestFail requestFail) {
            Log.d("Request Fail", requestFail.toString());
            callback.onFetchConnectionError();

        } catch (JSONException e) {
             //Error to create JSONArray or json hash does not exist
            Log.d("Error Json", e.toString());
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
