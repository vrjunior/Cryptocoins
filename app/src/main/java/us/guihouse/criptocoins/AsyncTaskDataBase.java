package us.guihouse.criptocoins;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import us.guihouse.criptocoins.repositories.CryptocoinsSQLiteOpenHelper;

/**
 * Created by aluno on 13/09/16.
 */
public class AsyncTaskDataBase extends AsyncTask<Void, Void, CryptocoinsSQLiteOpenHelper> {

    private AsyncTaskResult instace;
    private Context context;

    public AsyncTaskDataBase(AsyncTaskResult instance, Context context) {
        this.instace = instance;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected CryptocoinsSQLiteOpenHelper doInBackground(Void... voids) {
        CryptocoinsSQLiteOpenHelper openHelper = new CryptocoinsSQLiteOpenHelper(context, null);

        return openHelper;
    }

    @Override
    protected void onPostExecute(CryptocoinsSQLiteOpenHelper cryptocoinsSQLiteOpenHelper) {
        super.onPostExecute(cryptocoinsSQLiteOpenHelper);
        instace.onAsyncTaskResult(cryptocoinsSQLiteOpenHelper);
    }
}
