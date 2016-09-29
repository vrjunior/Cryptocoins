package us.guihouse.criptocoins;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import us.guihouse.criptocoins.adapters.TickerAdapter;
import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.repositories.RepositoryManager;
import us.guihouse.criptocoins.repositories.RepositoryManagerCallback;
import us.guihouse.criptocoins.repositories.TickerRepository;

public class DetailsActivity extends AppCompatActivity  implements RepositoryManagerCallback {

    private RepositoryManager repositoryManager;
    private Ticker ticker;
    private int extraId;
    private TickerRepository tickerRepository;

    private ImageView ivCoinLogo;
    private TextView tvCoinName;
    private TextView tvCoinSymbol;
    private TextView tvPriceUsd;
    private TextView tvPriceBtc;
    private TextView tvMarketCapUsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivCoinLogo = (ImageView) findViewById(R.id.ivCoinLogo);
        tvCoinName = (TextView) findViewById(R.id.tvCoinName);
        tvCoinSymbol = (TextView) findViewById(R.id.tvCoinSymbol);
        tvPriceUsd = (TextView) findViewById(R.id.tvPriceUsd);
        tvPriceBtc = (TextView) findViewById(R.id.tvPriceBtc);
        tvMarketCapUsd = (TextView) findViewById(R.id.tvMarketCapUsd);

        extraId = getIntent().getExtras().getInt(MainActivity.EXTRA_ID_CRYPTOCOIN, -1);
        this.repositoryManager = new RepositoryManager(this, this);
    }

    @Override
    public void onManagerReady() {
        tickerRepository = repositoryManager.getTickerRepository();
        ticker = tickerRepository.getTickerByCryptocoinId(this.extraId);
        if(ticker != null) {
            InputStream ims;
            try {
                ims = this.getAssets().open(TickerAdapter.COINS_LOGOS_FOLDER + ticker.getCryptocoin().getIdString() + TickerAdapter.POS_FIX_IMG_LOGOS);
                Drawable d = Drawable.createFromStream(ims, null);
                this.ivCoinLogo.setImageDrawable(d);
            }catch (IOException e) {}

            tvCoinName.setText(this.ticker.getCryptocoin().getName());
            tvCoinSymbol.setText(this.ticker.getCryptocoin().getSymbol());
            tvPriceUsd.setText(String.format("$%.6f USD", this.ticker.getPriceUsd()));
            tvPriceBtc.setText(String.format("%.2f BTC", this.ticker.getPriceBtc()));
            tvMarketCapUsd.setText(String.format("$%.2f", this.ticker.getMarketCapUsd()));
        }
    }
}
