package d4tekkom.presensiuas.ui.bluetooth;

import android.content.Context;

import d4tekkom.presensiuas.ApplicationContext;
import d4tekkom.presensiuas.PresensiApplication;
import d4tekkom.presensiuas.utility.rx.AppSchedulerProvider;
import d4tekkom.presensiuas.utility.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by doy on 17/06/17.
 */

@Module
public final class BluetoothModule {
    private final BluetoothContract.View mView;

    public BluetoothModule(BluetoothContract.View view) {
        this.mView = view;
    }

    @Provides
    BluetoothContract.View provideBluetoothContractView(){
        return mView;
    }

    @Provides
    BluetoothContract.Presenter provideBluetoothContractPresenter(BluetoothPresenter presenter){
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider(){
        return new AppSchedulerProvider();
    }
}
