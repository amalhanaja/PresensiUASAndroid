package d4tekkom.presensiuas.ui.main;

import android.content.Intent;

import java.util.List;

import d4tekkom.presensiuas.data.model.Jadwal;
import d4tekkom.presensiuas.ui.base.BasePresenter;
import d4tekkom.presensiuas.ui.base.BaseView;

/**
 * Created by doy on 16/06/17.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void refreshAdapter();

    }
    interface Presenter extends BasePresenter {
        List<Jadwal> getJadwal();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onGetRFID(String s);
    }
}
