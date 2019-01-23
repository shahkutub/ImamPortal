package com.imamportal;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityBeboherSorto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goponionitimala);
        TextView tvtext = findViewById(R.id.tvtext);
        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(getString(R.string.beboherSorto));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvtext.setMovementMethod(new ScrollingMovementMethod());
        tvtext.setText("ইমাম বাতায়ন দেখার জন্য আপনাকে ধন্যবাদ! ইসলামিক জ্ঞানের সকল শাখা-প্রশাখা নিয়ে আলোচনা করার পাশাপাশি কারিগরি ও বৃত্তিমূলক প্রশিক্ষণের তথ্য প্রদানের জন্য একটি উদ্যোগ। এটুআই প্রোগ্রামের পরিকল্পনা ও তত্বাবধানে এটি তৈরি করা হয়েছে। এই বাতায়ন ব্যবহার করার জন্য কিছু শর্তাবলি মেনে চলতে হবে ।\n" +
                "\n" +
                "1. এই বাতায়নে কোন তথ্য বা কনটেন্ট আপলোড করতে হলে আপনাকে অবশ্যই নিবন্ধন করতে হবে। \n" +
                "2. জাতীয় ঐক্য ও চেতনার পরিপন্থী কোনরকম কন্টেন্ট এই বাতায়নে শেয়ার করা যাবে না। \n" +
                "3. কোন সম্প্রদায়ের ধর্মীয় অনুভূতিতে আঘাত লাগতে পারে এমন বা ধর্মীয় অপব্যাখ্যামূলক কোন কন্টেন্ট আপলোড করা যাবে না। \n" +
                "4. রাজনৈতিক মতাদর্শ বা আলোচনা-সংশ্লিষ্ট কোন কন্টেন্ট প্রদান করা যাবে না। \n" +
                "5. বাংলাদেশে বসবাসকারী কোন ক্ষুদ্র জাতিসত্তা, নৃ-গোষ্ঠী, বা সম্প্রদায়ের প্রতি বৈষম্যমূলক বা হেয় প্রতিপন্নমূলক কন্টেন্ট শেয়ার করা যাবে না। \n" +
                "6. কোন ব্যক্তি, প্রতিষ্ঠান বা রাষ্ট্রকে হেয় প্রতিপন্ন করে এমন কন্টেন্ট দেওয়া যাবে না। \n" +
                "7. লিঙ্গ বৈষম্য বা এ সংক্রান্ত বিতর্কমূলক কোন কন্টেন্ট প্রদান করা যাবে না। জনমনে অসন্তোষ বা অপ্রীতিকর মনোভাব সৃষ্টি করতে পারে এমন কোন বিষয় আপলোড করা যাবে না।");

    }
}
