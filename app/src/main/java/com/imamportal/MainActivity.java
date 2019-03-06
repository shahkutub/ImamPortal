package com.imamportal;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.Adapter.AdapteDate;
import com.imamportal.Adapter.KitabAdapter;
import com.imamportal.Adapter.SlidingViewPagerAdapter;
import com.imamportal.converter.BanglaDateUtils;
import com.imamportal.fragments.FragmentPhoto;
import com.imamportal.fragments.FragmentQuizBizoyee;
import com.imamportal.fragments.FragmentSeraContent;
import com.imamportal.model.KitabInfo;
import com.imamportal.model.NoticeResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistentUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.time4j.SystemClock;
import net.time4j.android.ApplicationStarter;
import net.time4j.calendar.HijriCalendar;
import net.time4j.engine.StartOfDay;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabMoulikBisoy, tabSopmadokio;
    private RelativeLayout relIslamMoulikBisoy, relTabSopmpadokio;
    String moulikTitle, detailMoulik;
    //private PagerTabsIndicator tabsIndicator;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    //private SearchView searchView;
    private ImageView imgSearch, imgCancel, imgGoSearch;
    private LinearLayout linSearch;
    //private MaterialSearchView search_view;
    AutoCompleteTextView autocoEditView;

    LinearLayout linNoticeview, linFojor, linJohor, linAsor, linMagrib, linIsha, linMsgFloating, linKitabsomuho,
            linDokhotaSomuho, linIslamMenus,
            linMasalaMenus, linAskQues, linBlog, linMasael, linQuesAns, linJobCirculer, linQuize,
            relSisukisur, relOnndhra, linIslam, linSompadokio, linNarikornar, linVideo, linAudio, linDokhotarGolpo,
            linKitab, linphotogalary, linAlQran, linAHadith, linSantirbani;
    //relKitab,relDokhota,relIslam,relMasala,relIslamicAin,relAlKural,relHadithGrontho,relProfile,
    RelativeLayout relSompadokio, relNotification, linAutoserch;
    TextView tvFojorTime, tvJohorTime, tvAsorTime, tvMagribTime, tvIshaTime, tvReminder;
    private ImageView imgKitabArow, imgDokhotaArow, imgIslamArow, imgMasala;
    String fajor, johor, asor, magrib, isha;
    private ViewPager launchViewpager, viewpagerDate;
    // int[] imageRSC = {R.drawable.mosqu, R.drawable.sl1, R.drawable.sl2};
    String[] names = {"sadi", "ALom", "Saroar"};
    String[] dates;
    Context context;
    private Timer swipeTimer;
    boolean isTimerRunning;
    Runnable Update, updateSlider;
    Handler handler;
    RecyclerView recycleViewKitab;
    KitabAdapter horizontalAdapter;
    //IsFundamentalsAdapter isFundamentalsAdapter;
    private List<KitabInfo> data = new ArrayList<>();
    //private List<KitabInfo> data2 = new ArrayList<>();
    String pryname, banglaDate;

    TextView tvTabDescription, tvSompadokioDes, tvDate, MarqueeText;

    FloatingActionButton fab, fabChatGroup, fabApnarPoramorso, fabNamaj, fabChat, fabGroup, fabPoramorso, fabMsg;
    LinearLayout fabLayout1, fabLayout2, fabLayoutChatGroup, fabLayout3, fabLayoutChat, fabLayoutGroup, fabLayoutPoramorso;
    View fabBGLayout;
    boolean isFABOpen = false;
    boolean isFABmsgOpen = false;
    private String searchStr;
    private LinearLayout linAmarPata, linRegistration,linImamtrainingReg,linVocationalTrainReg,linUpload;
    private TextView tvLogin, tvLogOut;

    List<NoticeResponse> listNotice = new ArrayList<>();
    private String seraContentdataName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        context = this;
        updateworkdone();
        ApplicationStarter.initialize(this, true);
        getBangladate();
        hijriDate();
        initDrawer();
        initUi();
        slideshow();
        fabinitUi();


    }


    public void shareApp(View v) {
        ShareCompat.IntentBuilder.from(MainActivity.this)
                .setType("text/plain")
                .setChooserTitle("Share App")
                .setText("https://www.nanosoftbd.com/imamportal/")
                .startChooser();

    }

    public void goponioNitimala(View v) {
        startActivity(new Intent(context, ActivityGoponiyoNitimala.class));

    }

    public void beboherSortaboli(View v) {
        startActivity(new Intent(context, ActivityBeboherSorto.class));

    }

    public void sochoracorjiggasa(View v) {
        startActivity(new Intent(context, ActivitySochorachorJiggsa.class));

    }


