package d4tekkom.presensiuas.ui.login;

import d4tekkom.presensiuas.utility.rx.AppSchedulerProvider;
import d4tekkom.presensiuas.utility.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by doy on 18/06/17.
 */

@Module
public final class LoginModule {

    private final LoginContract.View mView;

    public LoginModule(LoginContract.View view) {
        this.mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView(){
        return mView;
    }

    @Provides
    LoginContract.Presenter provideLoginContractPresenter(LoginPresenter presenter){
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
