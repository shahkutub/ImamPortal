package com.imamportal.utils;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.imamportal.R;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AllDataResponse;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.AudioModel;
import com.imamportal.model.Catagories;
import com.imamportal.model.NotificationResponse;
import com.imamportal.model.SignUpResponse;
import com.imamportal.model.VideoModel;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sadi on 2/14/2018.
 */

public class AppConstant {

    public static String clockInOu="";
    public static String lat="";
    public static String lng="";
    public static String localpic="localpic";
    //public static String officname="officname";
    public static String locationName="";

    public static String alarmInOnOff="alarmInOnOff";
    public static boolean isGallery=false;
    public static boolean isHq=false;
    public static int CAMERA_RUNTIME_PERMISSION=2,WRITEEXTERNAL_PERMISSION_RUNTIME=3,LOCATION_PERMISSION=4;
    public static String quickAttandance = "quickAttandance";
    public static String alarmClockIn = "alarmClockIn";
    public static String alarmClockout = "alarmClockout";

    public static String alarmClockOutHour = "alarmClockOutHour";
    public static String alarmClockOutMin = "alarmClockOutMin";

    public static String alarmClockInHour = "alarmClockInHour";
    public static String alarmClockInMin = "alarmClockInMin";
    public static String checkInOrOut = "checkInOrOut";

