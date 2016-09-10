package us.guihouse.criptocoins;

/**
 * Created by valmir on 10/09/16.
 */
public class CryptoCoin {
    private int id;
    private String name;
    private String symbol;
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
    private long last_updated; //DateTime?

    public CryptoCoin(int id, String name, String symbol, int rankPosition, double priceUsd, double priceBtc,
                        double volumeUsdLast24h, double marketCapUsd, double availableSupply, double totalSupply,
                        double percentChange1h, double percentChange24h, double percentChange7d, long last_updated) {

        this.id = id;
        this.name = name;
        this.symbol = symbol;
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
        this.last_updated = last_updated;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
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

    public long getLast_updated() {
        return last_updated;
    }
}
