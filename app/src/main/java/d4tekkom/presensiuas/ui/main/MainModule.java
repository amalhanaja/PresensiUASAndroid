package d4tekkom.presensiuas.ui.main;

import d4tekkom.presensiuas.utility.rx.AppSchedulerProvider;
import d4tekkom.presensiuas.utility.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by doy on 19/06/17.
 */

@Module
public final class MainModule {

    private final MainContract.View mView;


    public MainModule(MainContract.View view) {
        this.mView = view;
    }

    @Provides
    MainContract.View provideMainContractView(){
        return mView;
    }

    @Provides
    MainContract.Presenter provideMainContractPresenter(MainPresenter presenter){
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider(){
        return new AppSchedulerProvider();
    }
}
