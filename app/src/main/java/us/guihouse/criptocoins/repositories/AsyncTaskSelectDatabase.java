package us.guihouse.criptocoins.repositories;

import android.os.AsyncTask;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.Cryptocoin;
import us.guihouse.criptocoins.models.Ticker;

/**
 * Created by vrjunior on 24/09/16.
 */

public class AsyncTaskSelectDatabase extends AsyncTask <Void, ArrayList<Ticker>, ArrayList<Ticker>> {

    private SelectDataBaseCallback instance;
    private TickerRepository tickerRepository;

    public AsyncTaskSelectDatabase(SelectDataBaseCallback instance, TickerRepository tickerRepository) {
        this.instance = instance;
        this.tickerRepository = tickerRepository;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Ticker> doInBackground(Void... params) {
        ArrayList<Ticker> result = tickerRepository.getAllTickers();

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Ticker> result) {
        super.onPostExecute(result);
        this.instance.onSelectResult(result);
    }
}
