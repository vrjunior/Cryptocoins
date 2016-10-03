package us.guihouse.criptocoins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.util.List;

import us.guihouse.criptocoins.adapters.FragmentPagerAdapter;
import us.guihouse.criptocoins.adapters.TickerAdapter;
import us.guihouse.criptocoins.coinmarketcap_api.FetchTickerAsyncTask;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.AsyncTaskFavorite;
import us.guihouse.criptocoins.repositories.AsyncTaskSelectDatabase;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.SelectDataBaseCallback;

public class MainActivity extends FragmentActivity implements RepositoryManagerCallback, AsyncTaskHttpResult {

    private FetchTickerAsyncTask asyncTaskHttp;
    protected RepositoryManager repositoryManager;
    private TextView tvLastUpdateDate;
    private SwipeRefreshLayout srlRvCryptocoins;
    public static String EXTRA_ID_CRYPTOCOIN = "idCryptocoin";
    public static String SHARED_PREFERENCE_FILE = "us.guihouse.cryptocoins.lastUpdatePreference";
    public static String SHARED_PREFERENCE_LAST_UPDATE = "lastUpdate";
    private SharedPreferences sharedPrefe;
    private FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.repositoryManager = new RepositoryManager(this, this);
        tvLastUpdateDate = (TextView) this.findViewById(R.id.tvLastUpdateDate);
        sharedPrefe = getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new FragmentPagerAdapter(getSupportFragmentManager(), this);
        vpPager.setAdapter(adapterViewPager);



        srlRvCryptocoins = (SwipeRefreshLayout) findViewById(R.id.srlRvCryptocoins);
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

    protected void doRequest() {
        asyncTaskHttp = new FetchTickerAsyncTask(this, repositoryManager.getTickerRepository());
        asyncTaskHttp.execute();
    }

    private void selectDataToFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> frags = fragmentManager.getFragments();

        for(Fragment frag: frags) {
            if (frag instanceof MainFragment) {
                ((MainFragment) frag).selectDataToShow(this.repositoryManager.getTickerRepository());
            }
            if (frag instanceof FavoriteFragment) {
                ((FavoriteFragment) frag).selectDataToShow(this.repositoryManager.getTickerRepository());
            }
        }
    }


    @Override
    public void onFetchSuccess() {
        android.app.Fragment frag = getFragmentManager().findFragmentById(R.layout.fragment_main);

        this.selectDataToFragments();

        srlRvCryptocoins.setRefreshing(false);
        this.updateLastUpdateDate(System.currentTimeMillis());
    }


    @Override
    public void onFetchConnectionError() {
        Toast.makeText(this, "Erro de conexão!", Toast.LENGTH_LONG).show();
        this.selectDataToFragments();
        srlRvCryptocoins.setRefreshing(false);
    }

    @Override
    public void onServerError() {
        Toast.makeText(this, "Não foi possível atualizar os dados!", Toast.LENGTH_LONG).show();
        srlRvCryptocoins.setRefreshing(false);

        this.selectDataToFragments();
        srlRvCryptocoins.setRefreshing(false);
    }

    public void updateLastUpdateDate(long timeMilis) {
        SharedPreferences.Editor editor = sharedPrefe.edit();
        editor.putLong(SHARED_PREFERENCE_LAST_UPDATE, System.currentTimeMillis());
        editor.commit();
    }

    public void showLastUpdateDate(long timeMilis) {
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        long time = sharedPrefe.getLong(SHARED_PREFERENCE_LAST_UPDATE, 0);
        calendar.setTimeInMillis(time);
        tvLastUpdateDate.setText(sdf.format(calendar.getTime()));
    }
}
