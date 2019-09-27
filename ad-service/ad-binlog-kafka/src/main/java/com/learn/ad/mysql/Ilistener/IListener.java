package com.learn.ad.mysql.Ilistener;

import com.learn.ad.dto.BinlogRowData;

public interface IListener {
    void register();
    void onEvent(BinlogRowData eventData);
}
