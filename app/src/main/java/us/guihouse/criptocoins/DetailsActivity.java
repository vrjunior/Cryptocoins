package us.guihouse.criptocoins;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private TextView tvAvailableSupply;
    private TextView tvTotalSupply;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);

        ivCoinLogo = (ImageView) findViewById(R.id.ivCoinLogo);
        tvCoinName = (TextView) findViewById(R.id.tvCoinName);
        tvCoinSymbol = (TextView) findViewById(R.id.tvCoinSymbol);
        tvPriceUsd = (TextView) findViewById(R.id.tvPriceUsd);
        tvPriceBtc = (TextView) findViewById(R.id.tvPriceBtc);
        tvMarketCapUsd = (TextView) findViewById(R.id.tvMarketCapUsd);
        tvAvailableSupply = (TextView) findViewById(R.id.tvAvailableSupply);;
        tvTotalSupply = (TextView) findViewById(R.id.tvToSupply);
        lineChart = (LineChart) findViewById(R.id.chart);


        extraId = getIntent().getExtras().getInt(MainActivity.EXTRA_ID_CRYPTOCOIN, -1);
        this.repositoryManager = new RepositoryManager(this, this);
    }

    private LineData getChartData() {
        final String[] labels = {getString(R.string.chart7days), getString(R.string.chart24hours), getString(R.string.char1hour)};
        ArrayList<Entry> entries = new ArrayList<>();

        AxisValueFormatter formatter = new AxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
            @Override
            public int getDecimalDigits() {  return 0; }
        };

        entries.add(new Entry(0, (float)this.ticker.getPercentChange7d(), getString(R.string.chart7days)));
        entries.add(new Entry(1, (float)this.ticker.getPercentChange24h(), getString(R.string.chart24hours)));
        entries.add(new Entry(2, (float)this.ticker.getPercentChange1h(), getString(R.string.char1hour)));


        LineDataSet lds = new LineDataSet(entries, "Oscilação de mercado");
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1); // minimum axis-step (interval) is 1
        lineChart.setDescription(getString(R.string.graphicDescription));

        xAxis.setValueFormatter(formatter);
        return new LineData(lds);
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
            tvPriceBtc.setText(String.format("%.6f BTC", this.ticker.getPriceBtc()));
            tvMarketCapUsd.setText(String.format("$%.2f", this.ticker.getMarketCapUsd()));
            tvAvailableSupply.setText(String.format("%.1f", this.ticker.getAvailableSupply()));
            tvTotalSupply.setText(String.format("%.1f", this.ticker.getTotalSupply()));
            lineChart.setData(this.getChartData());
        }
    }
}
