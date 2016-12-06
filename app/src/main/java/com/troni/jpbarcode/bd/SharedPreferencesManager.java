package com.troni.jpbarcode.bd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PMateus on 16/09/2015.
 * For project SIG@Viewer.
 * Contact: <paulomatew@gmail.com>
 */
public class SharedPreferencesManager {
    /*
    http://randomkeygen.com/
    TODO trocar keys a cada versão do app
     */
    private final String UNIQ_KEY = "p_I9yQ4L";

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared pref mode
    public final int PRIVATE_MODE_SHARED_PREF = 0;

    // Shared preferences file name
    public final String PREF_NAME = UNIQ_KEY + "JPBoard";

    /*KEYS para o sharedpreferences*/
    private final String KEY_IP = PREF_NAME + "ip";
    private final String KEY_KEY = PREF_NAME + "key";

    public SharedPreferencesManager(Context context) {
        this._context = context;

        removerBdVersoesAnteriores();

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE_SHARED_PREF);
        editor = pref.edit();
    }

    public void removerBdVersoesAnteriores() {
        /*final String bd1 = "s@ap6j?nu7ra3as&ASuB5*#4PruSig@Viewer";


        if (PREF_NAME.equals(bd1)) {
            _context.getSharedPreferences(bd1, 0).edit().clear().commit();
        }*/
    }

    public void close() {
        editor = null;
        pref = null;
    }

    public void setIp(String keyIp) {
        editor.putString(KEY_IP, keyIp);
        editor.commit();
    }

    public String getIp() {
        return pref.getString(KEY_IP, "");
    }

    public void setKey(String key) {
        editor.putString(KEY_KEY, key);
        editor.commit();
    }

    public String getKey() {
        return pref.getString(KEY_KEY, "");
    }
}
