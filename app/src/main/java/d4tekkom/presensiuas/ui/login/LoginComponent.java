package d4tekkom.presensiuas.ui.login;

import android.app.Application;

import d4tekkom.presensiuas.ApplicationComponent;
import dagger.Component;

/**
 * Created by doy on 18/06/17.
 */

@LoginScope
@Component(dependencies = ApplicationComponent.class, modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}