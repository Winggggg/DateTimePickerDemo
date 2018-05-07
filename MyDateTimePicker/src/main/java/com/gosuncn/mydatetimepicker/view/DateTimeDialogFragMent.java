package com.gosuncn.mydatetimepicker.view;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.gosuncn.mydatetimepicker.R;


/**
 * Created by weiye on 2018/5/7 0007.
 * 日期选择器弹出框
 */

public class DateTimeDialogFragMent extends DialogFragment {

    private DateTimePicker mDateTimePicker;
    private Button okButton,cancelButton;
    private OnDateChooseListener mOnDateChooseListener;

    public boolean ismIsShowAnimation() {
        return mIsShowAnimation;
    }

    public void setmIsShowAnimation(boolean mIsShowAnimation) {
        this.mIsShowAnimation = mIsShowAnimation;
    }

    private boolean mIsShowAnimation = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_datetime,container);
        mDateTimePicker=view.findViewById(R.id.dialog_dateTimePicker);
        okButton=view.findViewById(R.id.okButton);
        cancelButton=view.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnDateChooseListener != null) {
                    mOnDateChooseListener.onDateChoose(mDateTimePicker.getYear(),
                            mDateTimePicker.getMonth(), mDateTimePicker.getDay(),
                            mDateTimePicker.getHour(),mDateTimePicker.getMinute(),
                            mDateTimePicker.getSecond());
                }
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        initChildView();

        return view;
    }

    private void initChildView() {
        mDateTimePicker.setCyclic(true);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.DatePickerBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.dialog_datetime);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        Window window = dialog.getWindow();
        if (window != null) {
            if (mIsShowAnimation) {
                window.getAttributes().windowAnimations = R.style.DatePickerDialogAnim;
            }
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            lp.dimAmount = 0.35f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        return dialog;
    }


    public void setOnDateChooseListener(OnDateChooseListener onDateChooseListener) {
        mOnDateChooseListener = onDateChooseListener;
    }
    public interface OnDateChooseListener{
        void onDateChoose(int year, int month, int day, int hour, int minute, int second);
    }
}
