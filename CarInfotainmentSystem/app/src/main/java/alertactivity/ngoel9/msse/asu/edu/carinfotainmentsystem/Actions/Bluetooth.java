package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by tanmay on 4/23/16.
 */
public class Bluetooth {

    public boolean startBluetooth(Context context){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (!isEnabled) {
            Toast.makeText(context, "startBluetooth: Bluetooth is now turned ON ", Toast.LENGTH_LONG).show();
            return bluetoothAdapter.enable();
        }
        else
            return true;
    }

    public boolean stopBluetooth(Context context){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (isEnabled) {
            Toast.makeText(context, "stopBluetooth: Bluetooth is now turned OFF ", Toast.LENGTH_LONG).show();
            return bluetoothAdapter.disable();
        }
        else
            return true;
    }

}
