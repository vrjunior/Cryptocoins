package us.guihouse.criptocoins.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import us.guihouse.criptocoins.R;
import us.guihouse.criptocoins.models.CryptoCoin;
import us.guihouse.criptocoins.onFavoriteClick;

/**
 * Created by valmir on 11/09/16.
 */
public class CryptocoinAdapter extends RecyclerView.Adapter<CryptocoinAdapter.CustomViewHolder> {

    private List<CryptoCoin> cryptocoinItens;
    private Context mContext;
    private onFavoriteClick clickCallback;
    public static final String COINS_LOGOS_FOLDER = "coins_logos/";
    public static final String POS_FIX_IMG_LOGOS = ".png";

    public CryptocoinAdapter(Context context, onFavoriteClick clickCallback , List<CryptoCoin> objects) {
        this.cryptocoinItens = objects;
        this.mContext = context;
        this.clickCallback = clickCallback;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocoin_row_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view, clickCallback);

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
        holder.tvPrice.setText(String.format("$%.6f", cryptoCoin.getPriceUsd()));
        holder.tvPercentChange24h.setText(String.format("%.2f%%", cryptoCoin.getPercentChange24h()));
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

    public void updateData(ArrayList<CryptoCoin> newData) {
        cryptocoinItens.clear();
        cryptocoinItens.addAll(newData);
        notifyDataSetChanged();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener {
        protected ImageView ivCoinLogo;
        protected TextView tvCoinName;
        protected TextView tvCoinSymbol;
        protected TextView tvPrice;
        protected TextView tvPercentChange24h;
        protected CheckBox cbFavorite;
        private onFavoriteClick clickCallback;

        public CustomViewHolder(View v, onFavoriteClick clickCallback) {
            super(v);
            ivCoinLogo = (ImageView) v.findViewById(R.id.ivCoinLogo);
            tvCoinName = (TextView) v.findViewById(R.id.tvCoinName);
            tvCoinSymbol = (TextView) v.findViewById(R.id.tvCoinSymbol);
            tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            tvPercentChange24h = (TextView) v.findViewById(R.id.tvPercentChange24h);
            cbFavorite = (CheckBox) v.findViewById(R.id.cbFavorite);
            this.clickCallback = clickCallback;
            cbFavorite.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                this.clickCallback.favoriteCryptocoin(cryptocoinItens.get(this.getAdapterPosition()).getId());
                buttonView.setChecked(true);
            }
            else {
                this.clickCallback.unFavoriteCryptocoin(cryptocoinItens.get(this.getAdapterPosition()).getId());
                buttonView.setChecked(false);
            }
        }
    }
}
