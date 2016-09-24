package us.guihouse.criptocoins.repositories;

import android.os.AsyncTask;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by vrjunior on 24/09/16.
 */

public class AsyncTaskSelectDatabase extends AsyncTask <Void, ArrayList<CryptoCoin>, ArrayList<CryptoCoin>> {

    private SelectDataBaseCallback instance;
    private CryptocoinRepository cryptocoinRepository;
    private int limit;
    private int offset;

    public AsyncTaskSelectDatabase(SelectDataBaseCallback instance, CryptocoinRepository cryptocoinRepository, int limit, int offset) {
        this.instance = instance;
        this.cryptocoinRepository = cryptocoinRepository;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<CryptoCoin> doInBackground(Void... params) {
        ArrayList<CryptoCoin> result = cryptocoinRepository.getCryptocoins(this.limit, this.offset);

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<CryptoCoin> result) {
        super.onPostExecute(result);
        this.instance.onSelectResult(result);
    }
}
