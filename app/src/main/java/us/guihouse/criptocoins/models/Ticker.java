package us.guihouse.criptocoins.models;

/**
 * Created by valmir.massoni on 28/09/2016.
 */

public class Ticker {
    private Cryptocoin cryptocoin;
    private int rankPosition;
    private double priceUsd;
    private double priceBtc;
    private double volumeUsdLast24h;
    private double marketCapUsd;
    private double availableSupply;
    private double totalSupply;
    private double percentChange1h;
    private double percentChange24h;
    private double percentChange7d;
    private long lastUpdated;

    public Ticker(Cryptocoin cryptocoin, int rankPosition, double priceUsd, double priceBtc,
                      double volumeUsdLast24h, double marketCapUsd, double availableSupply, double totalSupply,
                      double percentChange1h, double percentChange24h, double percentChange7d, long last_updated) {

        this.cryptocoin = cryptocoin;
        this.rankPosition = rankPosition;
        this.priceUsd = priceUsd;
        this.priceBtc = priceBtc;
        this.volumeUsdLast24h = volumeUsdLast24h;
        this.marketCapUsd = marketCapUsd;
        this.availableSupply = availableSupply;
        this.totalSupply = totalSupply;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
        this.lastUpdated = last_updated;
    }

    public Cryptocoin getCryptocoin() {
        return this.cryptocoin;
    }

    public int getRankPosition() {
        return rankPosition;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public double getPriceBtc() {
        return priceBtc;
    }

    public double getVolumeUsdLast24h() {
        return volumeUsdLast24h;
    }

    public double getMarketCapUsd() {
        return marketCapUsd;
    }

    public double getAvailableSupply() {
        return availableSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }
}
