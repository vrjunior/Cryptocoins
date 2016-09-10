package us.guihouse.criptocoins.coinmarketcap_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import us.guihouse.criptocoins.CryptoCoin;

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
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObj = jsonArray.getJSONObject(i);
            currentCryptoCoin = new CryptoCoin( JSONObj.getInt("id"),
                    JSONObj.getString("name"),
                    JSONObj.getString("symbol"),
                    JSONObj.getInt("rank"),
                    JSONObj.getDouble("price_usd"),
                    JSONObj.getDouble("price_btc"),
                    JSONObj.getDouble("24h_volume_usd"),
                    JSONObj.getDouble("market_cap_usd"),
                    JSONObj.getDouble("available_supply"),
                    JSONObj.getDouble("total_supply"),
                    JSONObj.getDouble("percent_change_1h"),
                    JSONObj.getDouble("percent_change_24h"),
                    JSONObj.getDouble("percent_change_7d"),
                    JSONObj.getLong("last_updated") );

            result.add(currentCryptoCoin);
        }
        return result;
    }

}
