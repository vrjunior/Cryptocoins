package us.guihouse.criptocoins.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by aluno on 14/09/16.
 */
public class RepositoryManager {
    private CryptocoinRepository cryptocoinRepository;

    public RepositoryManager(final Context context, final RepositoryManagerCallback callback) {
        new AsyncTask<Void, Void, SQLiteDatabase>() {
            @Override
            protected SQLiteDatabase doInBackground(Void... objects) {
                CryptocoinsSQLiteOpenHelper openHelper = new CryptocoinsSQLiteOpenHelper(context, null);
                return openHelper.getWritableDatabase();
            }

            @Override
            protected void onPostExecute(SQLiteDatabase database) {
                super.onPostExecute(database);
                cryptocoinRepository = new CryptocoinRepository(database);
                callback.onManagerReady();
            }
        }.execute();
    }

    public CryptocoinRepository getCryptocoinRepository() {
        return cryptocoinRepository;
    }
}
