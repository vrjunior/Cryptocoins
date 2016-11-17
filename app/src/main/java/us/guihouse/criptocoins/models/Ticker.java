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

    public void setCryptocoin(Cryptocoin cryptocoin) {
        this.cryptocoin = cryptocoin;
    }

    public void setRankPosition(int rankPosition) {
        this.rankPosition = rankPosition;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public void setPriceBtc(double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public void setVolumeUsdLast24h(double volumeUsdLast24h) {
        this.volumeUsdLast24h = volumeUsdLast24h;
    }

    public void setMarketCapUsd(double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public void setAvailableSupply(double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
