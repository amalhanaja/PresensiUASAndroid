package d4tekkom.presensiuas.ui.device;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Set;

import d4tekkom.presensiuas.R;

public class DeviceListActivity extends Activity {
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private Button btnDiscovery;
    IntentFilter filter = new IntentFilter("android.bluetooth.device.action.FOUND");
    public ArrayList<String> list = new ArrayList();
    BluetoothAdapter mBluetoothAdapter;
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
            DeviceListActivity.this.mBluetoothAdapter.cancelDiscovery();
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            Intent intent = new Intent();
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
            Logger.i(address);
            DeviceListActivity.this.setResult(-1, intent);
            DeviceListActivity.this.finish();
        }
    };
    public ArrayAdapter<String> mNewDevicesArrayAdapter;
    public ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (device.getBondState() != 12) {
                    DeviceListActivity.this.mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                DeviceListActivity.this.setProgressBarIndeterminateVisibility(false);
                DeviceListActivity.this.setTitle("Select Device");
                if (DeviceListActivity.this.mNewDevicesArrayAdapter.getCount() == 0) {
                    DeviceListActivity.this.mNewDevicesArrayAdapter.add("Perangkat tidak ditemukam");
                }
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(5);
        setContentView(R.layout.activity_device_list);
        setResult(0);
        this.mPairedDevicesArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        this.mNewDevicesArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(this.mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(this.mDeviceClickListener);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(this.mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(this.mDeviceClickListener);
        this.btnDiscovery = (Button) findViewById(R.id.Button01);
        this.btnDiscovery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DeviceListActivity.this.doDiscovery();
                v.setVisibility(View.GONE);
            }
        });
        registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = this.mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                this.mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            return;
        }
        this.mPairedDevicesArrayAdapter.add("TIdak ada yang tersandingan");
    }

    private void doDiscovery() {
        setProgressBarIndeterminateVisibility(true);
        setTitle("Scanning for Device!");
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
        if (this.mBluetoothAdapter.isDiscovering()) {
            this.mBluetoothAdapter.cancelDiscovery();
        }
        this.mBluetoothAdapter.startDiscovery();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(this.mReceiver);
    }
}