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
import us.guihouse.criptocoins.models.Cryptocoin;
import us.guihouse.criptocoins.models.Ticker;
import us.guihouse.criptocoins.onRowClick;

/**
 * Created by valmir on 11/09/16.
 */
public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.CustomViewHolder> {

    private List<Ticker> tickerItens;
    private Context mContext;
    private onRowClick clickCallback;
    public static final String COINS_LOGOS_FOLDER = "coins_logos/";
    public static final String POS_FIX_IMG_LOGOS = ".png";

    public TickerAdapter(Context context, onRowClick clickCallback , List<Ticker> objects) {
        this.tickerItens = objects;
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
        Ticker ticker = tickerItens.get(position);
        Cryptocoin currentCryptocoin = ticker.getCryptocoin();

        InputStream ims;
        try {
            ims = this.mContext.getAssets().open(COINS_LOGOS_FOLDER + currentCryptocoin.getIdString() + POS_FIX_IMG_LOGOS);
            Drawable d = Drawable.createFromStream(ims, null);
            holder.ivCoinLogo.setImageDrawable(d);
        }catch (IOException e) {} //Find the image in a fullhd screen if you can! HU3 HU3 BR

        holder.tvCoinName.setText(currentCryptocoin.getName());
        holder.tvCoinSymbol.setText(currentCryptocoin.getSymbol());
        holder.tvPrice.setText(String.format("$%.6f", ticker.getPriceUsd()));
        holder.tvPercentChange24h.setText(String.format("%.2f%%", ticker.getPercentChange24h()));
        if(ticker.getPercentChange24h() >= 0) {
            holder.tvPercentChange24h.setTextColor(ContextCompat.getColor(mContext, R.color.green_text));
        }
        else {
            holder.tvPercentChange24h.setTextColor(ContextCompat.getColor(mContext, R.color.red_text)); // Como as views são recicladas, é necessário esse else!
        }
        if(currentCryptocoin.getIsFavorite()) {
            holder.cbFavorite.setChecked(true);
        }
        else {
            holder.cbFavorite.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return (null != tickerItens ? tickerItens.size() : 0); //ternária que devolve o tamanho se o arrayList não for nulo e zero se for
    }

    public void updateData(ArrayList<Ticker> newData) {
        tickerItens.clear();
        tickerItens.addAll(newData);
        notifyDataSetChanged();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener , View.OnClickListener{
        protected ImageView ivCoinLogo;
        protected TextView tvCoinName;
        protected TextView tvCoinSymbol;
        protected TextView tvPrice;
        protected TextView tvPercentChange24h;
        protected CheckBox cbFavorite;
        private onRowClick clickCallback;

        public CustomViewHolder(View v, onRowClick clickCallback) {
            super(v);
            ivCoinLogo = (ImageView) v.findViewById(R.id.ivCoinLogo);
            tvCoinName = (TextView) v.findViewById(R.id.tvCoinName);
            tvCoinSymbol = (TextView) v.findViewById(R.id.tvCoinSymbol);
            tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            tvPercentChange24h = (TextView) v.findViewById(R.id.tvPercentChange24h);
            cbFavorite = (CheckBox) v.findViewById(R.id.cbFavorite);
            this.clickCallback = clickCallback;

            v.setOnClickListener(this);
            cbFavorite.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                this.clickCallback.favoriteCryptocoin(tickerItens.get(this.getAdapterPosition()).getCryptocoin().getId());
                buttonView.setChecked(true);
            }
            else {
                this.clickCallback.unFavoriteCryptocoin(tickerItens.get(this.getAdapterPosition()).getCryptocoin().getId());
                buttonView.setChecked(false);
            }
        }

        @Override
        public void onClick(View v) {
            this.clickCallback.onRowClick(tickerItens.get(this.getAdapterPosition()).getCryptocoin().getId());
        }
    }
}
