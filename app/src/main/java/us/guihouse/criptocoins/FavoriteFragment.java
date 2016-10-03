package us.guihouse.criptocoins;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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

import java.util.ArrayList;

import us.guihouse.criptocoins.adapters.TickerAdapter;
import us.guihouse.criptocoins.coinmarketcap_api.AsyncTaskHttpResult;
import us.guihouse.criptocoins.coinmarketcap_api.FetchTickerAsyncTask;
import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.AsyncTaskFavorite;
import us.guihouse.criptocoins.repositories.AsyncTaskSelectDatabase;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.SelectDataBaseCallback;

public class FavoriteFragment extends Fragment implements RepositoryManagerCallback, AsyncTaskHttpResult, SelectDataBaseCallback, onRowClick {

    private RepositoryManager repositoryManager;

    private FetchTickerAsyncTask asyncTaskHttp;
    private TextView tvLastUpdateDate;
    private RecyclerView rvFavoriteCryptocoins;
    private SwipeRefreshLayout srlRvCryptocoins;
    private TickerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(int page, String title) {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString(Resources.getSystem().getString(R.string.fragmentFavoriteTitle), title);
        favoriteFragment.setArguments(args);
        return favoriteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavoriteCryptocoins = (RecyclerView) view.findViewById(R.id.rvFavoriteCryptocoins);
        srlRvCryptocoins = (SwipeRefreshLayout) view.findViewById(R.id.srlRvCryptocoins);


        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvFavoriteCryptocoins.setHasFixedSize(true);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(getContext());
        rvFavoriteCryptocoins.setLayoutManager(mLayoutManager);

        this.adapter = new TickerAdapter(getContext(), this);
        this.rvFavoriteCryptocoins.setAdapter(adapter);

        repositoryManager = new RepositoryManager(getContext(), this);

        srlRvCryptocoins.setRefreshing(true);

        srlRvCryptocoins.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRequest();
            }
        });

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
        ((MainActivity)getActivity()).updateLastUpdateDate(System.currentTimeMillis());
    }

    @Override
    public void onFetchConnectionError() {
        Toast.makeText(getContext(), "Erro de conexão!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onServerError() {
        Toast.makeText(getContext(), "Não foi possível atualizar os dados!", Toast.LENGTH_LONG).show();
        this.selectDataToShow();
    }

    //CALLBACK DO SELECT DO SQLITE
    public void onSelectResult(ArrayList<Ticker> result) {
        this.setOrUpdateRecyclerView(result);
    }

    private void selectDataToShow() {
        AsyncTaskSelectDatabase asyncTaskSelect = new AsyncTaskSelectDatabase(this, this.repositoryManager.getTickerRepository(), true);
        asyncTaskSelect.execute();
    }

    private void setOrUpdateRecyclerView(ArrayList<Ticker> tickersFeedList) {
        this.adapter.setTickers(tickersFeedList);
        srlRvCryptocoins.setRefreshing(false);
        ((MainActivity)getActivity()).showLastUpdateDate(System.currentTimeMillis());
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
        Intent openDetailsActivity = new Intent(getActivity(), DetailsActivity.class);
        openDetailsActivity.putExtra(MainActivity.EXTRA_ID_CRYPTOCOIN, id);
        this.startActivity(openDetailsActivity);
    }

}
