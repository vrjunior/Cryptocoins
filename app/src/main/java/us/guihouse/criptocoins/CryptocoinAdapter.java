package us.guihouse.criptocoins;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by valmir on 11/09/16.
 */
public class CryptocoinAdapter extends RecyclerView.Adapter<CryptocoinAdapter.CustomViewHolder> {

    private List<CryptoCoin> cryptocoinItens;
    private Context mContext;

    public CryptocoinAdapter(Context context, int resource, List<CryptoCoin> objects) {
        this.cryptocoinItens = objects;
        this.mContext = context;
    }
    
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocoin_row_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        CryptoCoin cryptoCoin = cryptocoinItens.get(position);

        if(cryptoCoin != null) {


        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivCoinLogo;
        protected TextView tvCoinName;
        protected TextView tvCoinSymbol;
        protected TextView tvPrice;
        protected TextView tvPercentChange24h;

        public CustomViewHolder(View v) {
            super(v);
            ivCoinLogo = (ImageView) v.findViewById(R.id.ivCoinLogo);
            tvCoinName = (TextView) v.findViewById(R.id.tvCoinName);
            tvCoinSymbol = (TextView) v.findViewById(R.id.tvCoinSymbol);
            tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            tvPercentChange24h = (TextView) v.findViewById(R.id.tvPercentChange24h);
        }
    }
}
