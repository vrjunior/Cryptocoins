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


        JSONObject JSONObj;
        Cryptocoin currentCryptocoin;
        Ticker currentTicker;
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObj = jsonArray.getJSONObject(i);

            //setting cryptocoin info
            currentCryptocoin = new Cryptocoin();
            if(!JSONObj.isNull("id")) {
                currentCryptocoin.setIdString(JSONObj.getString("id"));
            }
            if(!JSONObj.isNull("name")) {
                currentCryptocoin.setName(JSONObj.getString("name"));
            }
            if(!JSONObj.isNull("symbol")) {
                currentCryptocoin.setSymbol(JSONObj.getString("symbol"));
            }

            //setting ticker info
            currentTicker = new Ticker();

            currentTicker.setCryptocoin(currentCryptocoin);

            if(!JSONObj.isNull("rank")) {
                currentTicker.setRankPosition(JSONObj.getInt("rank"));
            }
            if(!JSONObj.isNull("price_usd")) {
                currentTicker.setPriceUsd(JSONObj.getDouble("price_usd"));
            }
            if(!JSONObj.isNull("price_btc")) {
                currentTicker.setPriceBtc(JSONObj.getDouble("price_btc"));
            }
            if(!JSONObj.isNull("last_updated")) {
                currentTicker.setLastUpdated(JSONObj.getLong("last_updated"));
            }
            if(!JSONObj.isNull("24h_volume_usd")) {
                currentTicker.setVolumeUsdLast24h(JSONObj.getDouble("24h_volume_usd"));
            }
            if(!JSONObj.isNull("market_cap_usd")) {
                currentTicker.setMarketCapUsd(JSONObj.getDouble("market_cap_usd"));
            }
            if(!JSONObj.isNull("available_supply")) {
                currentTicker.setAvailableSupply(JSONObj.getDouble("available_supply"));
            }
            if(!JSONObj.isNull("total_supply")) {
                currentTicker.setTotalSupply(JSONObj.getDouble("total_supply"));
            }
            if(!JSONObj.isNull("percent_change_1h")) {
                currentTicker.setPercentChange1h(JSONObj.getDouble("percent_change_1h"));
            }
            if(!JSONObj.isNull("percent_change_24h")) {
                currentTicker.setPercentChange24h(JSONObj.getDouble("percent_change_24h"));
            }
            if(!JSONObj.isNull("percent_change_7d")) {
                currentTicker.setPercentChange7d(JSONObj.getDouble("percent_change_7d"));
            }


            result.add(currentTicker);
        }
        return result;
    }

}
