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
import us.guihouse.criptocoins.repositories.TickerRepository;

public class FavoriteFragment extends Fragment implements SelectDataBaseCallback, onRowClick {

    private SwipeRefreshLayout srlFavoriteCryptocoins;
    private RecyclerView rvFavoriteCryptocoins;
    private TickerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FavoriteFragment() {
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

        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvFavoriteCryptocoins.setHasFixedSize(true);
        this.adapter = new TickerAdapter(getContext(), this);
        this.rvFavoriteCryptocoins.setAdapter(adapter);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(getContext());
        rvFavoriteCryptocoins.setLayoutManager(mLayoutManager);

        srlFavoriteCryptocoins = (SwipeRefreshLayout) view.findViewById(R.id.srlFavoriteCryptocoins);
        srlFavoriteCryptocoins.setRefreshing(true);
        srlFavoriteCryptocoins.setOnRefreshListener((MainActivity)(getActivity()));

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



    public void selectDataToShow(TickerRepository tickerRepository) {
        AsyncTaskSelectDatabase asyncTaskSelect = new AsyncTaskSelectDatabase(this, tickerRepository, true);
        asyncTaskSelect.execute();
    }

    //CALLBACK DO SELECT DO SQLITE
    public void onSelectResult(ArrayList<Ticker> result) {
        this.setOrUpdateRecyclerView(result);
    }

    private void setOrUpdateRecyclerView(ArrayList<Ticker> tickersFeedList) {
        this.adapter.setTickers(tickersFeedList);
        ((MainActivity)getActivity()).showLastUpdateDate(System.currentTimeMillis());
        this.srlFavoriteCryptocoins.setRefreshing(false);
    }

    @Override
    public void favoriteCryptocoin(Integer id) {
        AsyncTaskFavorite asyncTaskFavorite = new AsyncTaskFavorite(((MainActivity)getActivity()).repositoryManager.getTickerRepository(), id, true);
        this.adapter.setCheckedStar(id);
        ((MainActivity)getActivity()).updateFavoriteList();
    }

    @Override
    public void unFavoriteCryptocoin(Integer id) {
        ((MainActivity)getActivity()).repositoryManager.getTickerRepository().unFavoriteACryptocoin(id);
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
