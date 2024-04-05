package com.project.soulsoundapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingManager {
    private static SettingManager instance;
    private Context context;
    DatabaseHelper db = DatabaseHelper.getInstance(context);

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