//    private void fabmsgUi() {
//
//        fabLayoutChat= (LinearLayout) findViewById(R.id.fabLayoutChat);
//        fabLayoutGroup= (LinearLayout) findViewById(R.id.fabLayoutGroup);
//        fabLayoutPoramorso= (LinearLayout) findViewById(R.id.fabLayoutPoramorso);
//
//
//        fabChat = (FloatingActionButton) findViewById(R.id.fabChat);
//        fabGroup= (FloatingActionButton) findViewById(R.id.fabGroup);
//        fabPoramorso = (FloatingActionButton) findViewById(R.id.fabPoramorso);
//
//        fabChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogeNamajTime();
//                closeFABmsgMenu();
//            }
//        });
//
//        fabBGLayout=findViewById(R.id.fabBGLayout);
//
//        relNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isFABmsgOpen){
//                    showFABmsgMenu();
//                }else{
//                    closeFABmsgMenu();
//                }
//            }
//        });
//        fabBGLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                closeFABmsgMenu();
//            }
//        });
//    }


    private void fabinitUi() {

        //fabLayout1= (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayoutChatGroup = (LinearLayout) findViewById(R.id.fabLayoutChatGroup);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabChatGroup = (FloatingActionButton) findViewById(R.id.fabChatGroup);
        fabApnarPoramorso = (FloatingActionButton) findViewById(R.id.fabApnarPoramorso);
        fabNamaj = (FloatingActionButton) findViewById(R.id.fabNamaj);

        fabApnarPoramorso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ApnarPoramorsoActivity.class));
                closeFABMenu();
            }
        });

        fabChatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ChatSearchActivity.class));
                closeFABMenu();
            }
        });


        fabNamaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogeNamajTime();
                //startActivity(new Intent(context,NamjTimeActivity.class));
                closeFABMenu();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });


    }

//    private void showFABmsgMenu(){
//        isFABmsgOpen=true;
//        fabLayoutGroup.setVisibility(View.VISIBLE);
//        fabLayoutPoramorso.setVisibility(View.VISIBLE);
//        fabLayoutChat.setVisibility(View.VISIBLE);
//        fabBGLayout.setVisibility(View.VISIBLE);
//        //fab.animate().rotationBy(180);
////        fabLayoutGroup.animate().translationY(-getResources().getDimension(R.dimen._55sdp));
////        fabLayoutPoramorso.animate().translationY(-getResources().getDimension(R.dimen._100sdp));
////        fabLayoutChat.animate().translationY(-getResources().getDimension(R.dimen._145sdp));
//    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!PersistentUser.isLogged(context)) {
            relNotification.setVisibility(View.GONE);
            tvLogOut.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            linAmarPata.setVisibility(View.GONE);
            linUpload.setVisibility(View.GONE);
        } else {
            relNotification.setVisibility(View.VISIBLE);
            tvLogOut.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            linAmarPata.setVisibility(View.VISIBLE);
            linUpload.setVisibility(View.VISIBLE);
        }
    }

    private void showFABMenu() {
        isFABOpen = true;
        //fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabLayoutChatGroup.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        //fabLayout1.animate().translationY(-getResources().getDimension(R.dimen._55sdp));
        fabLayoutChatGroup.animate().translationY(-getResources().getDimension(R.dimen._55sdp));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen._100sdp));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen._145sdp));
    }


//    private void closeFABmsgMenu(){
//        isFABmsgOpen=false;
//        fabBGLayout.setVisibility(View.GONE);
//        //fab.animate().rotationBy(-180);
////        fabLayoutPoramorso.animate().translationY(0);
////        fabLayoutChat.animate().translationY(0);
////        fabLayoutGroup.animate().translationY(0).setListener(new Animator.AnimatorListener() {
////            @Override
////            public void onAnimationStart(Animator animator) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animator animator) {
////                if(!isFABmsgOpen){
////                    fabLayoutChat.setVisibility(View.GONE);
////                    fabLayoutPoramorso.setVisibility(View.GONE);
////                    fabLayoutGroup.setVisibility(View.GONE);
////                }
////
////            }
////
////            @Override
////            public void onAnimationCancel(Animator animator) {
////
////            }
////
////            @Override
////            public void onAnimationRepeat(Animator animator) {
////
////            }
////        });
//    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        //fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayoutChatGroup.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    //fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                    fabLayoutChatGroup.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    private void hijriDate() {


//        Translate t = null;
//        try {
//            t = new Translate.Builder(
//                    GoogleNetHttpTransport.newTrustedTransport()
//                    , GsonFactory.getDefaultInstance(), null)
//                    // Set your application name
//                    .setApplicationName("Stackoverflow-Example")
//                    .build();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Translate.Translations.List list = null;
//        try {
//            list = t.new Translations().list(
//                    Arrays.asList(
//                            // Pass in list of strings to be translated
//                            "Hello World",
//                            "How to use Google Translate from Java"),
//                    // Target language
//                    "ES");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // TODO: Set your API-Key from https://console.developers.google.com/
//        list.setKey("your-api-key");
//        TranslationsListResponse response = null;
//        try {
//            response = list.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (TranslationsResource translationsResource : response.getTranslations())
//        {
//            System.out.println(translationsResource.getTranslatedText());
//        }


        Date date = Calendar.getInstance().getTime();

        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String d = formatter.format(date);


        ChronoFormatter<HijriCalendar> hijriFormat =
                ChronoFormatter.setUp(HijriCalendar.family(), Locale.ENGLISH)
                        .addEnglishOrdinal(HijriCalendar.DAY_OF_MONTH)
                        .addPattern(" MMMM yyyy", PatternType.CLDR)
                        .build()
                        .withCalendarVariant(HijriCalendar.VARIANT_UMALQURA);

