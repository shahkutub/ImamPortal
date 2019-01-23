package com.imamportal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.imamportal.Adapter.ChatCreatAdapter;
import com.imamportal.Adapter.ChatUsetListAdapter;
import com.imamportal.model.ChatUserModel;

import java.util.ArrayList;
import java.util.List;

public class ChatCreateGroupActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private SearchView searchView;

    Context context;
    ChatCreatAdapter mAdapter;
    private LinearLayout linCreateGroup,linNewChat,linGroupName;
    private FrameLayout frmFloatingOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_search);

        context=this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Group");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linCreateGroup = (LinearLayout) findViewById(R.id.linCreateGroup);
        linNewChat = (LinearLayout) findViewById(R.id.linNewChat);
        linGroupName = (LinearLayout) findViewById(R.id.linGroupName);
        frmFloatingOk = (FrameLayout) findViewById(R.id.frmFloatingOk);
        linCreateGroup.setVisibility(View.GONE);
        linNewChat.setVisibility(View.GONE);
        linGroupName.setVisibility(View.VISIBLE);
        //frmFloatingOk.setVisibility(View.VISIBLE);

        frmFloatingOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,ChatAppActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);



        ChatUserModel chatUserModel = new ChatUserModel();

        chatUserModel.setContent("Hi whats up");
        chatUserModel.setUsername("Alom ali");
        chatUserModel.setDate("21,10,2018");

        List <ChatUserModel>  chatulist = new ArrayList<>();

//        for (int i = 0; i < 15; i++) {
//            chatUserModel.setUsername("Alom ali");
//            chatulist.add(chatUserModel);
//
//        }

        ChatUserModel chatUserModel1 = new ChatUserModel();

        chatUserModel1.setContent("Hi whats up");
        chatUserModel1.setUsername("azim");
        chatUserModel1.setDate("21,10,2018");
        chatulist.add(chatUserModel1);

        ChatUserModel chatUserModel2 = new ChatUserModel();

        chatUserModel2.setContent("Hi whats up");
        chatUserModel2.setUsername("Halim");
        chatUserModel2.setDate("21,10,2018");
        chatulist.add(chatUserModel2);

        ChatUserModel chatUserModel3 = new ChatUserModel();

        chatUserModel3.setContent("Hi whats up");
        chatUserModel3.setUsername("Joni");
        chatUserModel3.setDate("21,10,2018");
        chatulist.add(chatUserModel3);

        ChatUserModel chatUserModel4 = new ChatUserModel();

        chatUserModel4.setContent("Hi whats up");
        chatUserModel4.setUsername("Moni");
        chatUserModel4.setDate("21,10,2018");
        chatulist.add(chatUserModel4);


        ChatUserModel chatUserModel5 = new ChatUserModel();

        chatUserModel5.setContent("Hi whats up");
        chatUserModel5.setUsername("Kalm");
        chatUserModel5.setDate("21,10,2018");
        chatulist.add(chatUserModel5);

        ChatUserModel chatUserModel6 = new ChatUserModel();

        chatUserModel6.setContent("Hi whats up");
        chatUserModel6.setUsername("Amin");
        chatUserModel6.setDate("21,10,2018");
        chatulist.add(chatUserModel6);


        ChatUserModel chatUserModel7 = new ChatUserModel();

        chatUserModel7.setContent("Hi whats up");
        chatUserModel7.setUsername("Sony");
        chatUserModel7.setDate("21,10,2018");
        chatulist.add(chatUserModel7);

        ChatUserModel chatUserModel8 = new ChatUserModel();

        chatUserModel8.setContent("Hi whats up");
        chatUserModel8.setUsername("Any");
        chatUserModel8.setDate("21,10,2018");
        chatulist.add(chatUserModel8);


        mAdapter = new ChatCreatAdapter(chatulist,frmFloatingOk,context);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
//        if (!searchView.isIconified()) {
//            searchView.setIconified(true);
//            return;
//        }else {
//            finish();
//        }

        finish();
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


}
