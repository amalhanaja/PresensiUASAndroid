package d4tekkom.presensiuas.data.pref;

import d4tekkom.presensiuas.data.model.Petugas;

/**
 * Created by doy on 19/06/17.
 */

public interface PrefHelper {
    void CreateLoginSession(Petugas petugas);

    boolean isLoggedIn();

    public void setNama(String nama);

    public String getNama();

    public void setNip(int nip);

    public int getNip();

    public void setIdPetugas(int idPetugas);

    public int getIdPetugas();

}
