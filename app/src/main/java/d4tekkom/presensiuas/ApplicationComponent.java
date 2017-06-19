package d4tekkom.presensiuas;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import d4tekkom.presensiuas.data.DataModule;
import d4tekkom.presensiuas.data.DataRepository;
import dagger.Component;

/**
 * Created by doy on 16/06/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {
    void inject(Application application);

    @ApplicationContext
    Context context();

    Application application();

    DataRepository dataRepository();

}
