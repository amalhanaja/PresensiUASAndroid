package d4tekkom.presensiuas.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import d4tekkom.presensiuas.ApplicationContext;
import d4tekkom.presensiuas.service.BluetoothService;
import d4tekkom.presensiuas.ui.main.MainContract;
import d4tekkom.presensiuas.utility.rx.SchedulerProvider;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by doy on 16/06/17.
 */

public class BluetoothPresenter implements BluetoothContract.Presenter{

    private final BluetoothContract.View mView;
    private final Context mContext;
    private final BluetoothAdapter mBluetoothAdapter;
    private final CompositeDisposable mCompositeDisposable;
    private final SchedulerProvider mSchedulerProvider;
    private List<BluetoothDevice> mAvailableBluetoothDevices = new ArrayList<>();
    private List<BluetoothDevice> mPairedBluetoothDevices = new ArrayList<>();
    private BluetoothService btService;

    private final BroadcastReceiver mDiscoverBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Logger.i(action);
            if (action.equalsIgnoreCase(BluetoothDevice.ACTION_FOUND)){
                Logger.i("BLUETOOTH DEVICES FOUND");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Logger.i(device.getName() + " ," + device.getAddress());
                mAvailableBluetoothDevices.add(device);
            } else if (action.equalsIgnoreCase(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
                Logger.i("BLUETOOTH DISCOVERY STARTED");
                mView.showLoadingAvailableDevices();
            } else if (action.equalsIgnoreCase(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                Logger.i("BLUETOOTH DISCOVERY FINISHED");
                mView.hideLoadingAvailableDevices();
            }
            mView.showAvailableDevices();
        }
    };

    @Inject
    public BluetoothPresenter(BluetoothContract.View view,
                              @ApplicationContext Context context,
                              CompositeDisposable compositeDisposable,
                              SchedulerProvider schedulerProvider) {
        this.mView = view;
        mContext = context;
        mCompositeDisposable = compositeDisposable;
        mSchedulerProvider = schedulerProvider;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mView.setupView();
        IntentFilter discoverBluetoothIntentFilter = new IntentFilter();
        discoverBluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        discoverBluetoothIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        discoverBluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mDiscoverBluetoothReceiver, discoverBluetoothIntentFilter);
        if (mBluetoothAdapter == null){
            Logger.e("Devices Doesn't Support Bluetooth Capabilities");
        } else {
            getPairedBluetoothDeviceList();
            if (mBluetoothAdapter.isEnabled()){
                mView.showBluetoothOn();
                getPairedBluetoothDeviceList();
//                mView.showPairedDevices();
            } else {
                mView.showBluetoothOff();
            }
        }
    }

    @Override
    public void unsubscribe() {
        mContext.unregisterReceiver(mDiscoverBluetoothReceiver);
        mCompositeDisposable.clear();
    }

    @Override
    public void onBluetoothSwitch(boolean isOn) {
        Logger.i("On Bluetooth Checked Changed");
        if (isOn){
            mCompositeDisposable.add(Observable.just(mBluetoothAdapter.enable())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribeOn(mSchedulerProvider.io())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            Logger.i(aBoolean.toString());
                            getPairedBluetoothDeviceList();
                            discoverBluetoothDevices();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            Logger.e(throwable, throwable.getMessage());
                        }
                    }));
//            mBluetoothAdapter.enable();
//            getPairedBluetoothDeviceList();
//            mView.showPairedDevices();
//            discoverBluetoothDevices();
        } else {
            mView.showBluetoothOff();
            mBluetoothAdapter.disable();
        }
    }

    @Override
    public void discoverBluetoothDevices() {
        if (!mAvailableBluetoothDevices.isEmpty()){
            mAvailableBluetoothDevices.clear();
        }
        if (mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
            Logger.d("CANCEL DISCOVERING");
        }
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public List<BluetoothDevice> getAvailableBluetoothDevices() {
        Logger.d(mAvailableBluetoothDevices.size());
        return mAvailableBluetoothDevices;
    }

    private void getPairedBluetoothDeviceList(){
        Logger.i(String.valueOf(mBluetoothAdapter.isEnabled()));
        if (mBluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : pairedDevices) {
                if (!mPairedBluetoothDevices.isEmpty()) {
                    mPairedBluetoothDevices.clear();
                }
                Logger.i(device.getName());
                mPairedBluetoothDevices.add(device);
            }
            mView.showPairedDevices();
        }
    }

    @Override
    public List<BluetoothDevice> getPairedBluetoothDevices() {
        Logger.d(mPairedBluetoothDevices.size());
        return mPairedBluetoothDevices;
    }

    @Override
    public void pairingDevice(BluetoothDevice device) {
        mBluetoothAdapter.cancelDiscovery();
        boolean isPaired = false;
        mView.onPairing();
        try {
            isPaired = createBond(device);
            if (isPaired){
                getPairedBluetoothDeviceList();
//                mView.showPairedDevices();
                Logger.d(device.getName() + " Is Paired");
                mView.onPairingComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean createBond(BluetoothDevice btDevice) throws Exception {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    @Override
    public void connectToDevice(BluetoothDevice device) {
        mBluetoothAdapter.cancelDiscovery();
        mView.finishActivity(device);
    }
}