// conversion from gregorian to hijri-umalqura valid at noon
// (not really valid in the evening when next islamic day starts)
        HijriCalendar today =
                SystemClock.inLocalView().today().transform(
                        HijriCalendar.class,
                        HijriCalendar.VARIANT_UMALQURA
                );
        System.out.println(hijriFormat.format(today)); // 22nd Rajab 1438

// taking into account the specific start of day for Hijri calendar
        HijriCalendar todayExact =
                SystemClock.inLocalView().now(
                        HijriCalendar.family(),
                        HijriCalendar.VARIANT_UMALQURA,
                        StartOfDay.EVENING // simple approximation => 18:00
                ).toDate();
        //System.out.println(hijriFormat.format(todayExact));

        String hd = hijriFormat.format(todayExact);


        dates = new String[]{"বাংলা: " + banglaDate, "ইংরেজি: " + d, "Hijri:" + hd};


    }

    private void getBangladate() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String bd = String.valueOf(BanglaDateUtils.getBanglaDate(year, month, day));


        String[] sp = bd.split("\\s+");

        String mnth = sp[0];
        String dy = sp[1];
        String[] dyy = dy.split(",");
        String fiday = dyy[0];
        String yr = sp[2];


        String currentMonth = "", currentDay = "", currentYr = "";

        if (mnth.equalsIgnoreCase("BOISHAKH")) {
            currentMonth = "বৈশাখ";
        } else if (mnth.equalsIgnoreCase("JYOISHTHO")) {
            currentMonth = "জ্যৈষ্ঠ";
        } else if (mnth.equalsIgnoreCase("ASHARH")) {
            currentMonth = "আষাঢ়";
        } else if (mnth.equalsIgnoreCase("SHRABON")) {
            currentMonth = "শ্রাবণ";
        } else if (mnth.equalsIgnoreCase("BHADRO")) {
            currentMonth = "ভাদ্র";
        } else if (mnth.equalsIgnoreCase("ASHBIN")) {
            currentMonth = "আশ্বিন";
        } else if (mnth.equalsIgnoreCase("KARTIK")) {
            currentMonth = "কার্তিক";
        } else if (mnth.equalsIgnoreCase("OGROHAYON")) {
            currentMonth = "অগ্রহায়ণ";
        } else if (mnth.equalsIgnoreCase("POUSH")) {
            currentMonth = "পৌষ";
        } else if (mnth.equalsIgnoreCase("MAGH")) {
            currentMonth = "মাঘ";
        } else if (mnth.equalsIgnoreCase("FALGUN")) {
            currentMonth = "ফাল্গুন";
        } else if (mnth.equalsIgnoreCase("চৈত্র")) {
            currentMonth = "চৈত্র";
        }


        String[] bnday = {"১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯", "১০", "১১", "১২", "১৩", "১৪", "১৫", "১৬", "১৭", "১৮", "১৯", "২০", "২১",
                "২২", "২৩", "২৪", "২৫", "২৬", "২৭", "২৮", "২৯", "৩০", "৩১"};

        String[] enday = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
                "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

        for (int i = 0; i < enday.length; i++) {
            if (fiday.equalsIgnoreCase(enday[i])) {
                currentDay = bnday[i];
                break;
            }
        }

        String[] enYr = {"1425", "1426", "1427", "1428", "1429", "1430", "1431", "1432", "1433", "1434", "1435", "1436", "1437", "1438", "1439", "1440", "1441", "1442", "1443", "1444", "1445",
                "1446", "1447", "1448", "1449", "1450", "1451", "1452", "1453", "1454", "1455"};

        String[] bnYr = {"১৪২৫", "১৪২৬", "১৪২৭", "১৪২৮", "১৪২৯", "১৪৩০", "১৪৩১", "১৪৩২", "১৪৩৩", "১৪৩৪", "১৪৩৫", "১৪৩৬", "১৪৩৭",
                "১৪৩৮", "১৪৩৯", "১৪৪০", "১৪৪১", "১৪৪২", "১৪৪৩", "১৪৪৪", "১৪৪৫",
                "১৪৪৬", "১৪৪৭", "১৪৪৮", "১৪৪৯", "১৪৫০", "১৪৫১", "১৪৫২", "১৪৫৩", "১৪৫৪", "১৪৫৫"};

        for (int i = 0; i < enYr.length; i++) {
            if (yr.equalsIgnoreCase(enYr[i])) {
                currentYr = bnYr[i];
                break;
            }
        }

        banglaDate = currentDay + " " + currentMonth + " " + "" + currentYr;

        Log.e("bangla date", "" + currentDay + " " + currentMonth + " " + "" + currentYr);


    }


    @Override
    protected void onPause() {
        super.onPause();
        //getPrayerTime();
    }

    ArrayList<Date> prayerTimesDate;
    ArrayList<String> prayerNames;

    private void getPrayerTime() {

        double latitude = 23.8103;
        double longitude = 90.4125;
//        if (!TextUtils.isEmpty(AppConstant.lat) || !TextUtils.isEmpty(AppConstant.lng)) {
//            latitude = Double.parseDouble(AppConstant.lat);
//            longitude = Double.parseDouble(AppConstant.lng);
//        }


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
        int day = cal.get(Calendar.DATE);
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


                    // updated value every1 second
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update TextView here!
                            final Date date1 = getNextPrayer();

                            final Date date2 = new Date();

                            long mills = date1.getTime() - date2.getTime();
                            int hours = (int) (mills / (1000 * 60 * 60));
                            int mins = (int) ((mills / (1000 * 60)) % 60);
                            int secs = (int) ((mills / (1000)) % 60);

                            final String diff = hours + ":" + mins + ":" + secs;
                            Log.e("diff", "" + diff);
                            tvReminder.setText(pryname + ":" + diff);
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

        if (pryname.equalsIgnoreCase("ফজর")) {
            linFojor.setBackgroundColor(Color.parseColor("#EBBF2B"));
        } else if (pryname.equalsIgnoreCase("জোহর")) {
            linJohor.setBackgroundColor(Color.parseColor("#EBBF2B"));
        } else if (pryname.equalsIgnoreCase("আসর")) {
            linAsor.setBackgroundColor(Color.parseColor("#EBBF2B"));
        } else if (pryname.equalsIgnoreCase("মাগরিব")) {
            linMagrib.setBackgroundColor(Color.parseColor("#EBBF2B"));
        } else if (pryname.equalsIgnoreCase("ইশা")) {
            linIsha.setBackgroundColor(Color.parseColor("#EBBF2B"));
        }

        return nextPreyerTime;
    }

    private void prayerNamesHighlite() {


    }


    private void initDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    boolean isRuning = true;

    private void initUi() {

        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvLogOut = (TextView) findViewById(R.id.tvLogOut);
        linAmarPata = (LinearLayout) findViewById(R.id.linAmarPata);
        linUpload = (LinearLayout) findViewById(R.id.linUpload);
        linMsgFloating = (LinearLayout) findViewById(R.id.linMsgFloating);
        relNotification = (RelativeLayout) findViewById(R.id.relNotification);
        linRegistration = (LinearLayout) findViewById(R.id.linRegistration);
        linImamtrainingReg = (LinearLayout) findViewById(R.id.linImamtrainingReg);
        linVocationalTrainReg = (LinearLayout) findViewById(R.id.linVocationalTrainReg);



        linRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RegistrationActivity.class));
            }
        });


        linImamtrainingReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ImamtrainingRegistrationActivity.class));
            }
        });


        linVocationalTrainReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, VocationalTrainingActivity.class));
            }
        });



        linUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, UploadActivity.class));
            }
        });



        linAmarPata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AmarpataActivity.class));
            }
        });



        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginActivity.class));
                PersistentUser.setLogin(context);
            }
        });
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersistentUser.logOut(context);
                finish();
            }
        });


        if (!PersistentUser.isLogged(context)) {
            relNotification.setVisibility(View.GONE);
            tvLogOut.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            linAmarPata.setVisibility(View.GONE);
            linUpload.setVisibility(View.GONE);
            linRegistration.setVisibility(View.VISIBLE);
        } else {
            relNotification.setVisibility(View.VISIBLE);
            tvLogOut.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            linRegistration.setVisibility(View.GONE);
            linAmarPata.setVisibility(View.VISIBLE);
            linUpload.setVisibility(View.VISIBLE);
        }

        fabBGLayout = findViewById(R.id.fabBGLayout);
        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                linMsgFloating.setVisibility(View.GONE);
            }
        });
        relNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linMsgFloating.getVisibility() == View.GONE) {
                    fabBGLayout.setVisibility(View.VISIBLE);
                    linMsgFloating.setVisibility(View.VISIBLE);
                } else if (linMsgFloating.getVisibility() == View.VISIBLE) {
                    linMsgFloating.setVisibility(View.GONE);
                    fabBGLayout.setVisibility(View.GONE);
                }

            }
        });

        tvDate = (TextView) findViewById(R.id.tvDate);


