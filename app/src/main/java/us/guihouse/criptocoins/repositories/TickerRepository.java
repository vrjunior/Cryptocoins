package us.guihouse.criptocoins.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.Cryptocoin;
import us.guihouse.criptocoins.models.Ticker;

/**
 * Created by aluno on 14/09/16.
 */
public class TickerRepository {
    private SQLiteDatabase database;
    public static final int FAVORITE_TRUE = 1;
    public static final int FAVORITE_FALSE = 0;
    public static final String CRYPTOCOINS_TABLE = "cryptocoins";
    public static final String TICKERS_TABLE = "tickers";

    public TickerRepository(SQLiteDatabase database) {
        this.database = database;
    }

    private void insertCryptocoinIfNotExists(Cryptocoin cc) {
        ContentValues content = new ContentValues();

        content.put("id_string", cc.getIdString());
        content.put("name", cc.getName());
        content.put("symbol", cc.getSymbol());

        database.insertWithOnConflict(CRYPTOCOINS_TABLE, null, content, SQLiteDatabase.CONFLICT_IGNORE);
        content.clear();
    }

    private Integer selectIdCryptocoinByIdString(String idString){
        Integer id = null;
        StringBuilder sql = new StringBuilder();
        String[] params = {idString};
        sql.append("SELECT id ")
                .append("FROM cryptocoins ")
                .append("WHERE id_string = ?");

        Cursor cursor = this.database.rawQuery(sql.toString(), params);
        if(cursor != null && cursor.moveToFirst()) {
            id =  new Integer(cursor.getInt(0));
        }
        return id;
    }

    public void insertOrUpdateTicker(ArrayList<Ticker> tickers) {
        ContentValues content = new ContentValues();
        Integer id;
        database.beginTransaction();
        try {
            for (Ticker t : tickers) {

                this.insertCryptocoinIfNotExists(t.getCryptocoin());

                id = this.selectIdCryptocoinByIdString(t.getCryptocoin().getIdString());

                content.put("id_cryptocoin", id);
                content.put("rank", t.getRankPosition());
                content.put("price_usd", t.getPriceUsd());
                content.put("price_btc", t.getPriceBtc());
                content.put("volume_usd_24h", t.getVolumeUsdLast24h());
                content.put("market_cap_usd", t.getMarketCapUsd());
                content.put("available_supply", t.getAvailableSupply());
                content.put("total_supply", t.getTotalSupply());
                content.put("percent_change_1h", t.getPercentChange1h());
                content.put("percent_change_24h", t.getPercentChange24h());
                content.put("percent_change_7d", t.getPercentChange7d());
                content.put("last_update_timestamp", t.getLastUpdated());

                database.insertWithOnConflict(TICKERS_TABLE, null, content, SQLiteDatabase.CONFLICT_REPLACE);
                content.clear();
            }
            
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public ArrayList<Ticker> getAllTickers() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT rank, price_usd, price_btc, volume_usd_24h, ")
                .append("market_cap_usd, available_supply, total_supply, percent_change_1h, percent_change_24h, percent_change_7d, ")
                .append("last_update_timestamp, cryptocoins.id, cryptocoins.id_string, cryptocoins.name, cryptocoins.symbol, ")
                .append("cryptocoins.favorite ")
                .append("FROM tickers ")
                .append("INNER JOIN cryptocoins ")
                .append("ON tickers.id_cryptocoin = cryptocoins.id ")
                .append("ORDER BY rank ");

        Cursor cursor = database.rawQuery(sql.toString(), null);

        ArrayList<Ticker> result = new ArrayList<>();
        Cryptocoin cc;
        Ticker currentTicker;
        boolean currentFavorite;
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                Log.d("select", "comecou");
                do {
                    currentFavorite = cursor.getInt(15) == 0 ? false : true;
                    cc = new Cryptocoin(cursor.getInt(11),cursor.getString(12), cursor.getString(13), cursor.getString(14));
                    cc.setIsFavorite(currentFavorite);

                    currentTicker = new Ticker(cc , cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2),
                            cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5),
                            cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8),
                            cursor.getDouble(9), cursor.getLong(10));

                    result.add(currentTicker);
                } while (cursor.moveToNext());
                Log.d("select", "terminou");
            }
        }
        cursor.close();
        return result;
    }

    public Ticker getTickerByCryptocoinId(Integer id) {
        Ticker result = null;
        Cryptocoin cc;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT rank, price_usd, price_btc, volume_usd_24h, ")
                .append("market_cap_usd, available_supply, total_supply, percent_change_1h, percent_change_24h, percent_change_7d, ")
                .append("last_update_timestamp, cryptocoins.id, cryptocoins.id_string, cryptocoins.name, cryptocoins.symbol, ")
                .append("cryptocoins.favorite ")
                .append("FROM tickers ")
                .append("INNER JOIN cryptocoins ")
                .append("ON tickers.id_cryptocoin = cryptocoins.id ")
                .append("WHERE cryptocoins.id = " + id.intValue());

        Cursor cursor = this.database.rawQuery(sql.toString(), null);

        boolean currentFavorite;
        if(cursor != null && cursor.moveToFirst()) {
            currentFavorite = cursor.getInt(15) == 0 ? false : true;
            cc = new Cryptocoin(cursor.getInt(11),cursor.getString(12), cursor.getString(13), cursor.getString(14));
            cc.setIsFavorite(currentFavorite);

            result = new Ticker(cc , cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2),
                    cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5),
                    cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8),
                    cursor.getDouble(9), cursor.getLong(10));

        }
        cursor.close();
        return result;
    }

    public boolean favoriteACryptocoin(Integer id) {
        if(id == null) {
            return false;
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("id = ").append(id.intValue());
        ContentValues content = new ContentValues();
        content.put("favorite", FAVORITE_TRUE);
        this.database.update(CRYPTOCOINS_TABLE, content, whereClause.toString(), null);
        return true;
    }

    public boolean unFavoriteACryptocoin(Integer id) {
        ContentValues content = new ContentValues();
        if(id == null) {
            return false;
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("id = ").append(id.toString());
        content.clear();
        content.put("favorite", FAVORITE_FALSE);
        this.database.update(CRYPTOCOINS_TABLE, content, whereClause.toString(), null);
        return true;
    }

}
