package d4tekkom.presensiuas.data.remote;

import com.orhanobut.logger.Logger;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import d4tekkom.presensiuas.data.DataSource;
import d4tekkom.presensiuas.data.model.Jadwal;
import d4tekkom.presensiuas.data.model.Petugas;
import d4tekkom.presensiuas.utility.NetworkClient;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Credentials;

/**
 * Created by doy on 18/06/17.
 */

@Singleton
public class RemoteDataSource implements DataSource {

    @Inject
    public RemoteDataSource() {
    }

    @Override
    public Observable<Petugas> loginPetugas(String nip, String password) {
        String url = RemoteEndPoint.LOGIN_ENDPOINT();
        return Rx2AndroidNetworking.get(url)
                .addHeaders("authorization", Credentials.basic(nip, password))
                .build()
                .getStringObservable()
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(@NonNull Throwable throwable) throws Exception {
                        Logger.e(throwable, throwable.getMessage());
                        return null;
                    }
                })
                .map(new Function<String, Petugas>() {
                    @Override
                    public Petugas apply(@NonNull String s) throws Exception {
                        if (s == null){
                            return null;
                        }
                        Logger.json(s);
                        return Petugas.objectFromString(s);
                    }
                });
    }

    @Override
    public Observable<List<Jadwal>> getJadwalList() {
        String url = RemoteEndPoint.JADWAL_ENDPOINT();

        return Rx2AndroidNetworking.get(url)
                .setOkHttpClient(NetworkClient.timeOutClient())
                .build()
                .getStringObservable()
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(@NonNull Throwable throwable) throws Exception {
                        Logger.e(throwable, throwable.getMessage());
                        return null;
                    }
                })
                .map(new Function<String, List<Jadwal>>() {
                    @Override
                    public List<Jadwal> apply(@NonNull String s) throws Exception {
                        if (s == null){
                            return null;
                        }
                        Logger.json(s);
                        return Jadwal.arrayJadwalFromData(s);
                    }
                });

    }

    @Override
    public Observable<String> updateAbsen(String rfid) {
        String url = RemoteEndPoint.JADWAL_ENDPOINT(rfid);

        return Rx2AndroidNetworking.get(url)
                .build()
                .getStringObservable();
    }
}
