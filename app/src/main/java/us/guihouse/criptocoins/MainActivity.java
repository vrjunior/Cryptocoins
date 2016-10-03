package us.guihouse.criptocoins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
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

public class MainActivity extends FragmentActivity {

    private TextView tvLastUpdateDate;
    public static String EXTRA_ID_CRYPTOCOIN = "idCryptocoin";
    public static String SHARED_PREFERENCE_FILE = "us.guihouse.cryptocoins.lastUpdatePreference";
    public static String SHARED_PREFERENCE_LAST_UPDATE = "lastUpdate";
    private SharedPreferences sharedPrefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLastUpdateDate = (TextView) this.findViewById(R.id.tvLastUpdateDate);
        sharedPrefe = getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE);
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
