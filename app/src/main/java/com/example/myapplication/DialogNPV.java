package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class DialogNPV extends Dialog implements View.OnClickListener, NumberPickerView.OnScrollListener, NumberPickerView.OnValueChangeListener, View.OnLongClickListener {

    public interface Callback {
        void onResult(int minute, int second);
    }

    private static final String TAG = "picker";
    private Context mContext;
    private Button mButtonGetInfo;
    private NumberPickerView mPickerMinuteView;
    private NumberPickerView mPickerSecondView;
    public Callback mCallback;

    public DialogNPV(Context context) {
        super(context, R.style.dialog);
        mContext = context;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_npv);

        mPickerMinuteView = findViewById(R.id.picker_minute);
        mPickerSecondView = findViewById(R.id.picker_second);
        mPickerMinuteView.setOnScrollListener(this);
        mPickerMinuteView.setOnValueChangedListener(this);
//        mNumberPickerView.refreshByNewDisplayedValues(mDisplayValues);
        mPickerSecondView.setOnScrollListener(this);
        mPickerSecondView.setOnValueChangedListener(this);
//        mPickerSecondView.refreshByNewDisplayedValues(mDisplayValues);

        mButtonGetInfo = findViewById(R.id.button_get_info);
        mButtonGetInfo.setOnClickListener(this);
        mButtonGetInfo.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get_info:
                getCurrentContent();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int minute = mPickerMinuteView.getValue();
        int second = mPickerSecondView.getValue();
        if (mCallback != null) {
            mCallback.onResult(minute, second);
        }
        return true;
    }

    @Override
    public void onScrollStateChange(NumberPickerView view, int scrollState) {
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
    }

    private void getCurrentContent() {
        int minute = mPickerMinuteView.getValue();
        int second = mPickerSecondView.getValue();
        Toast.makeText(mContext, "时间:" + minute + " 分 " + second + " 秒 ,长按按钮设置", Toast.LENGTH_SHORT).show();
    }

}