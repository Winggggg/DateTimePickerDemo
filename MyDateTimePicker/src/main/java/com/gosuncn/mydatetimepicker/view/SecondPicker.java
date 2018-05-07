package com.gosuncn.mydatetimepicker.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by weiye on 2018/5/6 0006.
 */

public class SecondPicker extends WheelPicker<Integer> {



    private  OnSecondSelectedListener mOnSecondSelectedListener;
    private int mSelectedSecond;

    public SecondPicker(Context context) {
        this(context,null);
    }

    public SecondPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SecondPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        setDataFormat(numberFormat);
        mSelectedSecond= Calendar.getInstance().get(Calendar.SECOND);
        updateSecond();
        setSelectedSecond(mSelectedSecond,false);
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                mSelectedSecond=item;
                if (mOnSecondSelectedListener != null) {
                    mOnSecondSelectedListener.onSecondSelected(item);
                }
            }
        });
    }

    private void updateSecond() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add(i);
        }
        setDataList(list);
    }



    public void setmOnSecondSelectedListener(OnSecondSelectedListener mOnSecondSelectedListener) {
        this.mOnSecondSelectedListener = mOnSecondSelectedListener;
    }

    public int getSelectedSecond() {
        return mSelectedSecond;
    }

    public void setSelectedSecond(int second) {
        setSelectedSecond(second, true);
    }

    public void setSelectedSecond(int second, boolean smootScroll) {
        setCurrentPosition(second, smootScroll);
    }



    public interface OnSecondSelectedListener{
        void onSecondSelected(Integer second);
    }


}
