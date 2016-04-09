package com.example.liststickytabheaderexample;

/**
 * Created by 재화 on 2016-04-09.
 */
public class TabElementDummy {

    public String tabSpecId;
    public String tabName;

    public TabElementDummy(String tabName, String tabSpecId) {
        this.tabName = tabName;
        this.tabSpecId = tabSpecId;
    }

    public String getTabName() {
        return tabName;
    }

    public String getTabSpecId() {
        return tabSpecId;
    }
}
