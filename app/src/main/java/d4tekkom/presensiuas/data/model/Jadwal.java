package d4tekkom.presensiuas.data.model;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by doy on 19/06/17.
 */

public class Jadwal {
    /**
     * id_jadwal : 131
     * lokasi_ruangan : GEDUNG D4
     * nama_ruangan : A301
     * pengawas : [{"absen":0,"nama":"Nizam","nip":2110141057,"rfid":"4972198044128","status":"dosen"},{"absen":0,"nama":"Meilani","nip":2110141036,"rfid":"4685769","status":"dosen"}]
     * tipe_ruangan : kelas
     * waktu_akhir : 2017-06-25 06:15:00
     * waktu_awal : 2017-06-19 13:23:10
     */

    private int id_jadwal;
    private String lokasi_ruangan;
    private String nama_ruangan;
    private String tipe_ruangan;
    private String waktu_akhir;
    private String waktu_awal;
    private List<PengawasBean> pengawas;

//    public static Jadwal objectFromData(String str) {
//
//        return JSON.parseObject(str, Jadwal.class);
//
//    }
//
//    public static List<Jadwal> arrayJadwalFromData(String str) {
//        return JSON.parseArray(str, Jadwal.class);
//    }

    public static Jadwal objectFromData(String str) {

        return JSON.parseObject(str, Jadwal.class);

    }

    public static List<Jadwal> arrayJadwalFromData(String str) {
        return JSON.parseArray(str, Jadwal.class);
    }

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getLokasi_ruangan() {
        return lokasi_ruangan;
    }

    public void setLokasi_ruangan(String lokasi_ruangan) {
        this.lokasi_ruangan = lokasi_ruangan;
    }

    public String getNama_ruangan() {
        return nama_ruangan;
    }

    public void setNama_ruangan(String nama_ruangan) {
        this.nama_ruangan = nama_ruangan;
    }

    public String getTipe_ruangan() {
        return tipe_ruangan;
    }

    public void setTipe_ruangan(String tipe_ruangan) {
        this.tipe_ruangan = tipe_ruangan;
    }

    public String getWaktu_akhir() {
        return waktu_akhir;
    }

    public void setWaktu_akhir(String waktu_akhir) {
        this.waktu_akhir = waktu_akhir;
    }

    public String getWaktu_awal() {
        return waktu_awal;
    }

    public void setWaktu_awal(String waktu_awal) {
        this.waktu_awal = waktu_awal;
    }

    public List<PengawasBean> getPengawas() {
        return pengawas;
    }

    public void setPengawas(List<PengawasBean> pengawas) {
        this.pengawas = pengawas;
    }

    public static class PengawasBean {
        /**
         * absen : 0
         * nama : Nizam
         * nip : 2110141057
         * rfid : 4972198044128
         * status : dosen
         */

        private int absen;
        private String nama;
        private int nip;
        private String rfid;
        private String status;

        public static PengawasBean objectFromData(String str) {

            return JSON.parseObject(str, PengawasBean.class);

        }

        public static List<PengawasBean> arrayPengawasBeanFromData(String str) {
            return JSON.parseArray(str, PengawasBean.class);
        }

        public int getAbsen() {
            return absen;
        }

        public void setAbsen(int absen) {
            this.absen = absen;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public int getNip() {
            return nip;
        }

        public void setNip(int nip) {
            this.nip = nip;
        }

        public String getRfid() {
            return rfid;
        }

        public void setRfid(String rfid) {
            this.rfid = rfid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


//    /**
//     * id_jadwal : 127
//     * lokasi_ruangan : GEDUNG D4
//     * nama_ruangan : A302
//     * pengawas : [{"absen":0,"nama":"Rizal","nip":2110141039,"rfid":123432,"status":"dosen"},{"absen":0,"nama":"Mill","nip":2110141043,"rfid":123421,"status":"teknisi"}]
//     * tipe_ruangan : kelas
//     * waktu_akhir : 2017-06-19 23:00:00
//     * waktu_awal : 2017-06-19 18:00:00
//     */
//
//    private int id_jadwal;
//    private String lokasi_ruangan;
//    private String nama_ruangan;
//    private String tipe_ruangan;
//    private String waktu_akhir;
//    private String waktu_awal;
//    private List<PengawasBean> pengawas;
//
//    public static Jadwal objectFromData(String str) {
//
//        return JSON.parseObject(str, Jadwal.class);
//
//    }
//
//    public static List<Jadwal> arrayJadwalFromData(String str) {
//        return JSON.parseArray(str, Jadwal.class);
//    }
//
//    public int getId_jadwal() {
//        return id_jadwal;
//    }
//
//    public void setId_jadwal(int id_jadwal) {
//        this.id_jadwal = id_jadwal;
//    }
//
//    public String getLokasi_ruangan() {
//        return lokasi_ruangan;
//    }
//
//    public void setLokasi_ruangan(String lokasi_ruangan) {
//        this.lokasi_ruangan = lokasi_ruangan;
//    }
//
//    public String getNama_ruangan() {
//        return nama_ruangan;
//    }
//
//    public void setNama_ruangan(String nama_ruangan) {
//        this.nama_ruangan = nama_ruangan;
//    }
//
//    public String getTipe_ruangan() {
//        return tipe_ruangan;
//    }
//
//    public void setTipe_ruangan(String tipe_ruangan) {
//        this.tipe_ruangan = tipe_ruangan;
//    }
//
//    public String getWaktu_akhir() {
//        return waktu_akhir;
//    }
//
//    public void setWaktu_akhir(String waktu_akhir) {
//        this.waktu_akhir = waktu_akhir;
//    }
//
//    public String getWaktu_awal() {
//        return waktu_awal;
//    }
//
//    public void setWaktu_awal(String waktu_awal) {
//        this.waktu_awal = waktu_awal;
//    }
//
//    public List<PengawasBean> getPengawas() {
//        return pengawas;
//    }
//
//    public void setPengawas(List<PengawasBean> pengawas) {
//        this.pengawas = pengawas;
//    }
//
//    public static class PengawasBean {
//        /**
//         * absen : 0
//         * nama : Rizal
//         * nip : 2110141039
//         * rfid : 123432
//         * status : dosen
//         */
//
//        private int absen;
//        private String nama;
//        private int nip;
//        private long rfid;
//        private String status;
//
//        public static PengawasBean objectFromData(String str) {
//
//            return JSON.parseObject(str, PengawasBean.class);
//
//        }
//
//        public static List<PengawasBean> arrayPengawasBeanFromData(String str) {
//            return JSON.parseArray(str, PengawasBean.class);
//        }
//
//        public int getAbsen() {
//            return absen;
//        }
//
//        public void setAbsen(int absen) {
//            this.absen = absen;
//        }
//
//        public String getNama() {
//            return nama;
//        }
//
//        public void setNama(String nama) {
//            this.nama = nama;
//        }
//
//        public int getNip() {
//            return nip;
//        }
//
//        public void setNip(int nip) {
//            this.nip = nip;
//        }
//
//        public long getRfid() {
//            return rfid;
//        }
//
//        public void setRfid(int rfid) {
//            this.rfid = rfid;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//    }

}
