package us.guihouse.criptocoins.coinmarketcap_api;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public interface AsyncTaskHttpResult {
    void onFetchSuccess();
    void onFetchConnectionError();
    void onServerError();
}
