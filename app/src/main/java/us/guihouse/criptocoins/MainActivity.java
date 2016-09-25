package us.guihouse.criptocoins;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import us.guihouse.criptocoins.adapters.CryptocoinAdapter;
import us.guihouse.criptocoins.coinmarketcap_api.FetchTickerAsyncTask;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.repositories.AsyncTaskSelectDatabase;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.SelectDataBaseCallback;

public class MainActivity extends AppCompatActivity implements RepositoryManagerCallback, AsyncTaskHttpResult, SelectDataBaseCallback {


    private RepositoryManager repositoryManager;

    private FetchTickerAsyncTask asyncTaskHttp;
    private RecyclerView rvCryptocoins;
    private SwipeRefreshLayout srlRvCryptocoins;
    private CryptocoinAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCryptocoins = (RecyclerView) findViewById(R.id.rvCryptocoins);
        srlRvCryptocoins = (SwipeRefreshLayout) findViewById(R.id.srlRvCryptocoins);


        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvCryptocoins.setHasFixedSize(true);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(this);
        rvCryptocoins.setLayoutManager(mLayoutManager);

        repositoryManager = new RepositoryManager(this, this);
        srlRvCryptocoins.setRefreshing(true);

        srlRvCryptocoins.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRequest();
            }
        });
    }

    @Override
    public void onManagerReady() {
        this.doRequest();
    }

    private void doRequest() {
        asyncTaskHttp = new FetchTickerAsyncTask(this, repositoryManager.getCryptocoinRepository());
        asyncTaskHttp.execute();
    }

    @Override
    public void onFetchSuccess() {
        this.selectDataToShow();
    }

    @Override
    public void onFetchConnectionError() {
        //Toast.makeText(this, "Erro de conexão!", Toast.LENGTH_LONG).show();
        this.selectDataToShow();
    }

    @Override
    public void onServerError() {
        //Toast.makeText(this, "Não foi possível atualizar os dados!", Toast.LENGTH_LONG).show();
        this.selectDataToShow();
    }


    //CALLBACK DO SELECT DO SQLITE
    public void onSelectResult(ArrayList<CryptoCoin> result) {
        this.setOrUpdateRecyclerView(result);
    }

    private void selectDataToShow() {
        AsyncTaskSelectDatabase asyncTaskSelect = new AsyncTaskSelectDatabase(this, this.repositoryManager.getCryptocoinRepository());
        asyncTaskSelect.execute();
    }

    private void setOrUpdateRecyclerView(ArrayList<CryptoCoin> cryptonsFeedList) {
        if(this.adapter == null) {
            this.adapter = new CryptocoinAdapter(this, R.layout.cryptocoin_row_item, cryptonsFeedList);
            this.rvCryptocoins.setAdapter(adapter);
        }
        else {
            this.adapter.updateData(cryptonsFeedList);
        }
        srlRvCryptocoins.setRefreshing(false);
    }
}