//        linNoticeview = (LinearLayout) findViewById(R.id.linNoticeview);
//        linNoticeview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isRuning){
//                    isRuning=false;
//                    MarqueeText.setSelected(false);
//                }else if(!isRuning) {
//                    isRuning=true;
//                    MarqueeText.setSelected(true);
//                }
//
//            }
//        });


        linKitabsomuho = (LinearLayout) findViewById(R.id.linKitabsomuho);
        linDokhotaSomuho = (LinearLayout) findViewById(R.id.linDokhotaSomuho);
        linIslamMenus = (LinearLayout) findViewById(R.id.linIslamMenus);
        linMasalaMenus = (LinearLayout) findViewById(R.id.linMasalaMenus);


        linBlog = (LinearLayout) findViewById(R.id.linBlog);
        linMasael = (LinearLayout) findViewById(R.id.linMasael);
        linAskQues = (LinearLayout) findViewById(R.id.linAskQues);
        linQuesAns = (LinearLayout) findViewById(R.id.linQuesAns);


        linBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, BlogActivity.class));

//                PopupMenu popup = new PopupMenu(context,v);
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//
//                        String value = menuItem.getTitle().toString();
//                        AppConstant.activitiname=value;
//                        startActivity(new Intent(context,AllCommonPostActivity.class));
//                        return true;
//                    }
//                });
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.menu_blog, popup.getMenu());
//                popup.show();
            }
        });

        linQuesAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = "answer";
                startActivity(new Intent(context, QuestionAnswerActivity.class));
            }
        });

        linMasael.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = "মাসআলা মাসায়েল";
                startActivity(new Intent(context,MasAlaActivity.class));
            }
        });

        linAskQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ApnarGiggasaActivity.class));

            }
        });

        linJobCirculer = (LinearLayout) findViewById(R.id.linJobCirculer);
        linJobCirculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, JobCirculerActivity.class));
            }
        });


        linQuize = (LinearLayout) findViewById(R.id.linQuize);
        linQuize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogeQuiz();
            }
        });


