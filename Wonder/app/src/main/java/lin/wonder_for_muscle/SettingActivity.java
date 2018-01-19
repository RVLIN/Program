package lin.wonder_for_muscle;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG="MainActivity";
    BluetoothAdapter settingbluetoothAdapter;
    Button btnEnableDisable_Discoverable;

    Button btnStartConnection;
    Button btnSend;
    EditText etSend;
    BluetoothConnectionService mBluetoothConnection;

    BluetoothDevice mBTDevice;

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    public ArrayList<BluetoothDevice> settingBTDevices=new ArrayList<>();
    public DeviceListAdapter settingDeviceListAdapter;

    ListView settinglvNewOevices;

    private final BroadcastReceiver settingBroadcastReceiver1=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(settingbluetoothAdapter.ACTION_STATE_CHANGED))
            {
                final int state=intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,settingbluetoothAdapter.ERROR);
                switch (state)
                {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG,"onRecevier:STATE_OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG,"onRecevier:STATE_TURNING_OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG,"onRecevier:STATE_ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG,"onRecevier:STATE_TURNING_ON");
                        break;
                }
            }
        }
    };
    private final BroadcastReceiver settingBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };
    private BroadcastReceiver settingBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                settingBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                settingDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view,settingBTDevices);
                settinglvNewOevices.setAdapter(( ListAdapter ) settingDeviceListAdapter);
            }
        }
    };
    private BroadcastReceiver settingBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice settingDevice = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                if(settingDevice.getBondState()==BluetoothDevice.BOND_BONDED)
                {
                    Log.d(TAG, "BroadcastReceiver4: BOND_BONDED");
                    mBTDevice = settingDevice;
                }
                if(settingDevice.getBondState()==BluetoothDevice.BOND_BONDING)
                {
                    Log.d(TAG, "BroadcastReceiver4: BOND_BONDING");
                }
                if(settingDevice.getBondState()==BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver4: BOND_NONE");
                }
            }
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(settingBroadcastReceiver1);
        unregisterReceiver(settingBroadcastReceiver2);
        unregisterReceiver(settingBroadcastReceiver3);
        unregisterReceiver(settingBroadcastReceiver4);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnStartConnection = (Button) findViewById(R.id.btnStartConnection);
        btnSend = (Button) findViewById(R.id.btnSend);
        etSend = (EditText) findViewById(R.id.editText);
        Button btnONOFF=(Button)findViewById(R.id.btnONOFF);
         btnEnableDisable_Discoverable = (Button) findViewById(R.id.btnEnalbeDiscoverable_on_off);
        settinglvNewOevices = (ListView) findViewById(R.id.lvNewDevices);
        settingBTDevices = new ArrayList<>();

        IntentFilter filter=new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(settingBroadcastReceiver4,filter);

        settingbluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

        btnONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableDisbleBT();
            }
        });
        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = etSend.getText().toString().getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });
    }
    public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device,uuid);
    }



    public void enableDisbleBT(){
        if(settingbluetoothAdapter==null)
        {
            Log.d(TAG,"enableDisbleBT:Does not have BT capabilities");
        }
        if(!settingbluetoothAdapter.isEnabled()){
            Intent enbleBTIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enbleBTIntent);

            IntentFilter BTIntent=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(settingBroadcastReceiver1,BTIntent);
        }
        if(settingbluetoothAdapter.isEnabled())
        {
            settingbluetoothAdapter.disable();
            IntentFilter BTIntent=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(settingBroadcastReceiver1,BTIntent);
        }
    }

    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if(settingbluetoothAdapter.isDiscovering()){
            settingbluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            settingbluetoothAdapter.startDiscovery() ;
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(settingBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!settingbluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest
            checkBTPermissions();

            settingbluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(settingBroadcastReceiver3, discoverDevicesIntent);
        }
    }
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    public void btnEnalbeDisable_Discoverable(View view) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(settingbluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(settingBroadcastReceiver2, intentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        settingbluetoothAdapter.cancelDiscovery();
        String deviceName=settingBTDevices.get(i).getName();
        String deviceAddress=settingBTDevices.get(i).getAddress();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "Trying to pair with " + deviceName);
            settingBTDevices.get(i).createBond();
            mBTDevice = settingBTDevices.get(i);
            mBluetoothConnection = new BluetoothConnectionService(SettingActivity.this);
        }
    }
}
