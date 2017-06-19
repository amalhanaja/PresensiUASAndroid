package d4tekkom.presensiuas.ui.bluetooth;

import android.bluetooth.BluetoothDevice;

import java.util.List;

import d4tekkom.presensiuas.ui.base.BasePresenter;
import d4tekkom.presensiuas.ui.base.BaseView;

/**
 * Created by doy on 16/06/17.
 */

public interface BluetoothContract {
    interface View extends BaseView<Presenter>{
        void showBluetoothOn();

        void showBluetoothOff();

        void showLoadingAvailableDevices();

        void hideLoadingAvailableDevices();

        void showAvailableDevices();

        void showPairedDevices();

        void onPairing();

        void onPairingComplete();

        void finishActivity(BluetoothDevice device);

    }
    interface Presenter extends BasePresenter {
        void onBluetoothSwitch(boolean isOn);

        void discoverBluetoothDevices();

        List<BluetoothDevice> getAvailableBluetoothDevices();

        List<BluetoothDevice> getPairedBluetoothDevices();

        void pairingDevice(BluetoothDevice device);

        void connectToDevice(BluetoothDevice device);
    }
}
