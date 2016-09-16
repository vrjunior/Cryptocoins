package us.guihouse.criptocoins.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.widget.SimpleCursorAdapter;

/**
 * Created by aluno on 16/09/16.
 */
public class CriptocoinsDatabaseAdapter extends CursorLoader {


    public CriptocoinsDatabaseAdapter(Context context) {
        super(context);
    }


}
