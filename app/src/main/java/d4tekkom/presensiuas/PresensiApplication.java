package d4tekkom.presensiuas;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import d4tekkom.presensiuas.data.DataModule;
import d4tekkom.presensiuas.utility.NetworkClient;
import okhttp3.OkHttpClient;

/**
 * Created by doy on 14/06/17.
 */

public class PresensiApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this, NetworkClient.timeOutClient());
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
        mApplicationComponent.inject(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }
}
