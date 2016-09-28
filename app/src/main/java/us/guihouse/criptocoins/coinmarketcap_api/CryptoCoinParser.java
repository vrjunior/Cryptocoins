package us.guihouse.criptocoins.coinmarketcap_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.Cryptocoin;
import us.guihouse.criptocoins.models.Ticker;

/**
 * Created by valmir on 10/09/16.
 */
public class CryptoCoinParser {
    private JSONArray jsonArray;

    public CryptoCoinParser(String rawData) throws JSONException {
        jsonArray = new JSONArray(rawData);
    }

    public ArrayList<Ticker> getTickerArrayList() throws JSONException {
        ArrayList<Ticker> result = new ArrayList<>();
        Cryptocoin currentCryptocoin;
        Ticker currentTicker;
        JSONObject JSONObj;

        Double volumeUsd24hours; //Eu sei que isso é porco, mas depois a gente optimiza
        Double marketCapUsd;
        Double availableSupply;
        Double totalSupply;
        Double percentChange1h;
        Double percentChange24h;
        Double percentChange7d;

        volumeUsd24hours = 0.0;
        marketCapUsd = 0.0;
        availableSupply = 0.0;
        totalSupply = 0.0;
        percentChange1h = 0.0;
        percentChange24h = 0.0;
        percentChange7d = 0.0;


        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObj = jsonArray.getJSONObject(i);

            /*Iterator keys = JSONObj.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if(JSONObj.isNull(key)) {
                     = JSONObj.getDouble(key);
                }
            }*/

            if(!JSONObj.isNull("24h_volume_usd")) {
                volumeUsd24hours = JSONObj.getDouble("24h_volume_usd");
            }
            if(!JSONObj.isNull("market_cap_usd")) {
                marketCapUsd = JSONObj.getDouble("market_cap_usd");
            }
            if(!JSONObj.isNull("available_supply")) {
                availableSupply = JSONObj.getDouble("available_supply");
            }
            if(!JSONObj.isNull("total_supply")) {
                totalSupply = JSONObj.getDouble("total_supply");
            }
            if(!JSONObj.isNull("percent_change_1h")) {
                percentChange1h = JSONObj.getDouble("percent_change_1h");
            }
            if(!JSONObj.isNull("percent_change_24h")) {
                percentChange24h = JSONObj.getDouble("percent_change_24h");
            }
            if(!JSONObj.isNull("percent_change_7d")) {
                percentChange7d = JSONObj.getDouble("percent_change_7d");
            }


            currentCryptocoin = new Cryptocoin( null, JSONObj.getString("id"), JSONObj.getString("name"),
                    JSONObj.getString("symbol"));

            currentTicker = new Ticker(currentCryptocoin, JSONObj.getInt("rank"),
                    JSONObj.getDouble("price_usd"),
                    JSONObj.getDouble("price_btc"),
                    volumeUsd24hours,
                    marketCapUsd,
                    availableSupply,
                    totalSupply,
                    percentChange1h,
                    percentChange24h,
                    percentChange7d,
                    JSONObj.getLong("last_updated") );

            result.add(currentTicker);
        }
        return result;
    }

}
