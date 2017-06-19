package d4tekkom.presensiuas.ui.login;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import d4tekkom.presensiuas.data.DataRepository;
import d4tekkom.presensiuas.data.model.Petugas;
import d4tekkom.presensiuas.utility.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by doy on 16/06/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private final CompositeDisposable mCompositeDisposable;
    private final DataRepository mDataRepository;
    private final SchedulerProvider mScheduler;

    @Inject
    public LoginPresenter(LoginContract.View view,
                          CompositeDisposable compositeDisposable,
                          SchedulerProvider schedulerProvider,
                          DataRepository dataRepository) {
        this.mView = view;
        this.mCompositeDisposable = compositeDisposable;
        this.mScheduler = schedulerProvider;
        mDataRepository = dataRepository;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mView.setupView();
        if (mDataRepository.isPetugasLoggedIn()){
            // TODO: 19/06/17 OPEN MAINACTIVITY
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
        mView.dismissProgressDialog();
    }

    @Override
    public void onLogin(String nip, String password) {
        mView.showProgressDialog();
        mCompositeDisposable.add(mDataRepository.loginPetugas(nip, password)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Consumer<Petugas>() {
                    @Override
                    public void accept(@NonNull Petugas petugas) throws Exception {
                        mDataRepository.createLoginSession(petugas);
                        mView.dismissProgressDialog();
                        mView.startMainActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Logger.e(throwable, throwable.getMessage());
                        mView.dismissProgressDialog();
                    }
                })
        );
    }
}
