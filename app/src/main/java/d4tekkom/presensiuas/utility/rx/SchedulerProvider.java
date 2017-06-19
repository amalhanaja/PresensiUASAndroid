package d4tekkom.presensiuas.utility.rx;

import io.reactivex.Scheduler;

/**
 * Created by doy on 18/06/17.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();

    Scheduler computation();
}
