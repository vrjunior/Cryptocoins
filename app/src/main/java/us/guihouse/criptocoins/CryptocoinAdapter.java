package us.guihouse.criptocoins;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import us.guihouse.criptocoins.models.CryptoCoin;

/**
 * Created by valmir on 11/09/16.
 */
public class CryptocoinAdapter extends RecyclerView.Adapter<CryptocoinAdapter.CustomViewHolder> {

    private List<CryptoCoin> cryptocoinItens;
    private Context mContext;
    public static final String COINS_LOGOS_FOLDER = "coins_logos/";
    public static final String POS_FIX_IMG_LOGOS = ".png";

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

        InputStream ims;
        try {
            ims = this.mContext.getAssets().open(COINS_LOGOS_FOLDER + cryptoCoin.getId() + POS_FIX_IMG_LOGOS);
            Drawable d = Drawable.createFromStream(ims, null);
            holder.ivCoinLogo.setImageDrawable(d);
        }catch (IOException e) {} //Find the image in a fullhd screen if you can! HU3 HU3 BR

        holder.tvCoinName.setText(cryptoCoin.getName());
        holder.tvCoinSymbol.setText(cryptoCoin.getSymbol());
        holder.tvPrice.setText(String.format("$%.2f", cryptoCoin.getPriceUsd()));
        holder.tvPercentChange24h.setText(String.format("%.2f %%", cryptoCoin.getPercentChange24h()));
        if(cryptoCoin.getPercentChange24h() >= 0) {
            holder.tvPercentChange24h.setTextColor(ContextCompat.getColor(mContext, R.color.green_text));
        }
        else {
            holder.tvPercentChange24h.setTextColor(ContextCompat.getColor(mContext, R.color.red_text)); // Como as views são recicladas, é necessário esse else!
        }

    }

    @Override
    public int getItemCount() {
        return (null != cryptocoinItens ? cryptocoinItens.size() : 0); //ternária que devolve o tamanho se o arrayList não for nulo e zero se for
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
