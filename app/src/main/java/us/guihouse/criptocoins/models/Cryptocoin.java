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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
