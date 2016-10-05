package camonia.appchallenge;


import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import camonia.appchallenge.Screen.ScreenOne;


public class MainActivity extends AppCompatActivity
{

    private static final String LOG_TAG = "MainActivity";
    private BluetoothManager btManager;
    private BluetoothAdapter btAdapter;
    private Handler scanHandler = new Handler();
    private Handler showScreen = new Handler();

    private int scan_interval_ms = 2000;
    private boolean isScanning = false;
    private Beacon scannedBeacon ;
    private Boolean firstTimeRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init BLE
        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        scannedBeacon = new Beacon();

        scanHandler.postDelayed(new Runnable(){
            @Override
            public void run() {
                startScanning();
            }
        },scan_interval_ms);

    }

    private void startScanning(){
        if (isScanning)
        {
            if (btAdapter != null)
            {
                btAdapter.stopLeScan(leScanCallback);
            }
        }
        else
        {
            if (btAdapter != null)
            {
                btAdapter.startLeScan(leScanCallback);
            }
        }

        isScanning = !isScanning;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Runnable scanRunnable = new Runnable()
    {
        @Override
        public void run() {
            startScanning();
            scanHandler.postDelayed(this, scan_interval_ms);
        }

    };


    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord)
        {
            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5)
            {
                if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15)
                { //Identifies correct data length
                    patternFound = true;
                    break;
                }
                startByte++;
            }

            if (patternFound)
            {
                //Convert to hex String
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);
                //UUID detection
                String uuid =  hexString.substring(0,8) + "-" +
                        hexString.substring(8,12) + "-" +
                        hexString.substring(12,16) + "-" +
                        hexString.substring(16,20) + "-" +
                        hexString.substring(20,32);

                // major
                final int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

                // minor
                final int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

                Log.i(LOG_TAG,"UUID: " +uuid + "\\nmajor: " +major +"\\nminor" +minor);
                Beacon detectBeacon = new Beacon();
                detectBeacon.setUUID(uuid);
                detectBeacon.setMinor(minor);
                detectBeacon.setMajor(major);
                if(scannedBeacon.getUUID() == null){
                    scannedBeacon.setMajor(major);
                    scannedBeacon.setMinor(minor);
                    scannedBeacon.setUUID(uuid);
                }
                checkForRepeatBeacon(scannedBeacon,detectBeacon,firstTimeRun);



            }

        }
    };

    private void checkForRepeatBeacon(Beacon scannedBeacon,Beacon detectBeacon, Boolean firstTimeRun){
        if(scannedBeacon.getMajor() != detectBeacon.getMajor() && scannedBeacon.getMinor() != detectBeacon.getMinor()){
            scannedBeacon.setMajor(detectBeacon.getMajor());
            scannedBeacon.setMinor(detectBeacon.getMinor());
            scannedBeacon.setUUID(detectBeacon.getUUID());
            startNewScren(scannedBeacon);
        }else{
            //repeated beacon
        }
        if(firstTimeRun){
            firstTimeRun = false;
            startNewScren(scannedBeacon);
        }
    }


    /**
     * bytesToHex method
     */
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private void startNewScren(Beacon scannedBeacon){

        if(scannedBeacon.getMajor() == 1000 && scannedBeacon.getMinor() == 2){
            Log.e("Start New Fragment","Start ");
            Intent intent = new Intent(getApplicationContext(), ScreenOne.class);
            startActivity(intent);
        }

    }
    public class TimerHandler {
        private double ElapsedSeconds=10;
        private long begin;
        public boolean started=false;

        public TimerHandler() {
        }

        public TimerHandler(double ElapsedSeconds) {
            this.ElapsedSeconds = ElapsedSeconds;
        }
        public void start(){
            begin =  System.currentTimeMillis();
            started=true;
        }
        public boolean isFinished(){
            if(started)
                if(System.currentTimeMillis() - begin> ElapsedSeconds*1000)
                    return true;

            return false;
        }

    }
}



