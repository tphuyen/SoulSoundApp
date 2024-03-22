package com.project.soulsoundapp.utils;

import android.content.Context;

public class SettingManager {
    private static SettingManager instance;
    private Context context;

    private SettingManager(Context context) {
        this.context = context;
    }

    public static SettingManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SettingManager.class) {
                if (instance == null) {
                    instance = new SettingManager(context);
                }
            }
        }
        return instance;
    }
}
