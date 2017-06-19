package d4tekkom.presensiuas.ui.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import d4tekkom.presensiuas.ApplicationRecyclerAdapter;
import d4tekkom.presensiuas.ApplicationRecyclerClickListener;
import d4tekkom.presensiuas.PresensiApplication;
import d4tekkom.presensiuas.R;

public class BluetoothActivity extends AppCompatActivity implements BluetoothContract.View {

    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    @Inject
    BluetoothContract.Presenter mPresenter;

    @BindView(R.id.switch_bluetooth)
    Switch switchBluetooth;
    @BindView(R.id.progress_bar1)
    ProgressBar progressBar1;
    @BindView(R.id.txt_avaible_devices)
    TextView txtAvaibleDevices;
    @BindView(R.id.recycler_available_devices)
    RecyclerView recyclerAvailableDevices;
    @BindView(R.id.recycler_paired_devices)
    RecyclerView recyclerPairedDevices;
    @BindView(R.id.txt_paired_devices)
    TextView txtPairedDevices;
    @BindView(R.id.txt_off)
    TextView txtOff;
    @BindView(R.id.scroll_layout)
    NestedScrollView scrollLayout;

    public static Intent getStartedIntent(Context context) {
        return new Intent(context, BluetoothActivity.class);
    }

    private LinearLayoutManager mAvailableBluetoothLayoutManager;
    private LinearLayoutManager mPairedBluetoothLayoutManager;
    private ApplicationRecyclerAdapter mAvailableDeviceAdapter;
    private ApplicationRecyclerAdapter mPairedDeviceAdapter;
    private ApplicationRecyclerClickListener mAvailableDeviceClickListener;
    private ApplicationRecyclerClickListener mPairedDeviceClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        DaggerBluetoothComponent.builder()
                .applicationComponent(((PresensiApplication) getApplication()).getApplicationComponent())
                .bluetoothModule(new BluetoothModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(BluetoothContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setupView() {
        switchBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    scrollLayout.setVisibility(View.VISIBLE);
                    txtOff.setVisibility(View.GONE);
                } else {
                    scrollLayout.setVisibility(View.GONE);
                    txtOff.setVisibility(View.VISIBLE);
                }
                mPresenter.onBluetoothSwitch(b);
            }
        });

        mAvailableDeviceAdapter = new ApplicationRecyclerAdapter<BluetoothDevice, BluetoothVH>(
                R.layout.item_bt_devices, BluetoothVH.class, BluetoothDevice.class, mPresenter.getAvailableBluetoothDevices()
        ) {
            @Override
            protected void bindView(BluetoothVH holder, BluetoothDevice model, int position) {
                holder.onBind(model);
            }
        };

        mAvailableBluetoothLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAvailableDeviceClickListener = new ApplicationRecyclerClickListener(this, new ApplicationRecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.pairingDevice(mPresenter.getAvailableBluetoothDevices().get(position));
            }
        });

        recyclerAvailableDevices.setLayoutManager(mAvailableBluetoothLayoutManager);
        recyclerAvailableDevices.setAdapter(mAvailableDeviceAdapter);
        recyclerAvailableDevices.addOnItemTouchListener(mAvailableDeviceClickListener);


        mPairedDeviceAdapter = new ApplicationRecyclerAdapter<BluetoothDevice, BluetoothVH>(
                R.layout.item_bt_devices, BluetoothVH.class, BluetoothDevice.class, mPresenter.getPairedBluetoothDevices()
        ) {
            @Override
            protected void bindView(BluetoothVH holder, BluetoothDevice model, int position) {
                holder.onBind(model);
            }
        };

        mPairedBluetoothLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPairedDeviceClickListener = new ApplicationRecyclerClickListener(this, new ApplicationRecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.connectToDevice(mPresenter.getPairedBluetoothDevices().get(position));
//                BluetoothActivity.this..cancelDiscovery();
//                String info = ((TextView) v).getText().toString();
//                String address = info.substring(info.length() - 17);
//                Intent intent = new Intent();
//                intent.putExtra(BluetoothActivity.EXTRA_DEVICE_ADDRESS, address);
//                BluetoothActivity.this.setResult(-1, intent);
//                BluetoothActivity.this.finish();
            }
        });

        recyclerPairedDevices.setLayoutManager(mPairedBluetoothLayoutManager);
        recyclerPairedDevices.setAdapter(mPairedDeviceAdapter);
        recyclerPairedDevices.addOnItemTouchListener(mPairedDeviceClickListener);
    }

    @Override
    public void finishActivity(BluetoothDevice device) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEVICE_ADDRESS, device.getAddress());
        setResult(-1, intent);
        finish();
    }

    @Override
    public void showBluetoothOn() {
        switchBluetooth.setText("On");
        switchBluetooth.setChecked(true);
    }

    @Override
    public void showBluetoothOff() {
        switchBluetooth.setText("Off");
    }

    @Override
    public void showLoadingAvailableDevices() {
        progressBar1.setIndeterminate(true);
        progressBar1.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingAvailableDevices() {
        progressBar1.setVisibility(View.GONE);
    }

    @Override
    public void showAvailableDevices() {
        mAvailableDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPairedDevices() {
        Logger.i("Show Paired Devices");
        mPairedDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPairing() {
        Toast.makeText(this, "Pairing to Devices", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPairingComplete() {
        Toast.makeText(this, "Successfully Pairing to Devices", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                Logger.d("ACTION SCAN CLICKED");
                mPresenter.discoverBluetoothDevices();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
