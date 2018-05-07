package com.gosuncn.mydatetimepicker.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * MinutePicker
 * Created by weiye on 2018/5/6.
 */
public class MinutePicker extends WheelPicker<Integer> {
    private static final String TAG = MinutePicker.class.getSimpleName();
    private OnMinuteSelectedListener mOnMinuteSelectedListener;

    private  int mSelectedMinute;
    /**
     * 当前被选中的下标标的位置
     */
    private int mCurrentposition;

    public MinutePicker(Context context) {
        this(context, null);
    }

    public MinutePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MinutePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        setDataFormat(numberFormat);
        mSelectedMinute= Calendar.getInstance().get(Calendar.MINUTE);
        Log.e(TAG,"mSelectedMinute="+mSelectedMinute);
        updateMinute();
        setSelectedMinute(mSelectedMinute,false);
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                mCurrentposition=position;
                mSelectedMinute=item;
                if (mOnMinuteSelectedListener != null) {
                    mOnMinuteSelectedListener.onMinuteSelected(item);
                }
            }
        });
    }

    private void updateMinute() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add(i);
        }
        setDataList(list);
    }

    public int getSelectedMinute() {
        return mSelectedMinute;
    }

    public void setSelectedMinute(int hour) {
        setSelectedMinute(hour, true);
    }

    public void setSelectedMinute(int minute, boolean smootScroll) {
        mCurrentposition=minute;
        setCurrentPosition(minute, smootScroll);
    }

    public void setOnMinuteSelectedListener(OnMinuteSelectedListener onMinuteSelectedListener) {
        mOnMinuteSelectedListener = onMinuteSelectedListener;
    }

    public void changePosition(boolean isAdd, boolean smootScroll) {
        if (isAdd){
            //前进循环
            if(mCurrentposition+1==getDataList().size()) mCurrentposition=-1;
            setCurrentPosition(mCurrentposition+1,smootScroll);
        }else{
            //倒退循环
            if (mCurrentposition==0) mCurrentposition=getDataList().size();
            setCurrentPosition(mCurrentposition-1,smootScroll);
        }
    }

    public interface OnMinuteSelectedListener {
        void onMinuteSelected(int hour);
    }
}
