package us.guihouse.criptocoins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by valmir on 11/09/16.
 */
public class CryptocoinAdapter extends ArrayAdapter<CryptoCoin> {

    public CryptocoinAdapter(Context context, int resource, List<CryptoCoin> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.cryptocoin_row_item, null);
        }

        CryptoCoin cryptoCoin = getItem(position);

        if(cryptoCoin != null) {
            TextView tvCoinName = (TextView) v.findViewById(R.id.tvCoinName);
            TextView tvCoinSymbol = (TextView) v.findViewById(R.id.tvCoinSymbol);
            TextView tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            TextView tvVolume24h = (TextView) v.findViewById(R.id.tvVolume24h);
            TextView tvPercentChange24h = (TextView) v.findViewById(R.id.tvPercentChange24h);

            if(tvCoinName != null) {
                tvCoinName.setText(cryptoCoin.getName());
            }
            if(tvCoinSymbol != null) {
                tvCoinSymbol.setText(cryptoCoin.getSymbol());
            }
            if(tvPrice != null) {
                tvPrice.setText(String.format("$ %.2f", cryptoCoin.getPriceUsd()));
            }
            if(tvVolume24h != null) {
                tvVolume24h.setText(String.format("$ %.2f", cryptoCoin.getVolumeUsdLast24h()));
            }
            if(tvPercentChange24h != null) {
                tvPercentChange24h.setText(String.format("%.2f %%", cryptoCoin.getVolumeUsdLast24h()));
            }
        }

        return v;
    }
}
