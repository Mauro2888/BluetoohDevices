package bluetooth.discover.com.bluetoohdevices;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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
    private Button mTurnOn,mListDevices,mTurnOff;
    private ListView mListViewDevices;
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice>mPairedDevices;

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
        mTurnOn.setOnClickListener(this);
        mTurnOff.setOnClickListener(this);
        mListDevices.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void visibility(){
        Intent visible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(visible,125);
        Toast.makeText(this, "Visibile", Toast.LENGTH_SHORT).show();;
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

    public void disableBT(){
        mBluetoothAdapter.disable();
        Toast.makeText(this, "Bluetoorh Disabled", Toast.LENGTH_SHORT).show();
    }

    public void listDevices(){
        mPairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        for (BluetoothDevice devices: mPairedDevices) {
            arrayList.add(devices.getName());
            Log.d(TAG ,devices.getName());
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
        }
    }
}
