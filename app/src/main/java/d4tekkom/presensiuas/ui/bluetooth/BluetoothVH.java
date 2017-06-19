package d4tekkom.presensiuas.ui.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import d4tekkom.presensiuas.R;

/**
 * Created by doy on 16/06/17.
 */

public class BluetoothVH extends RecyclerView.ViewHolder {

    private final TextView txtName, txtSerial;

    public BluetoothVH(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txt_name);
        txtSerial = itemView.findViewById(R.id.txt_serial);
    }

    public void onBind(BluetoothDevice device){
        txtName.setText(device.getName());
        txtSerial.setText(device.getAddress());
    }
}
