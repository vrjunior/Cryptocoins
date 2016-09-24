package us.guihouse.criptocoins.content_providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import us.guihouse.criptocoins.repositories.CryptocoinRepository;

/**
 * Created by aluno on 16/09/16.
 */
/*public class CryptocoinsProvider extends ContentProvider {
    private CryptocoinRepository repository;

    public CryptocoinsProvider(CryptocoinRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        //return repository.getCryptocoins();
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}*/
