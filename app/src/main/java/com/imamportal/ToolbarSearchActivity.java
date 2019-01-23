package com.imamportal;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import static com.imamportal.R.drawable.*;

public class ToolbarSearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Toolbar tbMainSearch;
    private ListView lvToolbarSerch;
    private String TAG = ToolbarSearchActivity.class.getSimpleName();
    String[] arrays = new String[]{"সিয়াম", "ইতেকাফ", "যাকাত ও সাদকা", "হজ ও ওমরা", "কুরবানি"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_search);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        setUpViews();
    }

    private void setUpViews() {
        tbMainSearch = (Toolbar)findViewById(R.id.tb_toolbarsearch);
        tbMainSearch.setTitle("");
        lvToolbarSerch =(ListView) findViewById(R.id.lv_toolbarsearch);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrays);
        lvToolbarSerch.setAdapter(adapter);
        setSupportActionBar(tbMainSearch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tbMainSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();

        searchView.setBackgroundResource(R.drawable.shadow_search);
        searchView.setIconified(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(this  );
        Log.d(TAG, "onCreateOptionsMenu: mSearchmenuItem->" + mSearchmenuItem.getActionView());
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: query->"+query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: newText->" + newText);
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
