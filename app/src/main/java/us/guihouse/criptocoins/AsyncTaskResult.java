package us.guihouse.criptocoins;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinsSQLiteOpenHelper;

/**
 * Created by valmir.massoni on 09/09/2016.
 */
public interface AsyncTaskResult {
    public void onAsyncTaskResult(ArrayList<CryptoCoin> result);
    //public void onAsyncTaskResult(CryptocoinsSQLiteOpenHelper result);
}
