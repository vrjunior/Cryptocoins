package us.guihouse.criptocoins.coinmarketcap_api;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinsSQLiteOpenHelper;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public interface AsyncTaskHttpResult {
    void onFetchSuccess(ArrayList<CryptoCoin> result);
    void onFetchConnectionError();
    void onServerError();

}
