package us.guihouse.criptocoins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinsSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity{

    private static final String URLREQUEST = "https://api.coinmarketcap.com/v1/ticker?limit=10";
    private AsyncTaskRequestHttp asyncTaskHttp;
    private AsyncTaskDataBase asyncTaskDataBase;
    private ListView lvCryptocoins;
    private CryptocoinAdapter adapter;
    private List<CryptoCoin> cryptoCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCryptocoins = (ListView) findViewById(R.id.lvCryptocoins);



        /*asyncTaskDataBase = new AsyncTaskDataBase(
                new AsyncTaskResult() {
                    @Override
                    public void onAsyncTaskResult(CryptocoinsSQLiteOpenHelper result) {

                    }
                }, MainActivity.this);*/ //TODO


        asyncTaskHttp = new AsyncTaskRequestHttp(
            new AsyncTaskResult() {
                @Override
                public void onAsyncTaskResult(ArrayList<CryptoCoin> result) {
                    cryptoCoins = result;
                    adapter = new CryptocoinAdapter(MainActivity.this, R.layout.cryptocoin_row_item, cryptoCoins);

                    lvCryptocoins.setAdapter(adapter);
                }
            }, URLREQUEST);

        asyncTaskHttp.execute();
    }

}
