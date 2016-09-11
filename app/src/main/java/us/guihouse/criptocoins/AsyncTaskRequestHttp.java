package us.guihouse.criptocoins;

import android.os.AsyncTask;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import us.guihouse.criptocoins.coinmarketcap_api.CryptoCoinParser;
import us.guihouse.criptocoins.coinmarketcap_api.RequestHttp;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public class AsyncTaskRequestHttp extends AsyncTask<Void, ArrayList<CryptoCoin>, ArrayList<CryptoCoin>> {
    private String url;
    private AsyncTaskResult instance;

    public AsyncTaskRequestHttp(AsyncTaskResult instance, String url) {
        this.url = url;
        this.instance = instance;
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
            e.printStackTrace(); //URL initialize exception

        } catch (RequestHttp.RequestFail requestFail) {
            //TODO: Informar erro e dar a opção de mudar de servidor.
            requestFail.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace(); //Error to create JSONArray or json hash does not exist
            //TODO: Informar erro e exibir botão para tentar novamente.
        }

        return  result;
    }

    @Override
    protected void onPostExecute(ArrayList<CryptoCoin> result) {
        super.onPostExecute(result);
        instance.onAsyncTaskResult(result);
    }
}
