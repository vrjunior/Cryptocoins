package us.guihouse.criptocoins.coinmarketcap_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by valmir on 10/09/16.
 */
public class CryptoCoinParser {
    private JSONArray jsonArray;

    public CryptoCoinParser(String rawData) throws JSONException {
        jsonArray = new JSONArray(rawData);
    }

    public ArrayList<CryptoCoin> getCryptoCoinArrayList() throws JSONException {
        ArrayList<CryptoCoin> result = new ArrayList<>();
        CryptoCoin currentCryptoCoin;
        JSONObject JSONObj;

        Double volumeUsd24hours; //Eu sei que isso é porco, mas depois a gente optimiza
        Double marketCapUsd;
        Double availableSupply;
        Double totalSupply;
        Double percentChange1h;
        Double percentChange24h;
        Double percentChange7d;

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObj = jsonArray.getJSONObject(i);

            volumeUsd24hours = 0.0;
            marketCapUsd = 0.0;
            availableSupply = 0.0;
            totalSupply = 0.0;
            percentChange1h = 0.0;
            percentChange24h = 0.0;
            percentChange7d = 0.0;

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

            currentCryptoCoin = new CryptoCoin( JSONObj.getString("id"),
                    JSONObj.getString("name"),
                    JSONObj.getString("symbol"),
                    JSONObj.getInt("rank"),
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

            result.add(currentCryptoCoin);
        }
        return result;
    }

}
