package us.guihouse.criptocoins.repositories;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aluno on 14/09/16.
 */
public class CryptocoinRepository {
    private SQLiteDatabase database;

    public CryptocoinRepository(SQLiteDatabase database) {
        this.database = database;
    }
}
