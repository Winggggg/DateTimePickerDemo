package com.gosuncn.mydatetimepicker.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by weiye on 2018/5/6 0006.
 */

public class MonthDayAndWeekPicker extends WheelPicker<String>{

    private static final String TAG = MonthDayAndWeekPicker.class.getSimpleName();
    private static final String TADAY="   今天  ";

    private String mMonthDayAndWeekSelected;
    private OnMonthDayAndWeekSelectedListener mOnMonthDayAndWeekSelectedListener;
    /**
     * 当前被选中的下标标的位置
     */
    private int mCurrentSelectPosition;


    public MonthDayAndWeekPicker(Context context) {
        this(context,null);
    }

    public MonthDayAndWeekPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MonthDayAndWeekPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setItemMaximumWidthText("00年00月 周日");
        updateMonthDayAndWeekStr();

        int month=Calendar.getInstance().get(Calendar.MONTH)+1;
        int day=Calendar.getInstance().get(Calendar.DATE);
        mMonthDayAndWeekSelected=getmMonthDayAndWeek(month,day,Calendar.getInstance());

        setSelectedMonthDayAndWeek(mMonthDayAndWeekSelected,false);

        setOnWheelChangeListener(new OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                mMonthDayAndWeekSelected = item;
                mCurrentSelectPosition=position;
                Log.e(TAG,"onWheelSelected  mCurrentSelectPosition="+mCurrentSelectPosition);
                if (mOnMonthDayAndWeekSelectedListener != null) {
                    mOnMonthDayAndWeekSelectedListener.onMonthDayAndWeekSelected(mMonthDayAndWeekSelected);
                }
            }
        });
    }

    private void updateMonthDayAndWeekStr() {
        List<String> list=new ArrayList<>();
        int mDay=0;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        Calendar calendar=Calendar.getInstance();
        int currentYear= calendar.get(Calendar.YEAR);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time="";
        for (int i = 1; i <= 12; i++) {

            if (i==1||i==3||i==5||i==7||i==8||i==10||i==12){
                mDay=31;
            }else if (i==4||i==6||i==9||i==11){
                mDay=30;
            }else if (i==2){
                mDay=28;
            }
            for (int j=1;j<=mDay;j++){
                try {
                    time=currentYear+"-"+numberFormat.format(i)+"-"+numberFormat.format(j);
                    calendar.setTime(format.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String datatime=getmMonthDayAndWeek(i,j,calendar);
                list.add(datatime);
            }
        }
        setDataList(list);
    }

    public String getmMonthDayAndWeek(int month,int day,Calendar calendar){
        String datatime="";

        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                datatime=month+"月"+day+"日 "+"周日";
                break;
            case 2:
                datatime=month+"月"+day+"日 "+"周一";
                break;
            case 3:
                datatime=month+"月"+day+"日 "+"周二";
                break;
            case 4:
                datatime=month+"月"+day+"日 "+"周三";
                break;
            case 5:
                datatime=month+"月"+day+"日 "+"周四";
                break;
            case 6:
                datatime=month+"月"+day+"日 "+"周五";
                break;
            case 7:
                datatime=month+"月"+day+"日 "+"周六";
                break;
        }
        return datatime;
    }

    public String getMonthDayAndWeekSelected() {
        return mMonthDayAndWeekSelected;
    }


    public void setSelectedMonthDayAndWeek(String SelectedMonthDayAndWeek) {
        setSelectedMonthDayAndWeek(SelectedMonthDayAndWeek, true);
    }

    public void setSelectedMonthDayAndWeek(String SelectedMonthDayAndWeek, boolean smoothScroll) {
        Log.e(TAG,"position="+getDataList().indexOf(SelectedMonthDayAndWeek));

        int month=Calendar.getInstance().get(Calendar.MONTH)+1;
        int day=Calendar.getInstance().get(Calendar.DATE);
        String MonthDayAndWeek=getmMonthDayAndWeek(month,day,Calendar.getInstance());
        int currentPosition=getDataList().indexOf(SelectedMonthDayAndWeek);
        mCurrentSelectPosition=currentPosition;
        if (MonthDayAndWeek.equals(SelectedMonthDayAndWeek)){
            getDataList().remove(currentPosition);
            getDataList().add(currentPosition,TADAY);
        }
        setCurrentPosition(currentPosition, smoothScroll);

    }


    public int getSelectedDay() {
        if (mMonthDayAndWeekSelected.equals(TADAY)){
            return Calendar.getInstance().get(Calendar.DATE);
        }else{
            String day=mMonthDayAndWeekSelected.substring(mMonthDayAndWeekSelected.indexOf("月")+1,mMonthDayAndWeekSelected.indexOf("日"));
            return Integer.parseInt(day);
        }
    }

    public int getSelectedMonth() {
        //Log.e(TAG,"getSelectedMonth "+mMonthDayAndWeekSelected);
        if (mMonthDayAndWeekSelected.equals(TADAY)){
            return Calendar.getInstance().get(Calendar.MONTH)+1;
        }else{
            String month=mMonthDayAndWeekSelected.substring(0,mMonthDayAndWeekSelected.indexOf("月"));
            return Integer.parseInt(month);
        }
    }

    public void setmOnMonthDayAndWeekSelectedListener(OnMonthDayAndWeekSelectedListener mOnMonthDayAndWeekSelectedListener) {
        this.mOnMonthDayAndWeekSelectedListener = mOnMonthDayAndWeekSelectedListener;
    }


    public void changePosition(boolean isAdd,boolean smoothScroll) {
        Log.e(TAG,"mCurrentSelectPosition="+mCurrentSelectPosition);

        if (isAdd){
            //前进循环
            if(mCurrentSelectPosition+1==getDataList().size()) mCurrentSelectPosition=1;
            setCurrentPosition(mCurrentSelectPosition+1,smoothScroll);
        }else{
            //倒退循环
            if (mCurrentSelectPosition==0) mCurrentSelectPosition=getDataList().size();
            setCurrentPosition(mCurrentSelectPosition-1,smoothScroll);
        }
    }

    public interface OnMonthDayAndWeekSelectedListener {
        void onMonthDayAndWeekSelected(String MonthDayAndWeek);
    }
}
