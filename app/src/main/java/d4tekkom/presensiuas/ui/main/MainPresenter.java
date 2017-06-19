package d4tekkom.presensiuas.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import d4tekkom.presensiuas.ApplicationContext;
import d4tekkom.presensiuas.data.DataRepository;
import d4tekkom.presensiuas.data.model.Jadwal;
import d4tekkom.presensiuas.service.BluetoothService;
import d4tekkom.presensiuas.utility.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by doy on 16/06/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final CompositeDisposable mCompositeDisposable;
    private final SchedulerProvider mScheduler;
    private final DataRepository mRepository;
    private final MainContract.View mView;
    private final BluetoothService mBtService;
    private final Handler mHandler;

    private List<Jadwal> jadwalList = new ArrayList<>();

    @Inject
    public MainPresenter(CompositeDisposable compositeDisposable,
                         SchedulerProvider scheduler,
                         DataRepository repository,
                         MainContract.View view,
                         @ApplicationContext final Context context) {
        mCompositeDisposable = compositeDisposable;
        mScheduler = scheduler;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                switch (msg.what) {
                    case MainActivity.REQUEST_CONNECT_DEVICE /*1*/:
                        switch (msg.arg1) {
                            case 0:
                            case MainActivity.REQUEST_CONNECT_DEVICE /*1*/:
//                            context.mTitle.setText(R.string.title_not_connected);
//                            context.mImageView1.setVisibility(0);
//                            context.mImageView2.setVisibility(8);
//                            Toast.makeText()
                                Toast.makeText(context, "REQUEST CONNECT DEVICES", Toast.LENGTH_SHORT).show();
//                            context.mPairButton.setTextColor(-16777216);
                                return;
                            case MainActivity.REQUEST_ENABLE_BT /*2*/:
//                            context.mTitle.setText(R.string.title_connecting);
//                            context.mImageView1.setVisibility(0);
//                            context.mImageView2.setVisibility(8);
                                Toast.makeText(context, "REQUEST ENABLE BT", Toast.LENGTH_SHORT).show();
                                return;
                            case MainActivity.MESSAGE_WRITE /*3*/:
//                            context.mTitle.setText(R.string.title_connected_to);
//                            context.mTitle.append(context.mConnectedDeviceName);
//                            context.mConversationArrayAdapter.clear();
//                            context.mImageView1.setVisibility(8);
//                            context.mImageView2.setVisibility(0);
//                            context.mPairButton.setTextColor(-7829368);
                                Toast.makeText(context, "MESSAGE WRITE", Toast.LENGTH_SHORT).show();
                                v.vibrate(200);
                                return;
                            default:
                                return;
                        }
                    case MainActivity.REQUEST_ENABLE_BT /*2*/:
//                        context.sb.append(new String((byte[]) msg.obj, 0, msg.arg1));
//                    context.sb.append(new String(msg.o))
//                        String sbs = context.sb.toString();
//                        if (sbs.indexOf("\r") != -1) {
//                            sbs = sbs.replaceAll("(\\r|\\n)", "");
//                            if (context.sb.length() > MainActivity.REQUEST_CONNECT_DEVICE) {
//
////                            context.mConversationArrayAdapter.add(">" + sbs);
//                            } else {
////                            context.mConversationArrayAdapter.add(">");
//                            }
//                            if (MainActivity.SAVE_FLG) {
////                            context.openSDCardFile(context.sb);
//                            }
////                            context.sb.replace(0, context.sb.length(), "");
////                            MainActivity main_terminalBT2_main = context;
////                        main_terminalBT2_main.itemcounter = main_terminalBT2_main.itemcounter + MainActivity.REQUEST_CONNECT_DEVICE;
////                        context.mText2.setText("Items: " + context.itemcounter);
////                        context.SaveParam(null);
//                            v.vibrate(75);
//                            return;
//                        }
                        return;
                    case MainActivity.MESSAGE_WRITE /*3*/:
//                    context.mConversationArrayAdapter.add("Sent:  " + new String((byte[]) msg.obj).replaceAll("\\r", ""));
                        return;
                    case MainActivity.MESSAGE_DEVICE_NAME /*4*/:
//                    context.mConnectedDeviceName = msg.getData().getString(MainActivity.DEVICE_NAME);
//                    Toast.makeText(context.getApplicationContext(), "Connected to " + context.mConnectedDeviceName, 0).show();
                        return;
                    case MainActivity.MESSAGE_TOAST /*5*/:
                        Toast.makeText(context.getApplicationContext(), msg.getData().getString(MainActivity.TOAST), Toast.LENGTH_SHORT).show();
                        return;
                    default:
                        return;
                }
            }
        };


        mBtService = new BluetoothService(context, mHandler);
    }

    @Override
    public void subscribe() {
        Logger.i("SUBSCRIBING");
        mView.setupView();
        FetchJadwalList();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public List<Jadwal> getJadwal() {
        return jadwalList;
    }

    private void FetchJadwalList(){
        Logger.i("FETCHING");
        mCompositeDisposable.add(mRepository.getJadwalList()
                .observeOn(mScheduler.ui())
                .subscribeOn(mScheduler.io())
                .subscribe(new Consumer<List<Jadwal>>() {
                    @Override
                    public void accept(@NonNull List<Jadwal> jadwals) throws Exception {
                        Logger.i("ACCEPT");
                        if (!jadwalList.isEmpty()){
                            jadwalList.clear();
                        }
                        jadwalList.addAll(jadwals);
                        mView.refreshAdapter();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Logger.e(throwable, throwable.getMessage());
                    }
                })
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1){
//            mBtService.connect();
        }
    }

    @Override
    public void onGetRFID(String s) {
        for (Jadwal jadwal : jadwalList){
            for (final Jadwal.PengawasBean pengawasBean : jadwal.getPengawas()){
                if (String.valueOf(pengawasBean.getRfid()).equalsIgnoreCase(s)){
                    Logger.i("ABSEN == TRUE");
                    pengawasBean.setAbsen(1);
                    mView.refreshAdapter();
                    mCompositeDisposable.add(mRepository.updateAbsen(s)
                            .observeOn(mScheduler.ui())
                            .subscribeOn(mScheduler.io())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(@NonNull String s) throws Exception {
                                    Logger.d(s);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                    Logger.e(throwable, throwable.getMessage());
                                }
                            })
                    );
                    break;
                }

                Logger.i(s + "TIDAK ADA");
            }
        }
    }
}