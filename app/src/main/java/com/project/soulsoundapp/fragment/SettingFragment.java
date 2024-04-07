package com.project.soulsoundapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.ProfileActivity;
import com.project.soulsoundapp.activity.SignInActivity;
import com.project.soulsoundapp.activity.SleepTimerActivity;

import java.util.Locale;


public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment";

    private ImageView ivUserAvatar, ivSleepTimer;
    private TextView tvViewProfile, tvNameUser;
    private Button btnLogOut;
    SwitchCompat switchMode, switchModeLanguage;
    boolean nightMode, eLanguage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";


//    Preference for music player
SharedPreferences.Editor editorMusic;
    private static SharedPreferences prefs;
    private static final String SHARED_PREF_NAME_MUSIC = "player_music";
    private static final String KEY_SONG_ID = "songId";
    private static final String KEY_SONG_INDEX = "songIndex";
    private static final String KEY_PLAYLIST = "playlist";
    private static final String KEY_CURRENT_TIME = "currentTime";
    private static final String KEY_IS_PLAYING = "isPlaying";

    public SettingFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
        initView(view);
    }

    private void addControls(View view) {
        ivUserAvatar = view.findViewById(R.id.ivUserAvatar);
        tvViewProfile = view.findViewById(R.id.tvViewProfile);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        ivSleepTimer = view.findViewById(R.id.ivSleepTimer);
        tvNameUser = view.findViewById(R.id.tvNameUser);

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        prefs = getContext().getSharedPreferences(SHARED_PREF_NAME_MUSIC, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editorMusic = prefs.edit();

        String fullname;
        try {
            fullname = sharedPreferences.getString(KEY_NAME, null);
        } catch (Exception e) {
            fullname = "Unknown";
        }
        tvNameUser.setText(fullname);
    }

    private void addEvents() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin user khỏi SharedPreferences
                editor.remove(KEY_NAME);
                editor.remove(KEY_EMAIL);
                editor.apply();

                editorMusic.remove(KEY_SONG_ID);
                editorMusic.remove(KEY_SONG_INDEX);
                editorMusic.remove(KEY_PLAYLIST);
                editorMusic.remove(KEY_CURRENT_TIME);
                editorMusic.remove(KEY_IS_PLAYING);
                editorMusic.apply();

                boolean isNameRemoved = !sharedPreferences.contains(KEY_NAME);
                boolean isEmailRemoved = !sharedPreferences.contains(KEY_EMAIL);

                Log.v(TAG, "Name removed: " + isNameRemoved);
                Log.v(TAG, "Email removed: " + isEmailRemoved);

                // Chuyển sang màn hình đăng ""nhập
                Intent intent = new Intent(getContext(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa hết activity cũ khi chuyển qua activity mới
                startActivity(intent);
            }
        });
        ivUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
        tvViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
        ivSleepTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SleepTimerActivity.class);
                startActivity(i);
            }
        });
    }
    private void initView(View view){
        switchMode = view.findViewById(R.id.switchMode);
        sharedPreferences = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);
        switchMode.setChecked(nightMode);

        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nightMode = !nightMode;
                switchMode.setChecked(nightMode);

                AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO);

                editor = sharedPreferences.edit();
                editor.putBoolean("nightMode", nightMode);
                editor.apply();
            }
        });
//        switchModeLanguage=view.findViewById(R.id.switchModeLanguage);
//        sharedPreferences = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);
//        eLanguage = sharedPreferences.getBoolean("eLanguage", false);
//        String currentLanguage = getCurrentLanguage();
//
//        // Kiểm tra nếu ngôn ngữ hiện tại là "en" thì đặt SwitchCompat là OFF (tiếng Anh), ngược lại đặt là ON (tiếng Việt)
//        switchModeLanguage.setChecked(currentLanguage.equals("en"));
//
//        // Thiết lập sự kiện click cho SwitchCompat để thay đổi ngôn ngữ
//        switchModeLanguage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Nếu SwitchCompat được check, đặt ngôn ngữ là "en", ngược lại đặt là "vi"
//                String languageCode = switchModeLanguage.isChecked() ? "en" : "vi";
//
//                // Gọi phương thức setLocale() để thiết lập ngôn ngữ mới
//                setLocale(languageCode);
//
//                // Tái tạo activity để cập nhật ngôn ngữ
//                getActivity().recreate();
//            }
//        });
    }
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Lưu ngôn ngữ được chọn vào SharedPreferences để sử dụng cho các lần mở ứng dụng sau
        SharedPreferences.Editor editor = getContext().getSharedPreferences("eLanguage", Context.MODE_PRIVATE).edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    // Phương thức để lấy ngôn ngữ hiện tại từ SharedPreferences
    private String getCurrentLanguage() {
        SharedPreferences prefs = getContext().getSharedPreferences("eLanguage", Context.MODE_PRIVATE);
        return prefs.getString("language", "en"); // Trả về mã ngôn ngữ đã lưu, mặc định là tiếng Anh ("en")
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}