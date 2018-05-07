package com.gosuncn.mydatetimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gosuncn.mydatetimepicker.view.DateTimeDialogFragMent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * 弹出时间选择框
     * @param view
     */
    public void showDialog(View view){
        DateTimeDialogFragMent dateTimeDialogFragMent=new DateTimeDialogFragMent();
        dateTimeDialogFragMent.setOnDateChooseListener(new DateTimeDialogFragMent.OnDateChooseListener() {
            @Override
            public void onDateChoose(int year, int month, int day, int hour, int minute, int second) {
                Toast.makeText(getApplicationContext(), year + "年" + month + "月" + day+"日"+hour
                        +"时"+minute+"分"+second+"秒", Toast.LENGTH_SHORT).show();

            }
        });

        dateTimeDialogFragMent.show(getSupportFragmentManager(),"DateTimeDialogFragMent");
    }
}
