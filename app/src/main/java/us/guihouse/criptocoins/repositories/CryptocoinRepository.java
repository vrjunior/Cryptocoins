package us.guihouse.criptocoins.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by aluno on 14/09/16.
 */
public class CryptocoinRepository {
    private SQLiteDatabase database;

    public CryptocoinRepository(SQLiteDatabase database) {
        this.database = database;
    }

    public void insertAllCryptocoins(ArrayList<CryptoCoin> ccs) {
        ContentValues content = new ContentValues();

        for (CryptoCoin cc: ccs) {
            content.put("idString", cc.getId());
            content.put("name", cc.getName());
            content.put("symbol", cc.getSymbol());
            content.put("rank", cc.getRankPosition());
            content.put("price_usd", cc.getPriceUsd());
            content.put("price_btc", cc.getPriceBtc());
            content.put("volume_usd_24h", cc.getVolumeUsdLast24h());
            content.put("market_cap_usd", cc.getMarketCapUsd());
            content.put("available_supply", cc.getAvailableSupply());
            content.put("percent_change_1h", cc.getPercentChange1h());
            content.put("percent_change_24h", cc.getPercentChange24h());
            content.put("percent_change_7d", cc.getPercentChange7d());
            content.put("last_update_timestamp", cc.getLast_updated());

            database.insert("cryptocoins", null, content);
            content.clear();
        }
    }

    public ArrayList<CryptoCoin> getCryptocoins(int offSet, int limit) {
        ArrayList<CryptoCoin> result= new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idString, name, symbol, rank, price_usd, price_btc, volume_usd_24h, " +
                "market_cap_usd, available_supply, percent_change_1h, percent_change_24h, percent_change_7d, " +
                "last_update_timestamp");
        sql.append("FROM cryptocoins ");
        sql.append("LIMIT " + limit + ", " + offSet);

        Cursor cursor = database.rawQuery(sql.toString(), null);
        CryptoCoin cc;
        while(cursor.moveToNext()) {
            cc = new CryptoCoin(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                    cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8),
                    cursor.getDouble(9), cursor.getDouble(10), cursor.getDouble(11), cursor.getDouble(12), cursor.getLong(13));

            result.add(cc);
        }

        return result;
    }

}
