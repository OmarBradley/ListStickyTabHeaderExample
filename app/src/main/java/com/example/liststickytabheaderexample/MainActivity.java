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

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int TAB_HEADER_POSITION = 1;
    @Bind(R.id.listView) ListView listView;
    ArrayAdapter<String> adapter;
    @Bind(R.id.fixed_tabhost) TabHost tabHost;
    TabHost headerHost;

    TabHost.TabContentFactory dummyFactory = (tag)->{
        TextView tv = new TextView(this);
        return tv;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // template method 패턴과 비슷하다
        initListViewAndHost();
        setupTabs();
        setupTabElements();
        setOnScrollListenerOnHeaderHost();
        setOnScrollListenerAndVisibilityOnTabHost();
        setOnScrollListenerAndAdapterOnListView();
        setItem("tab1");
    }

    private void initListViewAndHost(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        View headerView = getLayoutInflater().inflate(R.layout.view_header, null);
        listView.addHeaderView(headerView);
        headerHost = (TabHost) getLayoutInflater().inflate(R.layout.view_tab_header, null);
    }

    private void setupTabs(){
        headerHost.setup();
        tabHost.setup();
    }

    private void setupTabElements(){
        List<TabElementDummy> tabElementDummies = Arrays.asList(new TabElementDummy("tab1", "TAB1"), new TabElementDummy("tab2", "TAB2"), new TabElementDummy("tab3", "TAB3"));
        Stream.of(tabElementDummies).forEach(i -> {
            headerHost.addTab(headerHost.newTabSpec(i.getTabName()).setIndicator(i.getTabSpecId()).setContent(dummyFactory));
            tabHost.addTab(headerHost.newTabSpec(i.getTabName()).setIndicator(i.getTabSpecId()).setContent(dummyFactory));
        });
    }

    private void setOnScrollListenerOnHeaderHost(){
        headerHost.setOnTabChangedListener(tabId -> {
            if (!tabHost.getCurrentTabTag().equals(tabId)) {
                tabHost.setCurrentTabByTag(tabId);
            }
        });
        listView.addHeaderView(headerHost, null, false);
    }

    private void setOnScrollListenerAndVisibilityOnTabHost(){
        tabHost.setVisibility(View.GONE);
        tabHost.setOnTabChangedListener(tabId -> {
            if (!headerHost.getCurrentTabTag().equals(tabId)) {
                headerHost.setCurrentTabByTag(tabId);
            }
            setItem(tabId);
        });
    }

    private void setOnScrollListenerAndAdapterOnListView(){
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
    }

    private void setItem(String tabId) {
        adapter.clear();
        for (int i = 0; i < 20; i++) {
            adapter.add(tabId + ":" + i);
        }
    }



}
