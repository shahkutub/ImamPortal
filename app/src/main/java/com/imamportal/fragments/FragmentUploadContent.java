package com.imamportal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.R;
import com.imamportal.VocationalTrainingActivity;
import com.imamportal.model.Catagories;
import com.imamportal.model.NameInfo;
import com.imamportal.utils.AppConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FragmentUploadContent extends Fragment {

    Context context;
    private EditText etHeadline,etAsk;
    private Spinner spinnerCatagory;
    private TextView tvFileName;

    List<Catagories> catagoriesList = new ArrayList<>();
    private String filePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.upload_fragment, container, false);

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
        tvFileName = (TextView) getView().findViewById(R.id.tvFileName);
        spinnerCatagory = (Spinner)getView().findViewById(R.id.spinnerCatagory);

        Catagories catagories = new Catagories();
        catagories.setName_bn("নির্বাচন করুন");
        catagoriesList.add(0,catagories);
        for (int i = 0; i <AppConstant.listAllCatagory.size() ; i++) {
            Catagories catagory = new Catagories();
            catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
            catagory.setId(AppConstant.listAllCatagory.get(i).getId());
            catagoriesList.add(catagory);
        }

        CustomAdapter adapterGender = new CustomAdapter(context, catagoriesList);
        spinnerCatagory.setAdapter(adapterGender);
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        // String[] mimetypes = {"image/*", "video/*"};
        //intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFileURI = data.getData();
                File file = new File(selectedFileURI.getPath().toString());
                filePath = selectedFileURI.getPath().toString();
                Log.e("filePath", "" + filePath);
//                uploadedFileName = file.getName().toString();
//                Log.e("File", "" + uploadedFileName);
                tvFileName.setText(file.getName().toString());
//                StringTokenizer tokens = new StringTokenizer(uploadedFileName, ":");
//
//                String first = tokens.nextToken();
//                String file_1 = tokens.nextToken().trim();
//                tvFileName.setText(file_1);
            }
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
