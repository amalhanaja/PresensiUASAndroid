package d4tekkom.presensiuas.data.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;

import javax.inject.Inject;

import d4tekkom.presensiuas.ApplicationContext;
import d4tekkom.presensiuas.data.PrefQualifier;
import d4tekkom.presensiuas.data.model.Petugas;

/**
 * Created by doy on 19/06/17.
 */

public class AppPref implements PrefHelper {

    public static final String PREF_KEY_ID = "ID";
    public static final String PREF_KEY_NAMA = "NAMA";
    public static final String PREF_KEY_NIP = "NIP";
    public static final String PREF_KEY_IS_LOGIN = "IS_LOGIN";

    private final SharedPreferences mPref;

    @Inject
    public AppPref(@ApplicationContext Context context,
                   @PrefQualifier String prefName) {
        mPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public void CreateLoginSession(Petugas petugas) {
        setIdPetugas(petugas.getIdPetugas());
        setNama(petugas.getNama());
        setNip(petugas.getNip());
        mPref.edit().putBoolean(PREF_KEY_IS_LOGIN, true).apply();
    }

    @Override
    public boolean isLoggedIn() {
        return mPref.getBoolean(PREF_KEY_IS_LOGIN, false);
    }

    @Override
    public void setNama(String nama) {
        mPref.edit().putString(PREF_KEY_NAMA, nama).apply();
    }

    @Override
    public String getNama() {
        return mPref.getString(PREF_KEY_NAMA, "");
    }

    @Override
    public void setNip(int nip) {
        mPref.edit().putInt(PREF_KEY_NIP, nip).apply();
    }

    @Override
    public int getNip() {
        return mPref.getInt(PREF_KEY_NIP, 0);
    }

    @Override
    public void setIdPetugas(int idPetugas) {
        mPref.edit().putInt(PREF_KEY_ID, idPetugas).apply();
    }

    @Override
    public int getIdPetugas() {
        return mPref.getInt(PREF_KEY_ID, 0);
    }
}
