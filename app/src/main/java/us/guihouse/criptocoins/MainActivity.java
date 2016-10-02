package us.guihouse.criptocoins;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import us.guihouse.criptocoins.adapters.TickerAdapter;
import us.guihouse.criptocoins.coinmarketcap_api.FetchTickerAsyncTask;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.AsyncTaskFavorite;
import us.guihouse.criptocoins.repositories.AsyncTaskSelectDatabase;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.SelectDataBaseCallback;

public class MainActivity extends AppCompatActivity implements RepositoryManagerCallback, AsyncTaskHttpResult, SelectDataBaseCallback, onRowClick {


    private RepositoryManager repositoryManager;

    private FetchTickerAsyncTask asyncTaskHttp;
    private TextView tvLastUpdateDate;
    private RecyclerView rvCryptocoins;
    private SwipeRefreshLayout srlRvCryptocoins;
    private TickerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String EXTRA_ID_CRYPTOCOIN = "idCryptocoin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLastUpdateDate = (TextView) findViewById(R.id.tvLastUpdateDate);
        rvCryptocoins = (RecyclerView) findViewById(R.id.rvCryptocoins);
        srlRvCryptocoins = (SwipeRefreshLayout) findViewById(R.id.srlRvCryptocoins);


        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvCryptocoins.setHasFixedSize(true);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(this);
        rvCryptocoins.setLayoutManager(mLayoutManager);

        this.adapter = new TickerAdapter(this, this);
        this.rvCryptocoins.setAdapter(adapter);

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
        asyncTaskHttp = new FetchTickerAsyncTask(this, repositoryManager.getTickerRepository());
        asyncTaskHttp.execute();
    }

    @Override
    public void onFetchSuccess() {
        this.selectDataToShow();
    }

    @Override
    public void onFetchConnectionError() {
        Toast.makeText(this, "Erro de conexão!", Toast.LENGTH_LONG).show();
        this.selectDataToShow();
    }

    @Override
    public void onServerError() {
        Toast.makeText(this, "Não foi possível atualizar os dados!", Toast.LENGTH_LONG).show();
        this.selectDataToShow();
    }

    //CALLBACK DO SELECT DO SQLITE
    public void onSelectResult(ArrayList<Ticker> result) {
        this.setOrUpdateRecyclerView(result);
    }

    private void selectDataToShow() {
        AsyncTaskSelectDatabase asyncTaskSelect = new AsyncTaskSelectDatabase(this, this.repositoryManager.getTickerRepository());
        asyncTaskSelect.execute();
    }

    private void setOrUpdateRecyclerView(ArrayList<Ticker> tickersFeedList) {
        this.adapter.setTickers(tickersFeedList);
        srlRvCryptocoins.setRefreshing(false);
        this.updateLastUpdateDate(tickersFeedList.get(0).getLastUpdated());
    }

    private void updateLastUpdateDate(long timespan) {
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss");
        Calendar cal = new GregorianCalendar();

        cal.setTimeInMillis(timespan * 1000);
        tvLastUpdateDate.setText(sdf.format(cal.getTime()));
    }

    @Override
    public void favoriteCryptocoin(Integer id) {
        AsyncTaskFavorite asyncTaskFavorite = new AsyncTaskFavorite(this.repositoryManager.getTickerRepository(), id, true);
        this.adapter.setCheckedStar(id);
    }

    @Override
    public void unFavoriteCryptocoin(Integer id) {
        this.repositoryManager.getTickerRepository().unFavoriteACryptocoin(id);
        this.adapter.setUncheckedStar(id);
    }

    @Override
    public void onRowClick(Integer id) {
        Intent openDetailsActivity = new Intent(MainActivity.this, DetailsActivity.class);
        openDetailsActivity.putExtra(EXTRA_ID_CRYPTOCOIN, id);
        this.startActivity(openDetailsActivity);
    }
}
