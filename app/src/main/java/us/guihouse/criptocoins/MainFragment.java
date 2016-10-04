package us.guihouse.criptocoins;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import us.guihouse.criptocoins.adapters.TickerAdapter;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.coinmarketcap_api.FetchTickerAsyncTask;
import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.AsyncTaskFavorite;
import us.guihouse.criptocoins.repositories.AsyncTaskSelectDatabase;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.SelectDataBaseCallback;
import us.guihouse.criptocoins.repositories.TickerRepository;


public class MainFragment extends Fragment implements SelectDataBaseCallback, onRowClick {

    private RecyclerView rvCryptocoins;
    private TickerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout srlRvCryptocoins;

    public MainFragment() {
    }

    public static MainFragment newInstance(int page, String title) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString(Resources.getSystem().getString(R.string.fragmentMainTitle), title);
        mainFragment.setArguments(args);
        return mainFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        rvCryptocoins = (RecyclerView) view.findViewById(R.id.rvCryptocoins);
        this.adapter = new TickerAdapter(getContext(), this);
        this.rvCryptocoins.setAdapter(adapter);


        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvCryptocoins.setHasFixedSize(true);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(getContext());
        rvCryptocoins.setLayoutManager(mLayoutManager);

        srlRvCryptocoins = (SwipeRefreshLayout) view.findViewById(R.id.srlRvCryptocoins);
        srlRvCryptocoins.setRefreshing(true);
        srlRvCryptocoins.setOnRefreshListener(((MainActivity)getActivity()));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //CALLBACK DO SELECT DO SQLITE
    public void onSelectResult(ArrayList<Ticker> result) {
        this.setOrUpdateRecyclerView(result);
    }

    public void selectDataToShow(TickerRepository tickerRepository) {
        AsyncTaskSelectDatabase asyncTaskSelect = new AsyncTaskSelectDatabase(this, tickerRepository, false);
        asyncTaskSelect.execute();
    }

    private void setOrUpdateRecyclerView(ArrayList<Ticker> tickersFeedList) {
        this.adapter.setTickers(tickersFeedList);
        ((MainActivity)getActivity()).showLastUpdateDate(System.currentTimeMillis());
        srlRvCryptocoins.setRefreshing(false);
    }

    @Override
    public void favoriteCryptocoin(Integer id) {
        AsyncTaskFavorite asyncTaskFavorite = new AsyncTaskFavorite(((MainActivity)getActivity()).repositoryManager.getTickerRepository(), id, true);
        asyncTaskFavorite.execute();
        this.adapter.setCheckedStar(id);
        ((MainActivity)getActivity()).updateFavoriteList();
    }

    @Override
    public void unFavoriteCryptocoin(Integer id) {
        AsyncTaskFavorite asyncTaskFavorite = new AsyncTaskFavorite( ((MainActivity)getActivity()).repositoryManager.getTickerRepository(), id, false);
        asyncTaskFavorite.execute();
        this.adapter.setUncheckedStar(id);
        ((MainActivity)getActivity()).updateFavoriteList();
    }

    @Override
    public void onRowClick(Integer id) {
        Intent openDetailsActivity = new Intent(getActivity(), DetailsActivity.class);
        openDetailsActivity.putExtra(MainActivity.EXTRA_ID_CRYPTOCOIN, id);
        this.startActivity(openDetailsActivity);
    }
}