//        relKitab = (RelativeLayout)findViewById(R.id.relKitab);
//        relDokhota = (RelativeLayout)findViewById(R.id.relDokhota);
//        relIslam = (RelativeLayout)findViewById(R.id.relIslam);
//        relMasala = (RelativeLayout)findViewById(R.id.relMasala);
//
//        relAlKural = (RelativeLayout)findViewById(R.id.relAlKural);
//        relHadithGrontho = (RelativeLayout)findViewById(R.id.relHadithGrontho);
        //relProfile = (RelativeLayout) findViewById(R.id.relProfile);
        relSompadokio = (RelativeLayout) findViewById(R.id.relSompadokio);

//        relProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context,PhotoGallaryActivity.class));
//            }
//        });

        relSisukisur = (LinearLayout) findViewById(R.id.relSisukisur);
        relOnndhra = (LinearLayout) findViewById(R.id.relOnndhra);


        linIslam = (LinearLayout) findViewById(R.id.linIslam);
        linSompadokio = (LinearLayout) findViewById(R.id.linSompadokio);
        linNarikornar = (LinearLayout) findViewById(R.id.linNarikornar);
        linVideo = (LinearLayout) findViewById(R.id.linVideo);
        linAudio = (LinearLayout) findViewById(R.id.linAudio);
        linDokhotarGolpo = (LinearLayout) findViewById(R.id.linDokhotarGolpo);
        linKitab = (LinearLayout) findViewById(R.id.linKitab);
        linphotogalary = (LinearLayout) findViewById(R.id.linphotogalary);
        linAlQran = (LinearLayout) findViewById(R.id.linAlQran);
        linAHadith = (LinearLayout) findViewById(R.id.linAHadith);
        linSantirbani = (LinearLayout) findViewById(R.id.linSantirbani);

        linAlQran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AlQuranActivity.class));
            }
        });

        linSantirbani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstant.activitiname = getString(R.string.santirbani);
                AppConstant.bolgpostName = "শান্তির বাণী";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });


        linAHadith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AlHadithActivity.class));
            }
        });


        linphotogalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = "ফটো গ্যালারী";
               // AppConstant.bolgpostName = "ফটো গ্যালারী";
                startActivity(new Intent(context, PhotoGallaryActivity.class));
            }
        });
        linKitab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogeKitab();
            }
        });
        relOnndhra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?time_continue=1031&v=zfZZlb0Q_V4")));
            }
        });

        linNarikornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.narikornar);
                AppConstant.bolgpostName = "নারী কর্নার";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });

        linVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = "ভিডিও ";
                startActivity(new Intent(context, VideoActivity.class));
            }
        });

        linAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = "অডিও ";
                startActivity(new Intent(context, AudioActivity.class));
            }
        });

        linDokhotarGolpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = "দক্ষতার গল্প ";
                AppConstant.bolgpostName = "দক্ষতার গল্প";
                startActivity(new Intent(context, DokkotarGolpoActivity.class));
            }
        });


        linIslam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppConstant.activitiname = "আল কুরআন ";
//                startActivity(new Intent(context,AlQuranActivity.class));

                dialogeIslam();
            }
        });


        linSompadokio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.sompadokio);
                startActivity(new Intent(context, SompadokioActivity.class));
            }
        });


        relSisukisur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.sisukisur);
                AppConstant.bolgpostName = "শিশু কিশোর কর্নার";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });

//        relHadithGrontho.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMenuAlhadith(v);
//            }
//        });
//
//        relAlKural.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMenuAlquran(v);
//            }
//        });

