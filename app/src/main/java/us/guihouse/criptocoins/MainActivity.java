package us.guihouse.criptocoins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskResult{

    private static final String URLREQUEST = "https://api.coinmarketcap.com/v1/ticker?limit=10";
    private AsyncTaskRequestHttp asynTask;
    private ListView lvCryptocoins;
    private CryptocoinAdapter adapter;
    private List<CryptoCoin> cryptoCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCryptocoins = (ListView) findViewById(R.id.lvCryptocoins);

        asynTask = new AsyncTaskRequestHttp(MainActivity.this, URLREQUEST);
        asynTask.execute();
    }


    @Override
    public void onAsyncTaskResult(ArrayList<CryptoCoin> result) {
        this.cryptoCoins = result;
        adapter = new CryptocoinAdapter(this, R.layout.cryptocoin_row_item, cryptoCoins);
        lvCryptocoins.setAdapter(adapter);
    }
}
