package com.wind.fitz.fitzdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected static BroadcastReceiver myBroadCastReceiver;
    public static String normalBroadCast = "com.fitz.noramlbroadcast.demo";
    public static String protectedBroadCast = "android.intent.action.DOCK_EVENT";
    public static String extraName = "fitz";
    protected Context mContext;
    public static final String TAG = "fitzdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        registerNormalBroadcast();
        registerProtectedBroadcast();
    }

    public void registerNormalBroadcast() {
        myBroadCastReceiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                Toast.makeText(mContext,intent.getStringExtra(extraName),Toast.LENGTH_SHORT).show();
                Log.d(TAG,"normal");
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(normalBroadCast);
        registerReceiver(myBroadCastReceiver, intentFilter);
    }

    public void registerProtectedBroadcast() {
        myBroadCastReceiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                Toast.makeText(mContext,intent.getStringExtra(extraName),Toast.LENGTH_SHORT).show();
                Log.d(TAG,"protected");
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(protectedBroadCast);
        registerReceiver(myBroadCastReceiver, intentFilter);
    }

    public void sendNormal(View view) {
        Intent intent = new Intent();
        intent.setAction(normalBroadCast);
        intent.putExtra(extraName, "0000");
        sendBroadcast(intent);

    }

    public void sendProtected(View view) {
        try{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DOCK_EVENT);
            intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
            intent.putExtra(Intent.EXTRA_DOCK_STATE, Intent.EXTRA_DOCK_STATE_DESK);
            intent.putExtra(extraName, "1111");
            sendBroadcast(intent);
        }catch (IllegalStateException ii){
            Toast.makeText(mContext,"GG",Toast.LENGTH_SHORT).show();
            Log.e(TAG,""+ii.toString());
        }
    }

}
