package com.robiul.firstapk.dbUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReciever extends BroadcastReceiver {

    private int lastShownStep = -1;
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryport = (int) ((level / (float) scale) * 100);

        //Calculate 10%
        int currentStep = batteryport / 10;

        if (currentStep != lastShownStep){
            lastShownStep = currentStep;

            //show messege depending on the step
            switch (currentStep){
                case 10:
                    Toast.makeText(context,"Battery Full",Toast.LENGTH_SHORT).show();
                    break;

                case 9: //90%
                    Toast.makeText(context,"Battery 90%",Toast.LENGTH_SHORT).show();
                    break;

                case 8: //80%
                    Toast.makeText(context,"Battery 80%",Toast.LENGTH_SHORT).show();
                    break;

                case 5: //50%
                    Toast.makeText(context,"Battery 50%",Toast.LENGTH_SHORT).show();
                    break;

                case 3: //30%
                    Toast.makeText(context,"Battery 30%",Toast.LENGTH_SHORT).show();
                    break;

                case 1: //10%
                    Toast.makeText(context,"Battery 10%",Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(context,"Battery Level: " + batteryport + "%",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
