package d4tekkom.presensiuas.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "BluetoothTerminal";
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private AcceptThread mAcceptThread;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private final Handler mHandler;
    private int mState = 0;

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = BluetoothService.this.mAdapter.listenUsingRfcommWithServiceRecord(BluetoothService.NAME, BluetoothService.MY_UUID);
            } catch (IOException e) {
            }
            this.mmServerSocket = tmp;
        }

        public void run() {
            setName("AcceptThread");
            while (BluetoothService.this.mState != BluetoothService.STATE_CONNECTED) {
                try {
                    BluetoothSocket socket = this.mmServerSocket.accept();
                    if (socket != null) {
                        synchronized (BluetoothService.this) {
                            switch (BluetoothService.this.mState) {
                                case 0:
                                case BluetoothService.STATE_CONNECTED /*3*/:
                                    try {
                                        socket.close();
                                        break;
                                    } catch (IOException e) {
                                        break;
                                    }
                                case BluetoothService.STATE_LISTEN /*1*/:
                                case BluetoothService.STATE_CONNECTING /*2*/:
                                    BluetoothService.this.connected(socket, socket.getRemoteDevice());
                                    break;
                            }
                        }
                    }
                } catch (IOException e2) {
                    return;
                }
            }
            return;
        }

        public void cancel() {
            try {
                this.mmServerSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        private final BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device) {
            this.mmDevice = device;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(BluetoothService.MY_UUID);
            } catch (IOException e) {
            }
            this.mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");
            BluetoothService.this.mAdapter.cancelDiscovery();
            try {
                this.mmSocket.connect();
                synchronized (BluetoothService.this) {
                    BluetoothService.this.mConnectThread = null;
                }
                BluetoothService.this.connected(this.mmSocket, this.mmDevice);
            } catch (IOException e) {
                BluetoothService.this.connectionFailed();
                try {
                    this.mmSocket.close();
                } catch (IOException e2) {
                }
                BluetoothService.this.start();
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;

        public ConnectedThread(BluetoothSocket socket) {
            this.mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            this.mmInStream = tmpIn;
            this.mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    BluetoothService.this.mHandler.obtainMessage(BluetoothService.STATE_CONNECTING, this.mmInStream.read(buffer), -1, buffer).sendToTarget();
                } catch (IOException e) {
                    BluetoothService.this.connectionLost();
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.mmOutStream.write(buffer);
                BluetoothService.this.mHandler.obtainMessage(BluetoothService.STATE_CONNECTED, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public BluetoothService(Context context, Handler handler) {
        this.mHandler = handler;
    }

    private synchronized void setState(int state) {
        this.mState = state;
        this.mHandler.obtainMessage(STATE_LISTEN, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        return this.mState;
    }

    public synchronized void start() {
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread == null) {
            this.mAcceptThread = new AcceptThread();
            this.mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    public synchronized void connect(BluetoothDevice device) {
        if (this.mState == STATE_CONNECTING && this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        this.mConnectThread = new ConnectThread(device);
        this.mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread != null) {
            this.mAcceptThread.cancel();
            this.mAcceptThread = null;
        }
        this.mConnectedThread = new ConnectedThread(socket);
        this.mConnectedThread.start();
        Message msg = this.mHandler.obtainMessage(4);
        Bundle bundle = new Bundle();
//        bundle.putString(main.DEVICE_NAME, device.getName());
        bundle.putString("device_name", device.getName());
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        setState(STATE_CONNECTED);
    }

    public synchronized void stop() {
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }
        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }
        if (this.mAcceptThread != null) {
            this.mAcceptThread.cancel();
            this.mAcceptThread = null;
        }
        setState(0);
    }

    public void write(byte[] out) {
        synchronized (this) {
            if (this.mState != STATE_CONNECTED) {
                return;
            }
            ConnectedThread r = this.mConnectedThread;
            r.write(out);
        }
    }

    private void connectionFailed() {
        setState(STATE_LISTEN);
        Message msg = this.mHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
//        bundle.putString(main.TOAST, "Unable to connect device");
        bundle.putString("toast", "Unable to connect device");
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
    }

    private void connectionLost() {
        setState(STATE_LISTEN);
        Message msg = this.mHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
//        bundle.putString(main.TOAST, "Device connection was lost");
        bundle.putString("toast", "Device connection was lost");
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
    }
}