package com.example.liststickytabheaderexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int TAB_HEADER_POSITION = 1;
    @Bind(R.id.listView)
    ListView listView;
    ArrayAdapter<String> adapter;
    @Bind(R.id.fixed_tabhost)
    TabHost tabHost;
    TabHost headerHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        View headerView = getLayoutInflater().inflate(R.layout.view_header, null);
        listView.addHeaderView(headerView);
        TabHost.TabContentFactory dummyFactory = new DummyContentFactory(this);

        headerHost = (TabHost) getLayoutInflater().inflate(R.layout.view_tab_header, null);
        headerHost.setup();
        headerHost.addTab(headerHost.newTabSpec("tab1").setIndicator("TAB1").setContent(dummyFactory));
        headerHost.addTab(headerHost.newTabSpec("tab2").setIndicator("TAB2").setContent(dummyFactory));
        headerHost.addTab(headerHost.newTabSpec("tab3").setIndicator("TAB3").setContent(dummyFactory));
        headerHost.setOnTabChangedListener(tabId -> {
            if (!tabHost.getCurrentTabTag().equals(tabId)) {
                tabHost.setCurrentTabByTag(tabId);
            }
        });
        listView.addHeaderView(headerHost, null, false);

        tabHost.setup();
        tabHost.addTab(headerHost.newTabSpec("tab1").setIndicator("TAB1").setContent(dummyFactory));
        tabHost.addTab(headerHost.newTabSpec("tab2").setIndicator("TAB2").setContent(dummyFactory));
        tabHost.addTab(headerHost.newTabSpec("tab3").setIndicator("TAB3").setContent(dummyFactory));
        tabHost.setVisibility(View.GONE);
        tabHost.setOnTabChangedListener(tabId -> {
            if (!headerHost.getCurrentTabTag().equals(tabId)) {
                headerHost.setCurrentTabByTag(tabId);
            }
            setItem(tabId);
        });

        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem < TAB_HEADER_POSITION) {
                    tabHost.setVisibility(View.GONE);
                } else {
                    tabHost.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
        setItem("tab1");
    }

    public void setItem(String tabId) {
        adapter.clear();
        for (int i = 0; i < 20; i++) {
            adapter.add(tabId + ":" + i);
        }
    }

    static class DummyContentFactory implements TabHost.TabContentFactory {
        Context context;

        public DummyContentFactory(Context context) {
            this.context = context;
        }

        @Override
        public View createTabContent(String tag) {
            TextView tv = new TextView(context);
            return tv;
        }
    }


}
