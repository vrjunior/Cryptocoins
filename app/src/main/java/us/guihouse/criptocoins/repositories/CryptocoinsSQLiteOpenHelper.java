package us.guihouse.criptocoins.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aluno on 13/09/16.
 */

public class CryptocoinsSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATA_BASE_NAME = "dataBaseCryptocoin";
    public static final int DATA_BASE_VERSION = 1;

    public CryptocoinsSQLiteOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {

        super(context, DATA_BASE_NAME, factory, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.getDataBaseUsersSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String getDataBaseUsersSQL() {
        StringBuilder sqlString = new StringBuilder();

        sqlString.append("CREATE TABLE cryptocoins (");
        sqlString.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sqlString.append("id_string CHAR(100) NOT NULL UNIQUE,");
        sqlString.append("name CHAR(360) NOT NULL,");
        sqlString.append("symbol CHAR(16) NOT NULL,");
        sqlString.append("rank INTEGER NOT NULL,");
        sqlString.append("price_usd REAL NOT NULL,");
        sqlString.append("price_btc REAL NOT NULL,");
        sqlString.append("volume_usd_24h REAL NOT NULL,");
        sqlString.append("market_cap_usd REAL NOT NULL,");
        sqlString.append("available_supply REAL NOT NULL,");
        sqlString.append("total_supply REAL NOT NULL,");
        sqlString.append("percent_change_1h REAL NOT NULL,");
        sqlString.append("percent_change_24h REAL NOT NULL,");
        sqlString.append("percent_change_7d REAL NOT NULL,");
        sqlString.append("last_update_timestamp BIGINT NOT NULL);");

        sqlString.append("CREATE INDEX rank_index ON cryptocoins(rank);");

        sqlString.append("CREATE TABLE favorite_cryptocoins (");
        sqlString.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sqlString.append("id_cryptocoin INT NOT NULL,");
        sqlString.append("FOREING KEY(id_cryptocoin) REFERENCES cryptocoins(id) );");

        return sqlString.toString();
    }
}
