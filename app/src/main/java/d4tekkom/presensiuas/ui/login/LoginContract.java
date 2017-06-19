package d4tekkom.presensiuas.ui.login;

import d4tekkom.presensiuas.ui.base.BasePresenter;
import d4tekkom.presensiuas.ui.base.BaseView;

/**
 * Created by doy on 16/06/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showProgressDialog();

        void dismissProgressDialog();

        void startMainActivity();
    }
    interface Presenter extends BasePresenter {
        void onLogin(String nip, String password);
    }
}
