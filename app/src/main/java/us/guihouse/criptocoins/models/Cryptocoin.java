package us.guihouse.criptocoins.models;

/**
 * Created by valmir on 10/09/16.
 */
public class Cryptocoin {
    private Integer id;
    private String idString;
    private String name;
    private String symbol;
    private boolean isFavorite;

    public Cryptocoin(Integer id, String idString, String name, String symbol) {
        this.id = id;
        this.idString = idString;
        this.name = name;
        this.symbol = symbol;
    }

    public Integer getId() {
        return this.id;
    }

    public String getIdString() {
        return this.idString;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
