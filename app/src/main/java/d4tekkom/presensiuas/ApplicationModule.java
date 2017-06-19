package d4tekkom.presensiuas;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by doy on 16/06/17.
 */

@Module
public final class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application){
        mApplication = application;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @ApplicationContext
    @Provides
    Context provideApplicationContext(){
        return mApplication;
    }

}
