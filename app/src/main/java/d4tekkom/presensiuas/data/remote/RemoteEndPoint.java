package d4tekkom.presensiuas.data.remote;

import d4tekkom.presensiuas.BuildConfig;
import okhttp3.HttpUrl;

/**
 * Created by doy on 18/06/17.
 */

public class RemoteEndPoint {
    public static final String LOGIN_ENDPOINT(){
        HttpUrl.Builder builder = HttpUrl.parse(BuildConfig.BASE_URL).newBuilder();
        builder.addEncodedPathSegment("login");
        return builder.build().toString();
    }

    public static final String JADWAL_ENDPOINT(){
        HttpUrl.Builder builder = HttpUrl.parse(BuildConfig.BASE_URL).newBuilder();
        builder.addEncodedPathSegment("jadwal");
        return builder.build().toString();
    }

    public static final String JADWAL_ENDPOINT(String RFID){
        HttpUrl.Builder builder = HttpUrl.parse(BuildConfig.BASE_URL).newBuilder();
        builder.addEncodedPathSegment("absen");
        builder.addEncodedPathSegment(RFID);
        return builder.build().toString();
    }
}
