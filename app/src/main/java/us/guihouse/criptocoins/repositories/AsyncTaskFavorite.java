package us.guihouse.criptocoins.repositories;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by vrjunior on 02/10/16.
 */

public class AsyncTaskFavorite extends AsyncTask<Void, Void, Void> {

    private TickerRepository tickerRepository;
    private Integer idCryptocoin;
    boolean flag;

    public AsyncTaskFavorite(TickerRepository tickerRepository, Integer idCryptocoin, boolean flag) {
        this.tickerRepository = tickerRepository;
        this.idCryptocoin = idCryptocoin;
        this.flag = flag;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(flag) {
            this.tickerRepository.favoriteACryptocoin(idCryptocoin);
        }
        else {
            this.tickerRepository.unFavoriteACryptocoin(idCryptocoin);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
