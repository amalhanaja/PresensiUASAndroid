package d4tekkom.presensiuas.ui.base;

/**
 * Created by doy on 16/06/17.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void setupView();
}
