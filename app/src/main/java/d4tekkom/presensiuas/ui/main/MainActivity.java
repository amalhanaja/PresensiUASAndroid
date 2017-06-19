package d4tekkom.presensiuas.ui.main;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import d4tekkom.presensiuas.ApplicationRecyclerAdapter;
import d4tekkom.presensiuas.PresensiApplication;
import d4tekkom.presensiuas.R;
import d4tekkom.presensiuas.data.model.Jadwal;
import d4tekkom.presensiuas.service.BluetoothService;
import d4tekkom.presensiuas.ui.bluetooth.BluetoothActivity;
import d4tekkom.presensiuas.ui.device.DeviceListActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    MainContract.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private GridLayoutManager mLayoutManager;
    private ApplicationRecyclerAdapter mAdapter;

    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_WRITE = 3;
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    public static boolean SAVE_FLG = false;
    public static boolean SENDMSG_FLG = false;
    public static final String TOAST = "toast";
    private StringBuffer sb = new StringBuffer();
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            switch (msg.what) {
                case MainActivity.REQUEST_CONNECT_DEVICE /*1*/:
                    switch (msg.arg1) {
                        case 0:
                        case MainActivity.REQUEST_CONNECT_DEVICE /*1*/:
//                            MainActivity.this.mTitle.setText(R.string.title_not_connected);
//                            MainActivity.this.mImageView1.setVisibility(0);
//                            MainActivity.this.mImageView2.setVisibility(8);
//                            Toast.makeText()
                            Toast.makeText(MainActivity.this, "REQUEST CONNECT DEVICES", Toast.LENGTH_SHORT).show();
//                            MainActivity.this.mPairButton.setTextColor(-16777216);
                            return;
                        case MainActivity.REQUEST_ENABLE_BT /*2*/:
//                            MainActivity.this.mTitle.setText(R.string.title_connecting);
//                            MainActivity.this.mImageView1.setVisibility(0);
//                            MainActivity.this.mImageView2.setVisibility(8);
                            Toast.makeText(MainActivity.this, "REQUEST ENABLE BT", Toast.LENGTH_SHORT).show();
                            return;
                        case MainActivity.MESSAGE_WRITE /*3*/:
//                            MainActivity.this.mTitle.setText(R.string.title_connected_to);
//                            MainActivity.this.mTitle.append(MainActivity.this.mConnectedDeviceName);
//                            MainActivity.this.mConversationArrayAdapter.clear();
//                            MainActivity.this.mImageView1.setVisibility(8);
//                            MainActivity.this.mImageView2.setVisibility(0);
//                            MainActivity.this.mPairButton.setTextColor(-7829368);
                            Toast.makeText(MainActivity.this, "MESSAGE WRITE", Toast.LENGTH_SHORT).show();
                            v.vibrate(200);
                            return;
                        default:
                            return;
                    }
                case MainActivity.REQUEST_ENABLE_BT /*2*/:
                    MainActivity.this.sb.append(new String((byte[]) msg.obj, 0, msg.arg1));
//                    MainActivity.this.sb.append(new String(msg.o))
                    String sbs = MainActivity.this.sb.toString();
                    if (sbs.indexOf("\r") != -1) {
                        sbs = sbs.replaceAll("(\\r|\\n)", "");
                        if (MainActivity.this.sb.length() > MainActivity.REQUEST_CONNECT_DEVICE) {

//                            MainActivity.this.mConversationArrayAdapter.add(">" + sbs);
                            mPresenter.onGetRFID(sbs);
                            Logger.i(">" + sbs);
                        } else {
//                            MainActivity.this.mConversationArrayAdapter.add(">");
                            Logger.i(">");
                        }
                        if (MainActivity.SAVE_FLG) {
//                            MainActivity.this.openSDCardFile(MainActivity.this.sb);
                        }
                        MainActivity.this.sb.replace(0, MainActivity.this.sb.length(), "");
                        MainActivity main_terminalBT2_main = MainActivity.this;
//                        main_terminalBT2_main.itemcounter = main_terminalBT2_main.itemcounter + MainActivity.REQUEST_CONNECT_DEVICE;
//                        MainActivity.this.mText2.setText("Items: " + MainActivity.this.itemcounter);
//                        MainActivity.this.SaveParam(null);
                        v.vibrate(75);
                        return;
                    }
                    return;
                case MainActivity.MESSAGE_WRITE /*3*/:
//                    MainActivity.this.mConversationArrayAdapter.add("Sent:  " + new String((byte[]) msg.obj).replaceAll("\\r", ""));
                    return;
                case MainActivity.MESSAGE_DEVICE_NAME /*4*/:
//                    MainActivity.this.mConnectedDeviceName = msg.getData().getString(MainActivity.DEVICE_NAME);
//                    Toast.makeText(MainActivity.this.getApplicationContext(), "Connected to " + MainActivity.this.mConnectedDeviceName, 0).show();
                    return;
                case MainActivity.MESSAGE_TOAST /*5*/:
                    Toast.makeText(MainActivity.this.getApplicationContext(), msg.getData().getString(MainActivity.TOAST), Toast.LENGTH_SHORT).show();
                    return;
                default:
                    return;
            }
        }
    };
    private BluetoothService mBTService;
    private BluetoothAdapter mBluetoothAdapter;

    public static Intent getStartedIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .applicationComponent((((PresensiApplication)getApplication()).getApplicationComponent()))
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setupView() {

        this.mBTService = new BluetoothService(this, this.mHandler);

        mAdapter = new ApplicationRecyclerAdapter<Jadwal, MainVH>(
                R.layout.item_jadwal, MainVH.class, Jadwal.class, mPresenter.getJadwal()
        ) {

            @Override
            protected void bindView(MainVH holder, Jadwal model, int position) {
                if (!model.getPengawas().isEmpty()) {
                    holder.onBind(model, MainActivity.this);
                }
            }
        };

        mLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshAdapter() {

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bluetooth:
                startActivityForResult(new Intent(this, DeviceListActivity.class), REQUEST_CONNECT_DEVICE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mPresenter.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE /*1*/:
                if (resultCode == -1) {
                    String dataku = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    this.mBTService.connect(this.mBluetoothAdapter.getRemoteDevice(dataku));
                    return;
                }
                return;
            default:
                return;
        }
    }
}
