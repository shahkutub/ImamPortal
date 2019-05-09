package com.imamportal.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.R;
import com.imamportal.model.Catagories;
import com.imamportal.model.UploadResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PathUtils;
import com.imamportal.utils.PersistData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentUploadPhoto extends Fragment {

    Context context;
    private EditText etHeadline,etAsk;
    private Spinner spinnerCatagory;
    private TextView tvFileName,tvVivag;
    private LinearLayout linChooseFile,linSpinner;

    List<Catagories> catagoriesList = new ArrayList<>();
    private String filePath = "";
    private AppCompatButton btnSave,btnChoseFile;
    private String category_id;
    String data;
    String token;
    Uri fileUri;
    private ImageView imgPhoto;
    private final static int IMAGE_RESULT = 200;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.upload_gallary, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initUi();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initUi() {

        etHeadline = (EditText)getView().findViewById(R.id.etHeadline);
        etAsk = (EditText)getView().findViewById(R.id.etAsk);
        etAsk.setVisibility(View.GONE);
        tvFileName = (TextView) getView().findViewById(R.id.tvFileName);
        tvVivag = (TextView) getView().findViewById(R.id.tvVivag);
        tvVivag.setVisibility(View.GONE);
        spinnerCatagory = (Spinner)getView().findViewById(R.id.spinnerCatagory);
        linChooseFile = (LinearLayout) getView().findViewById(R.id.linChooseFile);
        linSpinner = (LinearLayout) getView().findViewById(R.id.linSpinner);
        linSpinner.setVisibility(View.GONE);
        btnSave = (AppCompatButton) getView().findViewById(R.id.btnSave);
        btnChoseFile = (AppCompatButton) getView().findViewById(R.id.btnChoseFile);
        imgPhoto = (ImageView) getView().findViewById(R.id.imgPhoto);
        btnChoseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFileChooser();
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = etHeadline.getText().toString();
                String description = etAsk.getText().toString();
                if(TextUtils.isEmpty(data)){
                    AlertMessage.showMessage(context,"Alert!","");
                }else if(TextUtils.isEmpty(filePath)){
                    AlertMessage.showMessage(context,"Alert!","");
                }else {
                    post_store();
                }

            }
        });

        Catagories catagories = new Catagories();
        catagories.setName_bn("নির্বাচন করুন");
        catagoriesList.add(0,catagories);
        for (int i = 0; i <AppConstant.listAllCatagory.size() ; i++) {
            if(AppConstant.listAllCatagory.get(i).getName_bn()!=null){
                if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("কুরআন তেলাওয়াত")){
                    Catagories catagory = new Catagories();
                    catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                    catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                    catagoriesList.add(catagory);
                }

                if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("বয়ান")){
                    Catagories catagory = new Catagories();
                    catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                    catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                    catagoriesList.add(catagory);
                }

                if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("হামদ-নাত")){
                    Catagories catagory = new Catagories();
                    catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                    catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                    catagoriesList.add(catagory);
                }

                if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("দিকনির্দেশনা")){
                    Catagories catagory = new Catagories();
                    catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                    catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                    catagoriesList.add(catagory);
                }
            }


//            if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("অন্যান্য")){
//                Catagories catagory = new Catagories();
//                catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
//                catagory.setId(AppConstant.listAllCatagory.get(i).getId());
//                catagoriesList.add(catagory);
//            }

        }

        CustomAdapter adapterGender = new CustomAdapter(context, catagoriesList);
        spinnerCatagory.setAdapter(adapterGender);

        spinnerCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    category_id = catagoriesList.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void post_store() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + PersistData.getStringData(context,AppConstant.loginToken))
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);


        File file = new File(filePath);

        // Parsing any Media type file
        //RequestBody requestBody=RequestBody.create(MediaType.parse("application/pdf"), file);

         RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), PersistData.getStringData(context,AppConstant.loginUserid));
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), data);


        Call<UploadResponse> userCall = api.photogallery(fileToUpload,title,userid);
        userCall.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                pd.dismiss();

                UploadResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, "Send Successful", Toast.LENGTH_SHORT).show();
                    }
                }

//                if(responsData!=null){
//                    if(responsData.getStatus().equalsIgnoreCase("error")){
//                        if(responsData.getData().getEmail()!=null){
//                            for (int i = 0; i <responsData.getData().getEmail().size() ; i++) {
//                                Toast.makeText(context, ""+responsData.getData().getEmail().get(i), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    }
//                }

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

//    private void showFileChooser() {
//        Intent intent_upload = new Intent();
//        intent_upload.setType("video/*");
//        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent_upload,1);
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == IMAGE_RESULT) {
                filePath = getImageFilePath(data);

                Log.e("filePath", "" + filePath);
                if (filePath != null) {
                    Bitmap bmp = BitmapFactory.decodeFile(filePath);
                    bmp =  getResizedBitmap(bmp,612,384);
                    // filePath = setToImageView(bmp);
                    if(bmp != null){
                         imgPhoto.setImageBitmap(bmp);
                       // AppConstant.saveSCphotoSdCard(bmp);
                        //tvFileName.setText("");
                    }

                }
            }

        }

    }


    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = context.getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "3ss.jpg"));
        }
        return outputFileUri;
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int maxWidth, int maxHeight) {

        Bitmap bmp = null;

        if (maxHeight > 0 && maxWidth > 0) {
            int width = bm.getWidth();
            int height = bm.getHeight();

            Log.e("bm.getWidth()",""+bm.getWidth());
            Log.e("bm.getHeight()",""+bm.getHeight());

            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            bmp = Bitmap.createScaledBitmap(bm, finalWidth, finalHeight, true);
            return bmp;
        } else {
            return bmp;
        }

    }

    public class CustomAdapter  extends BaseAdapter implements SpinnerAdapter {

        List<Catagories> company;
        Context context;


        public CustomAdapter(Context context, List<Catagories> company) {
            this.company = company;
            this.context = context;
        }

        @Override
        public int getCount() {
            return company.size();
        }

        @Override
        public Object getItem(int position) {
            return company.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =  View.inflate(context, R.layout.company_main, null);
            TextView textView = (TextView) view.findViewById(R.id.main);
            textView.setText(company.get(position).getName_bn());
            return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View view;
            view =  View.inflate(context, R.layout.company_dropdown, null);
            final TextView textView = (TextView) view.findViewById(R.id.dropdown);
            textView.setText(company.get(position).getName_bn());

            return view;
        }
    }



}
