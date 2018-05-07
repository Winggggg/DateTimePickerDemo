package com.gosuncn.mydatetimepicker.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * HourPicker
 * Created by weiye on 2018/5/6.
 */
public class HourPicker extends WheelPicker<Integer>{

    private static final String TAG = HourPicker.class.getSimpleName();
    private OnHourSelectedListener mOnHourSelectedListener;

    private int mSelectHour;
    /**
     * 当前被选中的下标标的位置
     */
    private int mCurrentposition;

    public HourPicker(Context context) {
        this(context, null);
    }

    public HourPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        setDataFormat(numberFormat);

        SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
        String time=f.format(new Date());
//        mSelectHour= Calendar.getInstance().get(Calendar.HOUR);
        //获取24小时制的小时
        mSelectHour=Integer.parseInt(time.substring(0,2));
        Log.e(TAG,"mSelectHour="+mSelectHour);

        updateHour();
        setSelectedHour(mSelectHour,false);

        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                Log.e(TAG,"onWheelSelected hour="+item+"--position="+position);
                mCurrentposition=position;
                mSelectHour=item;
                if (mOnHourSelectedListener != null) {
                    mOnHourSelectedListener.onHourSelected(item);
                }
            }
        });
    }

    private void updateHour() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(i);
        }
        setDataList(list);
    }

    public int getSelectedHour(){
        return mSelectHour;
    }

    public void setSelectedHour(int hour) {
        setSelectedHour(hour, true);
    }

    public void setSelectedHour(int hour, boolean smootScroll) {
        mCurrentposition=hour;
        setCurrentPosition(hour, smootScroll);
    }

    public void setOnHourSelectedListener(OnHourSelectedListener onHourSelectedListener) {
        mOnHourSelectedListener = onHourSelectedListener;
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

    public interface OnHourSelectedListener {
        void onHourSelected(int hour);
    }
}
