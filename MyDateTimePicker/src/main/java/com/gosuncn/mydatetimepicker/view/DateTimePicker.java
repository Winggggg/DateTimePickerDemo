package com.gosuncn.mydatetimepicker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.gosuncn.mydatetimepicker.R;

import java.util.Calendar;

/**
 * Created by weiye on 2018/5/6 0006.
 * 自定义时间选择器
 */

public class DateTimePicker extends LinearLayout implements MonthDayAndWeekPicker.OnMonthDayAndWeekSelectedListener,
        HourPicker.OnHourSelectedListener,MinutePicker.OnMinuteSelectedListener,SecondPicker.OnSecondSelectedListener{


    private static  final String TAG=DateTimePicker.class.getSimpleName();
    private MonthDayAndWeekPicker mMonthDayAndWeekPicker;
    private HourPicker mHourPicker;
    private MinutePicker mMinutePicker;
    private SecondPicker mSecondPicker;
    private OnDateSelectedListener mOnDateSelectedListener;
    private int mSelectedBeforeHour = -1, mSelectedBeforeMinute = -1, mSelectedBeforeSecond = -1;


    public DateTimePicker(Context context) {
        this(context,null);
    }

    public DateTimePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DateTimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_datatime,this);
        initChild();
        initAttr(context,attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs==null){return;}
        TypedArray a=context.obtainStyledAttributes(R.styleable.DateTimePicker);
        int textsize=a.getDimensionPixelSize(R.styleable.DateTimePicker_itemTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));

        int textColor = a.getColor(R.styleable.DateTimePicker_itemTextColor,
                Color.GRAY);
        boolean isTextGradual = a.getBoolean(R.styleable.DateTimePicker_textGradual, true);

        boolean isCyclic = a.getBoolean(R.styleable.DateTimePicker_wheelCyclic, false);

        int halfVisibleItemCount = a.getInteger(R.styleable.DateTimePicker_halfVisibleItemCount, 3);

        int selectedItemTextColor = a.getColor(R.styleable.DateTimePicker_selectedTextColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_selectedTextColor));

        int selectedItemTextSize = a.getDimensionPixelSize(R.styleable.DateTimePicker_selectedTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));

        int itemWidthSpace = a.getDimensionPixelSize(R.styleable.DateTimePicker_itemWidthSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));

        int itemHeightSpace = a.getDimensionPixelSize(R.styleable.DateTimePicker_itemHeightSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));

        boolean isZoomInSelectedItem = a.getBoolean(R.styleable.DateTimePicker_zoomInSelectedItem, true);

        boolean isShowCurtain = a.getBoolean(R.styleable.DateTimePicker_wheelCurtain, true);

        int curtainColor = a.getColor(R.styleable.DateTimePicker_wheelCurtainColor, Color.WHITE);

        boolean isShowCurtainBorder = a.getBoolean(R.styleable.DateTimePicker_wheelCurtainBorder, true);

        int curtainBorderColor = a.getColor(R.styleable.DateTimePicker_wheelCurtainBorderColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_divider));
        a.recycle();

        setTextSize(textsize);
        setTextColor(textColor);
        setTextGradual(isTextGradual);
        setCyclic(isCyclic);
        setHalfVisibleItemCount(halfVisibleItemCount);
        setSelectedItemTextColor(selectedItemTextColor);
        setSelectedItemTextSize(selectedItemTextSize);
        setItemWidthSpace(itemWidthSpace);
        setItemHeightSpace(itemHeightSpace);
        setZoomInSelectedItem(isZoomInSelectedItem);
        setShowCurtain(isShowCurtain);
        setCurtainColor(curtainColor);
        setShowCurtainBorder(isShowCurtainBorder);
        setCurtainBorderColor(curtainBorderColor);
    }


    private void initChild() {
        mMonthDayAndWeekPicker=findViewById(R.id.monthDayAndWeekPicker_layout_date);
        mHourPicker=findViewById(R.id.hourPicker_layout_date);
        mMinutePicker=findViewById(R.id.minutePicker_layout_date);
        mSecondPicker=findViewById(R.id.secondPicker_layout_date);

        mMonthDayAndWeekPicker.setmOnMonthDayAndWeekSelectedListener(this);
        mHourPicker.setOnHourSelectedListener(this);
        mMinutePicker.setOnMinuteSelectedListener(this);
        mSecondPicker.setmOnSecondSelectedListener(this);

        mSelectedBeforeHour=mHourPicker.getSelectedHour();
        mSelectedBeforeMinute=mMinutePicker.getSelectedMinute();
        mSelectedBeforeSecond=mSecondPicker.getSelectedSecond();
    }

    private void onDateSelected() {
        if (mOnDateSelectedListener != null) {
            mOnDateSelectedListener.onDateSelected(getYear(),
                    getMonth(), getDay(),getHour(),getMinute(),getSecond());
        }
    }

    public int getDay() {
        return mMonthDayAndWeekPicker.getSelectedDay();
    }

    public int getMonth() {
        return mMonthDayAndWeekPicker.getSelectedMonth();
    }

    @Override
    public void onHourSelected(int hour) {
        Log.e(TAG,"onHourSelected hour="+hour+"--mSelectedBeforeHour="+mSelectedBeforeHour+" 往上滑动"+mHourPicker.isMoveForUp()+
                "--istouch="+mHourPicker.isTouch());
        if (mHourPicker.isTouch()){
            mHourPicker.setTouch(false);
            if (mSelectedBeforeHour-hour>0&&mHourPicker.isMoveForUp()){
                //进一
                mMonthDayAndWeekPicker.changePosition(true,false);
            }else if (hour-mSelectedBeforeHour>0&&!mHourPicker.isMoveForUp()){
                //退一
                mMonthDayAndWeekPicker.changePosition(false,false);
            }
        }
        mSelectedBeforeHour=hour;
        onDateSelected();
    }

    @Override
    public void onMinuteSelected(int minute) {
        if (mMinutePicker.isTouch()){
            Log.e(TAG,"onMinuteSelected minute="+minute+"--mSelectedBeforeMinute="+mSelectedBeforeMinute+" 往上滑动"+mMinutePicker.isMoveForUp()+
                    "--istouch="+mMinutePicker.isTouch());
            mMinutePicker.setTouch(false);
            if (mSelectedBeforeMinute-minute>0&&mMinutePicker.isMoveForUp()){
                //进一
                mHourPicker.changePosition(true,false);
            }else if (minute-mSelectedBeforeMinute>0&&!mMinutePicker.isMoveForUp()){
                //退一
                mHourPicker.changePosition(false,false);
            }
        }
        mSelectedBeforeMinute=minute;
        onDateSelected();
    }

    @Override
    public void onSecondSelected(Integer second) {
        if (mSecondPicker.isTouch()){
            Log.e(TAG,"onSecondSelected second="+second+"--mSelectedBeforeSecond="+mSelectedBeforeSecond+" 往上滑动"+mSecondPicker.isMoveForUp()+
                    "--istouch="+mSecondPicker.isTouch());
            mSecondPicker.setTouch(false);
            if (mSelectedBeforeSecond-second>0&&mSecondPicker.isMoveForUp()){
                //进一
                mMinutePicker.changePosition(true,false);
            }else if (second-mSelectedBeforeSecond>0&&!mSecondPicker.isMoveForUp()){
                //退一
                mMinutePicker.changePosition(false,false);
            }
        }
        mSelectedBeforeSecond=second;
        onDateSelected();
    }

    @Override
    public void onMonthDayAndWeekSelected(String MonthDayAndWeek) {
        onDateSelected();
    }


    /**
     * 设置时间
     * @param month
     * @param day
     * @param week
     * @param hour
     * @param minute
     * @param second
     */
    public void setDateTime( int month, int day, String week,int hour,int minute,int second) {
        setDateTime( month,  day,  week, hour, minute, second);
    }

    /**
     * 设置时间
     * @param month
     * @param day
     * @param week  如 “一”、“二”............“日”
     * @param hour
     * @param minute
     * @param second
     * @param smoothScroll
     */
    public void setDateTime(int month, int day, String week,int hour,int minute,int second, boolean smoothScroll) {
       mMonthDayAndWeekPicker.setSelectedMonthDayAndWeek(month+"月"+day+"日  周"+week,smoothScroll);
       mHourPicker.setSelectedHour(hour,smoothScroll);
       mMinutePicker.setSelectedMinute(minute,smoothScroll);
        mSecondPicker.setSelectedSecond(second,smoothScroll);
    }

    /**
     * 获取具体时间
     *
     * @return the date
     */
    public String getDate() {
        int year,hour,minute,second;
        String monthDayWeek;
        year = getYear();
        monthDayWeek=getMonthDayWeek();
        hour=getHour();
        minute=getMinute();
        second=getSecond();
        return year+"年"+monthDayWeek+hour+"时"+minute+"分"+second+"秒";
    }

    private String getMonthDayWeek() {
        return mMonthDayAndWeekPicker.getMonthDayAndWeekSelected();
    }

    public int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    /**
     * 获取 MonthDayAndWeek.
     *
     * @return the MonthDayAndWeek
     */
    public String getMonthDayAndWeek() {
        return mMonthDayAndWeekPicker.getMonthDayAndWeekSelected();
    }

    /**
     * Gets Hour.
     *
     * @return the Hour
     */
    public int getHour() {
        return mHourPicker.getSelectedHour();
    }

    /**
     * Gets Minute.
     *
     * @return the Minute
     */
    public int getMinute() {
        return mMinutePicker.getSelectedMinute();
    }

    /**
     * Gets second.
     *
     * @return the second
     */
    public int getSecond() {
        return mSecondPicker.getSelectedSecond();
    }



    /**
     * 获取日期选择器
     * @return
     */
    public MonthDayAndWeekPicker getmMonthDayAndWeekPicker() {
        return mMonthDayAndWeekPicker;
    }

    /**
     * 获取小时选择器
     * @return
     */
    public HourPicker getmHourPicker() {
        return mHourPicker;
    }

    /**
     * 获取分钟选择器
     * @return
     */
    public MinutePicker getmMinutePicker() {
        return mMinutePicker;
    }

    /**
     * 获取秒选择器
     * @return
     */
    public SecondPicker getmSecondPicker() {
        return mSecondPicker;
    }


    /**
     * 一般列表的文本颜色
     *
     * @param textColor 文本颜色
     */
    public void setTextColor(@ColorInt int textColor) {
        mMonthDayAndWeekPicker.setTextColor(textColor);
        mHourPicker.setTextColor(textColor);
        mMinutePicker.setTextColor(textColor);
        mSecondPicker.setTextColor(textColor);
    }

    /**
     * 一般列表的文本大小
     *
     * @param textSize 文字大小
     */
    public void setTextSize(int textSize) {
        mMonthDayAndWeekPicker.setTextSize(textSize);
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
        mSecondPicker.setTextSize(textSize);
    }

    /**
     * 设置被选中时候的文本颜色
     *
     * @param selectedItemTextColor 文本颜色
     */
    public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
        mMonthDayAndWeekPicker.setSelectedItemTextColor(selectedItemTextColor);
        mHourPicker.setSelectedItemTextColor(selectedItemTextColor);
        mMinutePicker.setSelectedItemTextColor(selectedItemTextColor);
        mSecondPicker.setSelectedItemTextColor(selectedItemTextColor);
    }

    /**
     * 设置被选中时候的文本大小
     *
     * @param selectedItemTextSize 文字大小
     */
    public void setSelectedItemTextSize(int selectedItemTextSize) {
        mMonthDayAndWeekPicker.setSelectedItemTextSize(selectedItemTextSize);
        mHourPicker.setSelectedItemTextSize(selectedItemTextSize);
        mMinutePicker.setSelectedItemTextSize(selectedItemTextSize);
        mSecondPicker.setSelectedItemTextSize(selectedItemTextSize);
    }


    /**
     * 设置显示数据量的个数的一半。
     * 为保证总显示个数为奇数,这里将总数拆分，itemCount = mHalfVisibleItemCount * 2 + 1
     *
     * @param halfVisibleItemCount 总数量的一半
     */
    public void setHalfVisibleItemCount(int halfVisibleItemCount) {
        mMonthDayAndWeekPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mHourPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mMinutePicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mSecondPicker.setHalfVisibleItemCount(halfVisibleItemCount);
    }

    /**
     * Sets item width space.
     *
     * @param itemWidthSpace the item width space
     */
    public void setItemWidthSpace(int itemWidthSpace) {
        mMonthDayAndWeekPicker.setItemWidthSpace(itemWidthSpace);
        mHourPicker.setItemWidthSpace(itemWidthSpace);
        mMinutePicker.setItemWidthSpace(itemWidthSpace);
        mSecondPicker.setItemWidthSpace(itemWidthSpace);
    }

    /**
     * 设置两个Item之间的间隔
     *
     * @param itemHeightSpace 间隔值
     */
    public void setItemHeightSpace(int itemHeightSpace) {
        mMonthDayAndWeekPicker.setItemHeightSpace(itemHeightSpace);
        mHourPicker.setItemHeightSpace(itemHeightSpace);
        mMinutePicker.setItemHeightSpace(itemHeightSpace);
        mSecondPicker.setItemHeightSpace(itemHeightSpace);
    }


    /**
     * Set zoom in center item.
     *
     * @param zoomInSelectedItem the zoom in center item
     */
    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        mMonthDayAndWeekPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mHourPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mMinutePicker.setZoomInSelectedItem(zoomInSelectedItem);
        mSecondPicker.setZoomInSelectedItem(zoomInSelectedItem);
    }

    /**
     * 设置是否循环滚动。
     * set wheel cyclic
     * @param cyclic 上下边界是否相邻
     */
    public void setCyclic(boolean cyclic) {
        mMonthDayAndWeekPicker.setCyclic(cyclic);
        mHourPicker.setCyclic(cyclic);
        mMinutePicker.setCyclic(cyclic);
        mSecondPicker.setCyclic(cyclic);
    }

    /**
     * 设置文字渐变，离中心越远越淡。
     * Set the text color gradient
     * @param textGradual 是否渐变
     */
    public void setTextGradual(boolean textGradual) {
        mMonthDayAndWeekPicker.setTextGradual(textGradual);
        mHourPicker.setTextGradual(textGradual);
        mMinutePicker.setTextGradual(textGradual);
        mSecondPicker.setTextGradual(textGradual);
    }


    /**
     * 设置中心Item是否有幕布遮盖
     * set the center item curtain cover
     * @param showCurtain 是否有幕布
     */
    public void setShowCurtain(boolean showCurtain) {
        mMonthDayAndWeekPicker.setShowCurtain(showCurtain);
        mHourPicker.setShowCurtain(showCurtain);
        mMinutePicker.setShowCurtain(showCurtain);
        mSecondPicker.setShowCurtain(showCurtain);
    }

    /**
     * 设置幕布颜色
     * set curtain color
     * @param curtainColor 幕布颜色
     */
    public void setCurtainColor(@ColorInt int curtainColor) {
        mMonthDayAndWeekPicker.setCurtainColor(curtainColor);
        mHourPicker.setCurtainColor(curtainColor);
        mMinutePicker.setCurtainColor(curtainColor);
        mSecondPicker.setCurtainColor(curtainColor);
    }

    /**
     * 设置幕布是否显示边框
     * set curtain border
     * @param showCurtainBorder 是否有幕布边框
     */
    public void setShowCurtainBorder(boolean showCurtainBorder) {
        mMonthDayAndWeekPicker.setShowCurtainBorder(showCurtainBorder);
        mHourPicker.setShowCurtainBorder(showCurtainBorder);
        mMinutePicker.setShowCurtainBorder(showCurtainBorder);
        mSecondPicker.setShowCurtainBorder(showCurtainBorder);
    }

    /**
     * 幕布边框的颜色
     * curtain border color
     * @param curtainBorderColor 幕布边框颜色
     */
    public void setCurtainBorderColor(@ColorInt int curtainBorderColor) {
        mMonthDayAndWeekPicker.setCurtainBorderColor(curtainBorderColor);
        mHourPicker.setCurtainBorderColor(curtainBorderColor);
        mMinutePicker.setCurtainBorderColor(curtainBorderColor);
        mSecondPicker.setCurtainBorderColor(curtainBorderColor);
    }

    /**
     * 设置选择器的指示器文本
     * set indicator text
     * @param monthDayAndWeekText  指示器文本
     * @param hourText 时指示器文本
     * @param minuteText   分指示器文本
     * @param secondText   秒指示器文本
     */
    public void setIndicatorText(String monthDayAndWeekText, String hourText, String minuteText,String secondText) {
        mMonthDayAndWeekPicker.setIndicatorText(monthDayAndWeekText);
        mHourPicker.setIndicatorText(hourText);
        mMinutePicker.setIndicatorText(minuteText);
        mSecondPicker.setIndicatorText(secondText);
    }

    /**
     * 设置指示器文字的颜色
     * set indicator text color
     * @param textColor 文本颜色
     */
    public void setIndicatorTextColor(@ColorInt int textColor) {
        mMonthDayAndWeekPicker.setIndicatorTextColor(textColor);
        mHourPicker.setIndicatorTextColor(textColor);
        mMinutePicker.setIndicatorTextColor(textColor);
        mSecondPicker.setIndicatorTextColor(textColor);
    }

    /**
     * 设置指示器文字的大小
     *  indicator text size
     * @param textSize 文本大小
     */
    public void setIndicatorTextSize(int textSize) {
        mMonthDayAndWeekPicker.setTextSize(textSize);
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
        mSecondPicker.setTextSize(textSize);
    }

    /**
     * Sets on date selected listener.
     *
     * @param onDateSelectedListener the on date selected listener
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        mOnDateSelectedListener = onDateSelectedListener;
    }

    /**
     * The interface On date selected listener.
     */
    public interface OnDateSelectedListener {
        /**
         * On date selected.
         *
         * @param year  the year
         * @param month the month
         * @param day   the day
         */
        void onDateSelected(int year, int month, int day, int hour, int minute, int second);
    }
}