//        relIslamicAin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMenu(v);
//            }
//        });

        imgKitabArow = (ImageView) findViewById(R.id.imgKitabArow);
        imgDokhotaArow = (ImageView) findViewById(R.id.imgDokhotaArow);
        imgIslamArow = (ImageView) findViewById(R.id.imgIslamArow);
        imgMasala = (ImageView) findViewById(R.id.imgMasala);

//        relMasala.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(linMasalaMenus.getVisibility()==View.GONE){
//                    imgMasala.setImageResource(R.drawable.ic_uparow);
//                    linMasalaMenus.setVisibility(View.VISIBLE);
//
//                }else if(linMasalaMenus.getVisibility()==View.VISIBLE){
//                    imgMasala.setImageResource(R.drawable.ic_downarrow);
//                    linMasalaMenus.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        relIslam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(linIslamMenus.getVisibility()==View.GONE){
//                    imgIslamArow.setImageResource(R.drawable.ic_uparow);
//                    linIslamMenus.setVisibility(View.VISIBLE);
//
//                }else if(linIslamMenus.getVisibility()==View.VISIBLE){
//                    imgIslamArow.setImageResource(R.drawable.ic_downarrow);
//                    linIslamMenus.setVisibility(View.GONE);
//                }
//            }
//        });

//        relDokhota.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(linDokhotaSomuho.getVisibility()==View.GONE){
//                    imgDokhotaArow.setImageResource(R.drawable.ic_uparow);
//                    linDokhotaSomuho.setVisibility(View.VISIBLE);
//
//                }else if(linDokhotaSomuho.getVisibility()==View.VISIBLE){
//                    imgDokhotaArow.setImageResource(R.drawable.ic_downarrow);
//                    linDokhotaSomuho.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        relKitab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(linKitabsomuho.getVisibility()==View.GONE){
//                    imgKitabArow.setImageResource(R.drawable.ic_uparow);
//                    linKitabsomuho.setVisibility(View.VISIBLE);
//
//                }else if(linKitabsomuho.getVisibility()==View.VISIBLE){
//                    imgKitabArow.setImageResource(R.drawable.ic_downarrow);
//                    linKitabsomuho.setVisibility(View.GONE);
//                }
//            }
//        });


        linAutoserch = (RelativeLayout) findViewById(R.id.linAutoserch);


        imgSearch = (ImageView) findViewById(R.id.imgSearch);

        autocoEditView = (AutoCompleteTextView) findViewById(R.id.autocoEditView);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        imgGoSearch = (ImageView) findViewById(R.id.imgGoSearch);
        imgGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchStr = autocoEditView.getText().toString();
                if (searchStr.length() > 0) {
                    linAutoserch.setVisibility(View.GONE);
                    startActivity(new Intent(context, ToolbarSearchActivity.class));
                }
            }
        });


        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linAutoserch.setVisibility(View.GONE);
            }
        });

        linSearch = (LinearLayout) findViewById(R.id.linSearch);
        String[] ProgLanguages = {getString(R.string.imanakida), getString(R.string.character), getString(R.string.dawah),
                getString(R.string.islam), getString(R.string.familysocity), getString(R.string.imanakida), "Objective-c", "Small-Talk", "C#", "Ruby", "ASP", "ASP .NET"};


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, ProgLanguages);

        autocoEditView.setThreshold(1);
        autocoEditView.setAdapter(arrayAdapter);


        linSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linAutoserch.setVisibility(View.VISIBLE);
                autocoEditView.requestFocus();
                //startActivity(new Intent(context, ToolbarSearchActivity.class));
            }
        });

        autocoEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {

                if (str.length() == 0) {
                    imgCancel.setVisibility(View.VISIBLE);
                } else if (str.length() > 0) {
                    imgCancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        List<String> where = new ArrayList<String>();
        where.add("android");
        where.add("java");
        where.add("dfd");
        where.add("jadfva");
        where.add("yh");
        where.add("java");

        String[] simpleArray = new String[where.size()];
        where.toArray(simpleArray);


        //kitabSomuho();
        //questionAnswer();
        //tabMoulikBisoy();
        //tabSompadokio();
        //alochitocontant();
        //importantLink();

    }


    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String value = menuItem.getTitle().toString();
                AppConstant.activitiname = value;
                AppConstant.bolgpostName = value;
                startActivity(new Intent(context, AllCommonPostActivity.class));
                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_islami_ain, popup.getMenu());
        popup.show();
    }


    public void showMenuAlquran(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String value = menuItem.getTitle().toString();
                AppConstant.activitiname = value;
                AppConstant.bolgpostName = value;
                startActivity(new Intent(context, KitabActivity.class));

                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_al_quran, popup.getMenu());
        popup.show();
    }

    public void showMenuMasayel(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String value = menuItem.getTitle().toString();
                Toast.makeText(context, "" + value, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_masayel, popup.getMenu());
        popup.show();
    }

    public void showMenuAlhadith(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String value = menuItem.getTitle().toString();
                AppConstant.activitiname = value;
                AppConstant.bolgpostName = value;
                startActivity(new Intent(context, KitabActivity.class));
                return true;
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_hadith, popup.getMenu());
        popup.show();
    }


    private void dialogeIslam() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_islam);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        ImageView imgBack = (ImageView) dialog.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RelativeLayout relIslamicAin = (RelativeLayout) dialog.findViewById(R.id.relIslamicAin);
        RelativeLayout relIslamicEconomy = (RelativeLayout) dialog.findViewById(R.id.relIslamicEconomy);
        relIslamicEconomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.islamiceconomy);
                AppConstant.bolgpostName = "ইসলামিক অর্থনীত";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });

        RelativeLayout relDawah = (RelativeLayout) dialog.findViewById(R.id.relDawah);
        relDawah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.dawah);
                AppConstant.bolgpostName = "দাওয়াত";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });


        RelativeLayout relPoribarSomaj = (RelativeLayout) dialog.findViewById(R.id.relPoribarSomaj);
        relPoribarSomaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.familysocity);
                AppConstant.bolgpostName = "পরিবার ও সমাজ";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });

        RelativeLayout relImanAkidha = (RelativeLayout) dialog.findViewById(R.id.relImanAkidha);
        relImanAkidha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.imanakida);
                AppConstant.bolgpostName = "ঈমান ও আকীদাহ";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });

        RelativeLayout relChoritro = (RelativeLayout) dialog.findViewById(R.id.relChoritro);
        relChoritro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.character);
                AppConstant.bolgpostName = "চরিত্র";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });

        RelativeLayout relOthers = (RelativeLayout) dialog.findViewById(R.id.relOthers);
        relOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.activitiname = getString(R.string.other);
                AppConstant.bolgpostName = "অন্যান্য";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });


        relIslamicAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void dialogeKitab() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_kitab);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imgBack = (ImageView) dialog.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RelativeLayout relAlKural = (RelativeLayout) dialog.findViewById(R.id.relAlKural);
        relAlKural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuAlquran(view);
            }
        });
        RelativeLayout relHadithGrontho = (RelativeLayout) dialog.findViewById(R.id.relHadithGrontho);
        relHadithGrontho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuAlhadith(view);
            }
        });


        RelativeLayout relFikh = (RelativeLayout) dialog.findViewById(R.id.relFikh);
        relFikh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiname = "ফিকাহর গ্রন্থাবলী";
                AppConstant.bolgpostName = "ফিকাহর গ্রন্থাবলী";
                startActivity(new Intent(context, KitabActivity.class));
            }
        });


        RelativeLayout relHistoryBook = (RelativeLayout) dialog.findViewById(R.id.relHistoryBook);
        relHistoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiname = "ইসলামের ইতিহাসের গ্রন্থাবলী";
                AppConstant.bolgpostName = "ইসলামের ইতিহাসের গ্রন্থাবলী";
                startActivity(new Intent(context, KitabActivity.class));
            }
        });


        RelativeLayout relBioBook = (RelativeLayout) dialog.findViewById(R.id.relBioBook);
        relBioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiname = "জীবনী";
                AppConstant.bolgpostName = "জীবনী";
                startActivity(new Intent(context, KitabActivity.class));
            }
        });

        RelativeLayout relStory = (RelativeLayout) dialog.findViewById(R.id.relStory);
        relStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiname = "ইসলামী গল্প";
                AppConstant.bolgpostName = "ইসলামী গল্প";
                startActivity(new Intent(context, KitabActivity.class));
            }
        });


        RelativeLayout relDorson = (RelativeLayout) dialog.findViewById(R.id.relDorson);

        relDorson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiname = "ইসলামী দর্শন";
                AppConstant.bolgpostName = "ইসলামী দর্শন";
                startActivity(new Intent(context, KitabActivity.class));
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void dialogeMasala() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_masala);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imgBack = (ImageView) dialog.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RelativeLayout relPobitrota = (RelativeLayout) dialog.findViewById(R.id.relPobitrota);
        RelativeLayout relSalat = (RelativeLayout) dialog.findViewById(R.id.relSalat);
        RelativeLayout relSiam = (RelativeLayout) dialog.findViewById(R.id.relSiam);
        RelativeLayout relItekaf = (RelativeLayout) dialog.findViewById(R.id.relItekaf);
        RelativeLayout relHazOmra = (RelativeLayout) dialog.findViewById(R.id.relHazOmra);
        RelativeLayout relZakat = (RelativeLayout) dialog.findViewById(R.id.relZakat);
        RelativeLayout relkubani = (RelativeLayout) dialog.findViewById(R.id.relkubani);
        RelativeLayout relParibarik = (RelativeLayout) dialog.findViewById(R.id.relParibarik);
        RelativeLayout relOthers = (RelativeLayout) dialog.findViewById(R.id.relOthers);

        relPobitrota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showMenuAlquran(view);
            }
        });

