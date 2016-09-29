package us.guihouse.criptocoins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.TickerRepository;

public class DetailsActivity extends AppCompatActivity  implements RepositoryManagerCallback {

    private RepositoryManager repositoryManager;
    private Ticker ticker;
    private int extraId;
    private TickerRepository tickerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extraId = getIntent().getExtras().getInt(MainActivity.EXTRA_ID_CRYPTOCOIN, -1);
        setContentView(R.layout.activity_details);
        this.repositoryManager = new RepositoryManager(this, this);
    }

    @Override
    public void onManagerReady() {
        tickerRepository = repositoryManager.getTickerRepository();
        ticker = tickerRepository.getTickerByCryptocoinId(this.extraId);
        if(ticker != null) {

        }
    }
}
