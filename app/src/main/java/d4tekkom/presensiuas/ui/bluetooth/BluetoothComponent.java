package d4tekkom.presensiuas.ui.bluetooth;

import d4tekkom.presensiuas.ApplicationComponent;
import dagger.Component;

/**
 * Created by doy on 17/06/17.
 */

@BluetoothScope
@Component(dependencies = ApplicationComponent.class, modules = BluetoothModule.class)
public interface BluetoothComponent {
    void inject(BluetoothActivity activity);
}
