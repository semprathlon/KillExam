package com.example.yuwei.killexam.tools;

import android.content.res.Resources;

import com.example.yuwei.killexam.R;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by yuwei on 15/2/22.
 */

//使用安卓里读取spinner 里的array，来将两个值（selectedName，position）互相绑定
public class SpinnerValue implements Serializable {
    private String selectedName;
    private String[] names;
    private HashMap<String, Integer> position = new HashMap();

    public static SpinnerValue initSpinnerValue(int resId, Resources resources){
        String[] remindMethods = resources.getStringArray(resId);
        SpinnerValue spinnerValue = new SpinnerValue(remindMethods);
        return spinnerValue;
    }

    public SpinnerValue(String[] names){
        this.names = names;
        selectedName = names[0];
        for (int i = 0; i < names.length; i++){
            position.put(names[i], i);
        }
    }

    public int getPosition(){
        return position.get(selectedName);
    }

    public String getName(int posioton){
        return names[posioton];
    }


    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String name) {
        this.selectedName = name;
    }

}
