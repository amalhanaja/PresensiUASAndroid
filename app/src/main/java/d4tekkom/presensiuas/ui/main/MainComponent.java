package d4tekkom.presensiuas.ui.main;

import d4tekkom.presensiuas.ApplicationComponent;
import dagger.Component;

/**
 * Created by doy on 19/06/17.
 */

@MainScope
@Component(dependencies = ApplicationComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
