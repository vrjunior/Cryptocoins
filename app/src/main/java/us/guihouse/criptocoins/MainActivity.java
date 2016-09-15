package us.guihouse.criptocoins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import us.guihouse.criptocoins.coinmarketcap_api.RequestHttpAsyncTask;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.CryptocoinRepository;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;

public class MainActivity extends AppCompatActivity implements RepositoryManagerCallback, AsyncTaskHttpResult {

    private static final String URLREQUEST = "https://api.coinmarketcap.com/v1/ticker?limit=10";
    private RepositoryManager repositoryManager;

    private RequestHttpAsyncTask asyncTaskHttp;
    private ListView lvCryptocoins;
    private CryptocoinAdapter adapter;
    private CryptocoinRepository cryptocoinRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCryptocoins = (ListView) findViewById(R.id.lvCryptocoins);

        repositoryManager = new RepositoryManager(this, this);
    }

    @Override
    public void onManagerReady() {
        doRequest();
    }

    private void doRequest() {
        if (asyncTaskHttp != null) {
            return;
        }

        asyncTaskHttp = new RequestHttpAsyncTask(this, URLREQUEST);
        asyncTaskHttp.execute();
    }

    @Override
    public void onFetchSuccess(ArrayList<CryptoCoin> result) {
        this.cryptocoinRepository = repositoryManager.getCryptocoinRepository();
        this.cryptocoinRepository.insertAllCryptocoins(result); //ISSO NÃO É AQUI, APENAS PARA TESTAR
    }

    @Override
    public void onFetchConnectionError() {
        Toast.makeText(this, "Erro de conexão!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServerError() {
        Toast.makeText(this, "Não foi possível atualizar os dados!", Toast.LENGTH_LONG).show();
    }
}
