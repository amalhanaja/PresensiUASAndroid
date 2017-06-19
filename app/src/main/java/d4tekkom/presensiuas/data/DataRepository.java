package d4tekkom.presensiuas.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import d4tekkom.presensiuas.data.model.Jadwal;
import d4tekkom.presensiuas.data.model.Petugas;
import d4tekkom.presensiuas.data.pref.PrefHelper;
import io.reactivex.Observable;

/**
 * Created by doy on 18/06/17.
 */

@Singleton
public class DataRepository implements DataSource {

    private final DataSource mRemoteDataSource;
    private final PrefHelper mPref;

    @Inject
    public DataRepository(@RemoteQualifier DataSource remoteDataSource,
                          PrefHelper helper){
        mRemoteDataSource = remoteDataSource;
        mPref = helper;
    }

    @Override
    public Observable<Petugas> loginPetugas(String nip, String password) {
        if (mPref.isLoggedIn()){
            Petugas petugas = new Petugas();
            petugas.setIdPetugas(mPref.getIdPetugas());
            petugas.setNama(mPref.getNama());
            petugas.setNip(mPref.getNip());
            return Observable.just(petugas);
        }
        return mRemoteDataSource.loginPetugas(nip, password);
    }

    public boolean isPetugasLoggedIn(){
        return mPref.isLoggedIn();
    }

    public void createLoginSession(Petugas petugas){
        mPref.CreateLoginSession(petugas);
    }

    @Override
    public Observable<List<Jadwal>> getJadwalList() {
        return mRemoteDataSource.getJadwalList();
    }

    @Override
    public Observable<String> updateAbsen(String rfid) {
        return mRemoteDataSource.updateAbsen(rfid);
    }
}
