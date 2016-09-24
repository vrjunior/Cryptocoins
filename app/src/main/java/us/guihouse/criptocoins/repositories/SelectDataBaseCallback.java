package us.guihouse.criptocoins.repositories;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by vrjunior on 24/09/16.
 */

public interface SelectDataBaseCallback {
    public void onSelectResult(ArrayList<CryptoCoin> result);
}
