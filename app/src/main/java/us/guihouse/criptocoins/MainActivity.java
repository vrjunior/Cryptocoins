package us.guihouse.criptocoins;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import us.guihouse.criptocoins.coinmarketcap_api.FetchTickerAsyncTask;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinRepository;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;

public class MainActivity extends AppCompatActivity implements RepositoryManagerCallback, AsyncTaskHttpResult {


    private RepositoryManager repositoryManager;

    private FetchTickerAsyncTask asyncTaskHttp;
    private ListView lvCryptocoins;
    private CryptocoinAdapter adapter;
    private CryptocoinRepository cryptocoinRepository;
    private int listViewOffset;
    private final int listViewLimit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCryptocoins = (ListView) findViewById(R.id.lvCryptocoins);
        listViewOffset = 0;
        repositoryManager = new RepositoryManager(this, this);

    }

    @Override
    public void onManagerReady() {
        this.doRequest();
    }

    private void doRequest() {
        if (asyncTaskHttp != null) {
            return;
        }

        asyncTaskHttp = new FetchTickerAsyncTask(this, repositoryManager.getCryptocoinRepository());
        asyncTaskHttp.execute();
    }

    @Override
    public void onFetchSuccess() {
        this.cryptocoinRepository = repositoryManager.getCryptocoinRepository();
        //this.appendCryptocoinsToListView(cryptocoinRepository.getCryptocoins(this.listViewLimit, this.listViewOffset));
    }

    @Override
    public void onFetchConnectionError() {
        //Toast.makeText(this, "Erro de conexão!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServerError() {
        //Toast.makeText(this, "Não foi possível atualizar os dados!", Toast.LENGTH_LONG).show();
    }

    private void appendCryptocoinsToListView(ArrayList<CryptoCoin> cryptocoins) {
        if(this.adapter == null) {
            adapter = new CryptocoinAdapter(this, R.layout.cryptocoin_row_item, cryptocoins);
            lvCryptocoins.setAdapter(adapter);
        }
        else {
            adapter.addAll(cryptocoins);
            adapter.notifyDataSetChanged();
        }
    }
}
