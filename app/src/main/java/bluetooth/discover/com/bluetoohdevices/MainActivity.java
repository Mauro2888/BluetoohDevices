package bluetooth.discover.com.bluetoohdevices;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mTurnOn,mListDevices,mTurnOff,mActiveDevices,mDiscoverDevices;
    private ListView mListViewDevices;
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice>mPairedDevices;
    private ArrayList<String> arrayListPaired = new ArrayList<String>();
    private ArrayList<String> arrayListAvailable = new ArrayList<>();
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupEnvirnment();
    }

    public void setupEnvirnment(){
        mTurnOn = findViewById(R.id.turn_on);
        mTurnOff = findViewById(R.id.turn_off);
        mListDevices = findViewById(R.id.list_devices);
        mListViewDevices = findViewById(R.id.listvidew_devices);
        mActiveDevices = findViewById(R.id.active_devices);
        mDiscoverDevices = findViewById(R.id.discover_devices);
        mTurnOn.setOnClickListener(this);
        mTurnOff.setOnClickListener(this);
        mListDevices.setOnClickListener(this);
        mActiveDevices.setOnClickListener(this);
        mDiscoverDevices.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void visibility(){
        Intent visible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(visible,125);
        Toast.makeText(this, "Visibile", Toast.LENGTH_SHORT).show();;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceN = device.getName();
                String deviceAddress = device.getAddress();
                Toast.makeText(context, " " + deviceN + " " + deviceAddress, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void discoverDevices(){
        mBluetoothAdapter.startDiscovery();
        //call Broadcast
        intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void enableBT(){
        if (!mBluetoothAdapter.enable()){
            Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enable);
            Toast.makeText(this, "Bluetoorh Enabled", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Bluetoorh is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    public void activeDevices(){
        Intent active = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(active,0);
    }

    public void disableBT(){
        mBluetoothAdapter.disable();
        Toast.makeText(this, "Bluetoorh Disabled", Toast.LENGTH_SHORT).show();
    }

    public void listDevices(){
        mPairedDevices = mBluetoothAdapter.getBondedDevices();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayListPaired);
        for (BluetoothDevice devices: mPairedDevices) {
            arrayListPaired.add(devices.getName());
            Toast.makeText(this, ""+ devices.getName(), Toast.LENGTH_LONG).show();
        }
        mListViewDevices.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == mTurnOn){
            enableBT();
        }if (v == mListDevices){
            listDevices();
        }if (v == mTurnOff){
            disableBT();
        }if (v == mActiveDevices){
            activeDevices();
        }if (v == mDiscoverDevices){
            discoverDevices();
        }
    }
}