//        relSalat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMenuAlhadith(view);
//            }
//        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void dialogeQuiz() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_quiz);

        Button btnBatil = (Button) dialog.findViewById(R.id.btnBatil);
        Button btnSuru = (Button) dialog.findViewById(R.id.btnSuru);

        btnBatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, QuizeActivity.class));
                dialog.dismiss();
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void dialogeNamajTime() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_namaj);

        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvReminder = (TextView) dialog.findViewById(R.id.tvReminder);


        linFojor = (LinearLayout) dialog.findViewById(R.id.linFojor);
        linAsor = (LinearLayout) dialog.findViewById(R.id.linAsor);
        linIsha = (LinearLayout) dialog.findViewById(R.id.linIsha);
        linJohor = (LinearLayout) dialog.findViewById(R.id.linJohor);
        linMagrib = (LinearLayout) dialog.findViewById(R.id.linMagrib);

        LinearLayout linKutba = (LinearLayout) dialog.findViewById(R.id.linKutba);
        linKutba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.119/imamportal/public/pdf/%E0%A6%96%E0%A7%81%E0%A6%A4%E0%A6%AC%E0%A6%BE%E0%A6%A4%E0%A7%81%E0%A6%B2-%E0%A6%86%E0%A6%B9%E0%A6%95%E0%A6%BE%E0%A6%AE.pdf"));
                startActivity(browserIntent);
            }
        });

        tvFojorTime = (TextView) dialog.findViewById(R.id.tvFojorTime);
        tvJohorTime = (TextView) dialog.findViewById(R.id.tvJohorTime);
        tvAsorTime = (TextView) dialog.findViewById(R.id.tvAsorTime);
        tvMagribTime = (TextView) dialog.findViewById(R.id.tvMagribTime);
        tvIshaTime = (TextView) dialog.findViewById(R.id.tvIshaTime);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);

        getPrayerTime();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    //search_view.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void slideshow() {

        handler = new Handler();
        createSwipeTimer();
        isTimerRunning = true;
        Activity activity = this;
