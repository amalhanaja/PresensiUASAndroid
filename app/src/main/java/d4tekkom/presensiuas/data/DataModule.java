package d4tekkom.presensiuas.data;

import javax.inject.Singleton;

import d4tekkom.presensiuas.data.pref.AppPref;
import d4tekkom.presensiuas.data.pref.PrefHelper;
import d4tekkom.presensiuas.data.remote.RemoteDataSource;
import d4tekkom.presensiuas.utility.AppConstants;
import dagger.Module;
import dagger.Provides;

/**
 * Created by doy on 18/06/17.
 */

@Module
public final class DataModule {

    public DataModule() {
    }

    @Provides
    @RemoteQualifier
    DataSource provideRemoteDataSource(){
        return new RemoteDataSource();
    }

    @Provides
    @PrefQualifier
    String providePreferencesName(){
        return AppConstants.PreferenceName;
    }

    @Provides
    @Singleton
    PrefHelper providePrefHelper(AppPref appPref){
        return appPref;
    }
}
