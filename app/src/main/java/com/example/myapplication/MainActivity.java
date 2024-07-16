package com.example.myapplication;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView text_second_24, text_minute_all, start, pause, clean_24, clean_14, all_function_done;
    private ExoPlayer mExoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);


        text_minute_all = findViewById(R.id.text_minute);
        text_second_24 = findViewById(R.id.text_second);
        text_minute_all.setTag(600);
        text_second_24.setTag(24);

        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        clean_24 = findViewById(R.id.clean_24);
        clean_14 = findViewById(R.id.clean_14);
        all_function_done = findViewById(R.id.all_function_done);

        //text_all.setOnClickListener(this);
        //text_24.setOnClickListener(this);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        clean_24.setOnClickListener(this);
        clean_14.setOnClickListener(this);
        all_function_done.setOnClickListener(this);

        start.setClickable(true);
        pause.setClickable(false);
        clean_24.setClickable(true);
        clean_14.setClickable(true);
        all_function_done.setClickable(true);
    }


    @Override
    public void onClick(View v) {
        if (v == start) {
            mediaRelease();
            startTime();
        } else if (v == pause) {
            pauseTime();
        } else if (v == clean_24) {
            clean24Time();
        } else if (v == clean_14) {
            clean14Time();
        } else if (v == all_function_done) {
            resetAll();
        }
    }

    private void openMediaWhenTimeOut() {
        //Log.e("TAGMain", "openMediaWhenTimeOut()");
        mExoPlayer = new ExoPlayer.Builder(this).build();
        Uri uri = Uri.parse("asset:///1.m4a");
        MediaItem mediaItem = MediaItem.fromUri(uri);
        mExoPlayer.addMediaItem(mediaItem);
        mExoPlayer.setVolume(1f);
        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.prepare();

        mHandler.sendEmptyMessageDelayed(3, 5000);
    }

    private void mediaRelease() {
        //Log.e("TAGMain", "mediaRelease() mExoPlayer != null " + (mExoPlayer != null));
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.setVolume(0);
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void resetAll() {
        //Log.e("TAGMain", "resetAll()");
        mHandler.removeMessages(1);
        mHandler.removeMessages(2);

        text_second_24.setTextColor(Color.WHITE);
        text_minute_all.setTextColor(Color.WHITE);

        text_second_24.setTag(24);
        text_minute_all.setTag(600);//10分钟
        text_second_24.setText(String.valueOf(24));
        text_minute_all.setText(formatSecondsAsMinutesSeconds(600));

        start.setClickable(true);
        pause.setClickable(false);
        clean_24.setClickable(true);
        clean_14.setClickable(true);

        start.setTextColor(Color.WHITE);
        pause.setTextColor(getResources().getColor(R.color.teal_700));
        clean_24.setTextColor(Color.WHITE);
        clean_14.setTextColor(Color.WHITE);

    }

    private void clean14Time() {
        //Log.e("TAGMain", "clean14Time()");

        mHandler.removeMessages(1);
        mHandler.removeMessages(2);

        int timeAll = (int) text_minute_all.getTag();
        if (timeAll < 14) {
            text_second_24.setText(String.valueOf(timeAll));
            text_second_24.setTag(timeAll);
        } else {
            text_second_24.setText("14");
            text_second_24.setTag(14);
        }

        text_second_24.setTextColor(Color.WHITE);

        start.setClickable(true);
        pause.setClickable(false);
        clean_24.setClickable(true);
        clean_14.setClickable(false);

        start.setTextColor(Color.WHITE);
        pause.setTextColor(getResources().getColor(R.color.teal_700));
        clean_24.setTextColor(Color.WHITE);
        clean_14.setTextColor(getResources().getColor(R.color.teal_700));
    }

    private void clean24Time() {
        //Log.e("TAGMain", "clean24Time()");

        mHandler.removeMessages(1);
        mHandler.removeMessages(2);

        int timeAll = (int) text_minute_all.getTag();
        if (timeAll < 24) {
            text_second_24.setText(String.valueOf(timeAll));
            text_second_24.setTag(timeAll);
        } else {
            text_second_24.setText("24");
            text_second_24.setTag(24);
        }

        text_second_24.setTextColor(Color.WHITE);

        start.setClickable(true);
        pause.setClickable(false);
        clean_24.setClickable(false);
        clean_14.setClickable(true);

        start.setTextColor(Color.WHITE);
        pause.setTextColor(getResources().getColor(R.color.teal_700));
        clean_24.setTextColor(getResources().getColor(R.color.teal_700));
        clean_14.setTextColor(Color.WHITE);

    }

    private void pauseTime() {
        //Log.e("TAGMain", "pauseTime()");

        mHandler.sendEmptyMessage(2);

        start.setClickable(true);
        pause.setClickable(false);
        clean_24.setClickable(true);
        clean_14.setClickable(true);

        start.setTextColor(Color.WHITE);
        pause.setTextColor(getResources().getColor(R.color.teal_700));
        clean_24.setTextColor(Color.WHITE);
        clean_14.setTextColor(Color.WHITE);
    }

    private void startTime() {
        //Log.e("TAGMain", "startTime()");

        start.setClickable(false);
        pause.setClickable(true);
        clean_24.setClickable(true);
        clean_14.setClickable(true);

        start.setTextColor(getResources().getColor(R.color.teal_700));
        pause.setTextColor(Color.WHITE);
        clean_24.setTextColor(Color.WHITE);
        clean_14.setTextColor(Color.WHITE);

        if (text_second_24.getTag() == null) {
            text_second_24.setTag(24);//24S
        }

        if (text_minute_all.getTag() == null) {
            text_minute_all.setTag(600);//10分钟
        }

        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    public static String formatSecondsAsMinutesSeconds(int seconds) {
        // 将秒数转换为毫秒
        long milliseconds = TimeUnit.SECONDS.toMillis(seconds);
        // 使用SimpleDateFormat来格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        // 将毫秒转换为Date对象
        Date time = new Date(milliseconds);
        // 格式化并返回时间字符串
        return formatter.format(time);
    }

    private void allTimeEnd() {
        //Log.e("TAGMain", "allTimeEnd()");

        mHandler.removeMessages(1);
        mHandler.removeMessages(2);

        start.setClickable(false);
        pause.setClickable(false);
        clean_24.setClickable(false);
        clean_14.setClickable(false);

        start.setTextColor(getResources().getColor(R.color.teal_700));
        pause.setTextColor(getResources().getColor(R.color.teal_700));
        clean_24.setTextColor(getResources().getColor(R.color.teal_700));
        clean_14.setTextColor(getResources().getColor(R.color.teal_700));
    }

    private void game24TimeEnd() {
        //Log.e("TAGMain", "game24TimeEnd()");

        mHandler.removeMessages(1);
        mHandler.removeMessages(2);

        start.setClickable(true);
        pause.setClickable(false);
        clean_24.setClickable(true);
        clean_14.setClickable(true);

        start.setTextColor(Color.WHITE);
        pause.setTextColor(getResources().getColor(R.color.teal_700));
        clean_24.setTextColor(Color.WHITE);
        clean_14.setTextColor(Color.WHITE);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {//start
                Object second24Tag = text_second_24.getTag();
                Object minuteAllTag = text_minute_all.getTag();
                //Log.e("TAGMain", "handleMessage second24Tag:" + second24Tag + "，minuteAllTag：" + minuteAllTag);

                if (second24Tag != null && minuteAllTag != null) {
                    int time = (int) second24Tag;
                    text_second_24.setTag(--time);

                    int timeAll = (int) minuteAllTag;
                    text_minute_all.setTag(--timeAll);

                    if (timeAll <= 0) {
                        text_minute_all.setText("0");
                        text_minute_all.setTextColor(Color.RED);
                        text_minute_all.setTag(null);
                        text_second_24.setText("0");
                        text_second_24.setTextColor(Color.RED);
                        text_second_24.setTag(null);

                        allTimeEnd();
                        openMediaWhenTimeOut();
                    } else if (time <= 0) {
                        text_second_24.setText("0");
                        text_second_24.setTextColor(Color.RED);
                        String formatSecondsAsMinutesSeconds = formatSecondsAsMinutesSeconds(timeAll);
                        text_minute_all.setText(formatSecondsAsMinutesSeconds);
                        text_second_24.setTag(timeAll < 24 ? timeAll : 24);

                        game24TimeEnd();
                        openMediaWhenTimeOut();
                    } else {
                        text_second_24.setTextColor(Color.WHITE);
                        text_minute_all.setTextColor(Color.WHITE);
                        text_second_24.setText(String.valueOf(time));
                        text_minute_all.setText(formatSecondsAsMinutesSeconds(timeAll));

                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    }

                    //Log.e("TAGMain", "text_second_24:" + text_second_24.getText()+ "，text_minute_all：" + text_minute_all.getText());
                }
            } else if (msg.what == 2) {//pause
                mHandler.removeMessages(1);
            } else if (msg.what == 3) {//media release
                mediaRelease();
            }
        }
    };


}
