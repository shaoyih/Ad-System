package com.learn.ad.sender;

import com.learn.ad.dto.MySqlRowData;

public interface ISender {
    void sender(MySqlRowData rowData);
}
