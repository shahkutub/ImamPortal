package com.imamportal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imamportal.utils.AppConstant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class NamjTimeActivity extends AppCompatActivity {


    LinearLayout linFojor,linJohor,linAsor,linMagrib,linIsha,linNamajTime;
    private TextView tvFojorTime,tvJohorTime,tvAsorTime,tvMagribTime,tvIshaTime,tvReminderTime;

    Context context;


    String pryname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoge_namaj);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        context = this;
        //translate = Translator.getInstance();

        initUi();
        getPrayerTime();


    }




    ArrayList<Date> prayerTimesDate;
    ArrayList<String> prayerNames;
    private void getPrayerTime() {

        double latitude = 23.8103;
        double longitude = 90.4125;
        if(!TextUtils.isEmpty(AppConstant.lat)||!TextUtils.isEmpty(AppConstant.lng)){
            latitude = Double.parseDouble(AppConstant.lat);
            longitude = Double.parseDouble(AppConstant.lng);
        }


        double timezone = 6;
        // Test Prayer times here
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Hanafi);
        prayers.setAsrJuristic(prayers.Hanafi);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        final Calendar cal = Calendar.getInstance();
        int day=cal.get(Calendar.DATE);
        cal.setTime(now);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                latitude, longitude, timezone);
        cal.add(Calendar.DATE, 1);
        ArrayList<String> nextDayList = prayers.getPrayerTimes(cal,
                latitude, longitude, timezone);
        prayerTimes.add(nextDayList.get(0));
        prayerTimesDate = new ArrayList<>();
        int index = 0;
        for (String i : prayerTimes) {
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            String[] arr = TextUtils.split(i, "[\\s:]");
            String ampm = arr[2];
            int h = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            c.set(Calendar.AM_PM, ampm.equals("am") ? Calendar.AM : Calendar.PM);
            c.set(Calendar.HOUR, h);
            c.set(Calendar.MINUTE, m);
            c.set(Calendar.DATE, day);
            if (index == 7) c.add(Calendar.DATE, 1);
            prayerTimesDate.add(c.getTime());
            index++;
        }
        prayerTimesDate.remove(4);
        prayerTimesDate.remove(1);

        prayerNames = prayers.getTimeNames();
        prayerNames.remove(4);
        prayerNames.remove(1);





        tvFojorTime.setText(prayerTimes.get(0));
        tvJohorTime.setText(prayerTimes.get(2));
        tvAsorTime.setText(prayerTimes.get(3));
        tvMagribTime.setText(prayerTimes.get(5));
        tvIshaTime.setText(prayerTimes.get(6));

//        Calendar calendar = Calendar.getInstance();
////        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
////        int min = calendar.get(Calendar.MINUTE);
//
        DateFormat dateFormath = new SimpleDateFormat("dd");
        DateFormat dateFormatm = new SimpleDateFormat("mm");
        final int dd = Integer.parseInt(dateFormath.format(new Date()));
        int min = Integer.parseInt(dateFormatm.format(new Date()));
//
//        Log.e("diff",hour+"."+min);
//        Log.e("diff",""+prayers.floatToTime24(prayers.timeDiff(11.57, Double.parseDouble(hour+"."+min))));


//        long mills = d1.getTime() - date2.getTime();
//        long hours = mills/(1000 * 60 * 60);
//        long mins = (mills/(1000*60)) % 60;
//
//        String diff = hours + ":" + mins;
//
//        Log.e("diff",""+diff);


        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    final Date date1 = getNextPrayer();

                    final Date date2 = new Date();

                    long mills = date1.getTime() - date2.getTime();
                    int hours = (int) (mills / (1000 * 60 * 60));
                    int mins = (int) ((mills / (1000 * 60)) % 60);
                    int secs = (int) ((mills / (1000)) % 60);

                    final String diff = hours + ":" + mins+":"+secs; // updated value every1 second

                    Log.e("diff",""+diff);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update TextView here!
                            tvReminderTime.setText( pryname+":" + diff);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1000); // here 1000 means 1000 mills i.e. 1 second


    }


    private Date getNextPrayer() {
        Date now = new Date();
        Date nextPreyerTime = prayerTimesDate.get(0);
        //pryname=prayerNames.get(0);
        int size = prayerTimesDate.size();
        for (int i = 0; i < size - 1; i++) {
            if (prayerTimesDate.get(i).getTime() < now.getTime()) {
                nextPreyerTime = prayerTimesDate.get(i + 1);
                pryname=prayerNames.get(i + 1);

            } else {
                nextPreyerTime = prayerTimesDate.get(i);
                pryname=prayerNames.get(i);
                break;
            }

        }

        prayerNamesHighlite();

        return nextPreyerTime;
    }

    private void prayerNamesHighlite() {

        if(pryname.equalsIgnoreCase("ফজর")){
            linFojor.setBackgroundColor(Color.parseColor("#0286EA"));
            linJohor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linAsor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linMagrib.setBackgroundColor(Color.parseColor("#8CC63E"));
            linIsha.setBackgroundColor(Color.parseColor("#8CC63E"));

        }else if(pryname.equalsIgnoreCase("জোহর")){
            linFojor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linJohor.setBackgroundColor(Color.parseColor("#0286EA"));
            linAsor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linMagrib.setBackgroundColor(Color.parseColor("#8CC63E"));
            linIsha.setBackgroundColor(Color.parseColor("#8CC63E"));
        }else if(pryname.equalsIgnoreCase("আসর")){
            linFojor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linJohor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linAsor.setBackgroundColor(Color.parseColor("#0286EA"));
            linMagrib.setBackgroundColor(Color.parseColor("#8CC63E"));
            linIsha.setBackgroundColor(Color.parseColor("#8CC63E"));
        }else if(pryname.equalsIgnoreCase("মাগরিব")){
            linFojor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linJohor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linAsor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linMagrib.setBackgroundColor(Color.parseColor("#0286EA"));
            linIsha.setBackgroundColor(Color.parseColor("#8CC63E"));
        }else if(pryname.equalsIgnoreCase("ইশা")){
            linFojor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linJohor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linAsor.setBackgroundColor(Color.parseColor("#8CC63E"));
            linMagrib.setBackgroundColor(Color.parseColor("#8CC63E"));
            linIsha.setBackgroundColor(Color.parseColor("#0286EA"));
        }
    }





    private void initUi() {


        //tvReminderTime = (TextView) findViewById(R.id.tvReminderTime);

        linFojor = (LinearLayout) findViewById(R.id.linFojor);
        linAsor = (LinearLayout)  findViewById(R.id.linAsor);
        linIsha = (LinearLayout)  findViewById(R.id.linIsha);
        linJohor = (LinearLayout)  findViewById(R.id.linJohor);
        linMagrib = (LinearLayout)  findViewById(R.id.linMagrib);

        tvFojorTime = (TextView) findViewById(R.id.tvFojorTime);
        tvJohorTime = (TextView) findViewById(R.id.tvJohorTime);
        tvAsorTime = (TextView) findViewById(R.id.tvAsorTime);
        tvMagribTime = (TextView) findViewById(R.id.tvMagribTime);
        tvIshaTime = (TextView) findViewById(R.id.tvIshaTime);



    }






}
