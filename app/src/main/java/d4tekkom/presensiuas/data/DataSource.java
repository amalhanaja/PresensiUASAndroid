package d4tekkom.presensiuas.data;

import java.util.List;

import d4tekkom.presensiuas.data.model.Jadwal;
import d4tekkom.presensiuas.data.model.Petugas;
import io.reactivex.Observable;

/**
 * Created by doy on 18/06/17.
 */

public interface DataSource {
    Observable<Petugas> loginPetugas(String nip, String password);

    Observable<List<Jadwal>> getJadwalList();

    Observable<String> updateAbsen(String rfid);
}