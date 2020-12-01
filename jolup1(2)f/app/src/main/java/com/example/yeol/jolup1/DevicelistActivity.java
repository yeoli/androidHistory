package com.example.yeol.jolup1;

/**
 * Created by yeol on 2019-06-09.
 */

        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.Manifest;
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Bundle;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Set;

/**
 * Created by demm on 2019-03-22.
 */


public class DevicelistActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listview;
    private ListViewAdapter adapter;
    BluetoothAdapter btAdapter;
    ArrayList<ListItem> btlist;
    Button btnscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelist);

        adapter = new ListViewAdapter();
        listview = (ListView) findViewById(R.id.Lv);
//리스트뷰에 커스튬어뎁터 연결
        listview.setAdapter(adapter);

        //블루투스 어댑터 초기화
        btAdapter = BluetoothAdapter.getDefaultAdapter();
//페어링된 디바이스 목록 가져오기
        Set<BluetoothDevice> pairedDvices = btAdapter.getBondedDevices();

        btlist = new ArrayList<ListItem>();

        //페어링된 디바이스 목록 리스트뷰에 띄우기
        if (pairedDvices.size() > 0) {
            for (BluetoothDevice device : pairedDvices) {
                adapter.add(device.getName(), device.getAddress());
            }
        }


        //블루투스 브로드캐스트 리시버 등록
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND); //기기 찾았을 때
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);//기기검색 시작
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//기기 검색 종료
        this.registerReceiver(mReceiver, filter);

        //기기검색
        btnscan = (Button) findViewById(R.id.btnscan);
        btnscan.setOnClickListener(this);

//리스트뷰 클릭시(연결 원하는 기기 선택시)
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                ListItem item = adapter.getItem(position);
                intent.putExtra("name", item.getBtname());
                intent.putExtra("address",item.getBtMaddress());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    //스캔버튼 클릭시
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnscan) {
            btnscan.setEnabled(false);
            //android 6.0 이상은 이거 넣어줘야 기기스캔가능
            int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            btAdapter.startDiscovery();
        }
    }

    //기기검색 브로드캐스트 리시버
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                //기기 검색 시작
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    btnscan.setEnabled(false);
                    btnscan.setText("검색 중 ...");
                    break;
                //기기 찾음
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(adapter.overlapcheck(device.getName())){
                        adapter.add(device.getName(), device.getAddress());
                        Toast.makeText(DevicelistActivity.this, device.getName(), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();}
                    break;
                //기기 검색 종료
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    btnscan.setText("SCAN FOR DEVICE");
                    btnscan.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        btAdapter.cancelDiscovery();
        super.onDestroy();
    }
}