    //public static List<LocationInfo> locationInfoList = new ArrayList<>();
    public static String path ="path";
    public static String photourl ="http://css-bd.com/attendance-system/uploads/users/";
    public static String bitmap = "bitmap";
    public static String serverTime;
    public static String activitiname="";
    public static String chatActivityName;
    public static String fcm_token = "fcm_token";
    public static List<AlquranAlhadits> listAlqranAlhadith = new ArrayList<>();
    public static List<AudioModel> listAudio = new ArrayList<>();
    public static List<VideoModel> listVideo = new ArrayList<>();
    public static String bolgpostName;
    public static List<AllBlogpostModel> listAllBlogPost = new ArrayList<>();
    public static List<Catagories> listAllCatagory = new ArrayList<>();
    public static String masalaFragmentName = "";
    public static AllDataResponse allData = new AllDataResponse();
    public static String loginToken = "loginToken";
    public static String loginUserid = "loginUserid";
    public static AllBlogpostModel detaisData = new AllBlogpostModel();
    public static NotificationResponse notificationResponse = null;
    public static String otheruserId = "";

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

//    public static void alarmClockIn(Context con) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
//
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockInHour)));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockInMin)));
//        calendar.set(Calendar.SECOND, 0);
//
//        Intent intent1 = new Intent(con, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 1,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
//
////        Intent myIntent = new Intent(con , AlarmReceiver.class);
////        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
////        PendingIntent pendingIntent = PendingIntent.getService(con, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(Calendar.HOUR_OF_DAY, 8);
////        calendar.set(Calendar.MINUTE, 45);
////        calendar.set(Calendar.SECOND, 00);
////        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
//
//    }
//
//    public static void alarmClockInNext(Context con) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
//
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockInHour)));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockInMin)));
//        calendar.set(Calendar.SECOND, 0);
//
//        Intent intent1 = new Intent(con, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 1,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
//
////        Intent myIntent = new Intent(con , AlarmReceiver.class);
////        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
////        PendingIntent pendingIntent = PendingIntent.getService(con, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(Calendar.HOUR_OF_DAY, 8);
////        calendar.set(Calendar.MINUTE, 45);
////        calendar.set(Calendar.SECOND, 00);
////        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
//
//    }
//
//
//    public static void alarmClockOut(Context con) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockOutHour)));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockOutMin)));
//        calendar.set(Calendar.SECOND, 0);
//        Intent intent1 = new Intent(con, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 2,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
////        Intent myIntent = new Intent(con , AlarmReceiver.class);
////        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
////        PendingIntent pendingIntent = PendingIntent.getService(con, 2, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(Calendar.HOUR_OF_DAY, 17);
////        calendar.set(Calendar.MINUTE, 25);
////        calendar.set(Calendar.SECOND, 00);
////        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
//    }
//
//    public static void alarmClockOutNext(Context con) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockOutHour)));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockOutMin)));
//        calendar.set(Calendar.SECOND, 0);
//        Intent intent1 = new Intent(con, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 2,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
////        Intent myIntent = new Intent(con , AlarmReceiver.class);
////        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
////        PendingIntent pendingIntent = PendingIntent.getService(con, 2, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(Calendar.HOUR_OF_DAY, 17);
////        calendar.set(Calendar.MINUTE, 25);
////        calendar.set(Calendar.SECOND, 00);
////        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
//    }

    public static void saveUserdat(Context con, SignUpResponse loginData) {
        SharedPreferences mPrefs = con.getSharedPreferences("loginData",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        prefsEditor.putString("loginData", json);
        prefsEditor.commit();

    }

    public static SignUpResponse getUserdata(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("loginData",MODE_PRIVATE);
        SignUpResponse loginData = new SignUpResponse();
        Gson gson = new Gson();
        String json = mPrefs.getString("loginData", "");
        loginData = gson.fromJson(json, SignUpResponse.class);
        return loginData;
    }




    public static void saveAllData(Context con, AllDataResponse allDataResponse) {
        SharedPreferences mPrefs = con.getSharedPreferences("allDataResponse",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allDataResponse);
        prefsEditor.putString("allDataResponse", json);
        prefsEditor.commit();

    }


        public static AllDataResponse getAllData(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("allDataResponse",MODE_PRIVATE);
        AllDataResponse allDataResponse = new AllDataResponse();
        Gson gson = new Gson();
        String json = mPrefs.getString("allDataResponse", "");
        allDataResponse = gson.fromJson(json, AllDataResponse.class);
        return allDataResponse;
    }

    //Catagory save
    public static void saveCatagories(Context con, List<Catagories> locationInfos) {
        SharedPreferences mPrefs = con.getSharedPreferences("catagory",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(locationInfos);
        prefsEditor.putString("catagory", json);
        prefsEditor.commit();

    }

    //catagory get
    public static List<Catagories> getCatagories(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("catagory",MODE_PRIVATE);
        List<Catagories> locationInfos = new ArrayList<>();
        Gson gson = new Gson();
        String json = mPrefs.getString("catagory", "");
        locationInfos = (List<Catagories>) gson.fromJson(json, Catagories.class);
        return locationInfos;
    }



    //savelistAlquranAlhadit
    public static void saveAlQuranAldaithList(Context context, List<AlquranAlhadits> listAlquranAlhadit) {
        SharedPreferences mPrefs = context.getSharedPreferences("listAlquranAlhadit",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listAlquranAlhadit);
        prefsEditor.putString("listAlquranAlhadit", json);
        prefsEditor.commit();

    }

    //getlistAlquranAlhadit

    public static List<AlquranAlhadits> getlistAlquranAlhadit(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("listAlquranAlhadit",MODE_PRIVATE);
        List<AlquranAlhadits> locationInfos = new ArrayList<>();
        Gson gson = new Gson();
        String json = mPrefs.getString("listAlquranAlhadit", "");
        locationInfos = (List<AlquranAlhadits>) gson.fromJson(json, AlquranAlhadits.class);
        return locationInfos;
    }


    public static JSONArray getlistAlquranAlhaditson(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("listAlquranAlhadit",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("listAlquranAlhadit", "");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json);
        return jsonArray;
    }


//    private void getImageReto(){
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://wwwns.akamai.com/media_resources/globe_emea.png")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//            }
//
//        });
//
//    }
//
//
//    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = pixels;
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return output;
//    }

    public static void dilogDetails(Context context,String title,String content,String publisher,String publishDate,String viewcount, String likecount,String commentcount){

        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_common_post);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();

        TextView tvTitle = (TextView)dialog.findViewById(R.id.tvTitle);
        final TextView tvDescription = (TextView)dialog.findViewById(R.id.tvDescription);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());

        TextView tvPublisher = (TextView)dialog.findViewById(R.id.tvPublisher);
        TextView tvPublishDate = (TextView)dialog.findViewById(R.id.tvPublishDate);
        TextView tvCountView = (TextView)dialog.findViewById(R.id.tvCountView);
        TextView tvCommentCount = (TextView)dialog.findViewById(R.id.tvCommentCount);

        WebView webView = (WebView)dialog.findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);

        String htmlString = "<div style=\"color:#069\"><b>"+"প্রশ্ন: "+title+"</b></div\n" + "<p>"+content+"</p>";
        webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");



        tvTitle.setText(title);
        tvDescription.setText(content);
        tvPublisher.setText(publisher);
        tvPublishDate.setText(publishDate);
        tvCountView.setText(viewcount);
        tvCommentCount.setText(commentcount);

        final int[] font = {13};
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, font[0]);
        tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(font[0] == 13){
                    font[0] +=1;
                    tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, font[0]);
                }

                if(font[0] == 25){
                    font[0] =-1;
                    tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, font[0]);
                }

            }
        });

    }
}