//        TextView textViewDescription = (TextView)getView ().findViewById(R.id.textViewDescription);
//        textViewDescription.setText(Html.fromHtml(getString(R.string.text_cose_kimik)));
        launchViewpager = (ViewPager) findViewById(R.id.launchViewpager);
        launchViewpager.setPageMargin(5); // TODO Convert 'px' to 'dp'
        launchViewpager.setPageMarginDrawable(R.color.cardview_shadow_start_color);
        setupViewPager(launchViewpager);




        viewpagerDate = (ViewPager) findViewById(R.id.viewpagerDate);

//        launchViewpager.setAdapter(new Adapter(context, imageRSC, activity));
        viewpagerDate.setAdapter(new AdapteDate(context, dates));
//
//        launchViewpager.setCurrentItem(0);
//        viewpagerDate.setCurrentItem(0);

        viewpagerDate.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isTimerRunning = false;
                    swipeTimer.cancel();
                } else {
                    if (!isTimerRunning) {
                        // createSwipeTimer();
                        isTimerRunning = true;
                    }
                }
            }
        });


        Update = new Runnable() {
            @Override
            public void run() {

                if (dates != null && dates.length > 0) {
                    int currentImg = viewpagerDate.getCurrentItem();
                    currentImg++;
                    if (currentImg == dates.length) {
                        currentImg = 0;
                    }
                    viewpagerDate.setCurrentItem(currentImg, true);
                }

                /*
                 * if (currentPage == AllMenuImgInfo.getAllMenuImgInfo().size())
                 * { currentPage = 0; }
                 * MainViewPager.setCurrentItem(currentPage++, true);
                 */
            }
        };
    }

    private void createSwipeTimer() {
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
                handler.post(updateSlider);
            }
        }, 6000, 6000);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentSeraContent fragmentSeraContent = new FragmentSeraContent();
        SlidingViewPagerAdapter adapter = new SlidingViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentPhoto(), "উসুলে হাদিস");
        adapter.addFragment(new FragmentQuizBizoyee(), "হাদিসে কুদসি");
        adapter.addFragment(new FragmentSeraContent(), "বিষয়ভিত্তিক হাদিস");
        viewPager.setAdapter(adapter);
    }


    private void updateworkdone() {


        if (!NetInfo.isOnline(context)) {
            AlertMessage.showMessage(context, "Alert!", "No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NoticeResponse>> userCall = api.notices();
        userCall.enqueue(new Callback<List<NoticeResponse>>() {
            @Override
            public void onResponse(Call<List<NoticeResponse>> call, Response<List<NoticeResponse>> response) {
                pd.dismiss();

                listNotice = response.body();

                String notice = "";
                if (listNotice != null) {
                    for (int i = 0; i < listNotice.size(); i++) {
                        notice += " >>> " + listNotice.get(i).getNotice();

                    }
                }
                MarqueeText = (TextView) findViewById(R.id.MarqueeText);
                MarqueeText.setText(notice);
                MarqueeText.setSelected(true);
                MarqueeText.setFocusable(true);
                MarqueeText.setFocusableInTouchMode(true);

                //Toast.makeText(context, ""+listNotice.get(0).getNotice(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<NoticeResponse>> call, Throwable t) {
                pd.dismiss();
            }
        });


    }


}
