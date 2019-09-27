package com.learn.ad.mysql.Ilistener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.learn.ad.constant.Constant;
import com.learn.ad.constant.OpType;
import com.learn.ad.dto.BinlogRowData;
import com.learn.ad.dto.MySqlRowData;
import com.learn.ad.dto.TableTemplate;
import com.learn.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource
    private ISender sender;


    private AggregationListener aggregationListener;

    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    //根据binlog处理完之后实现的一个listener
    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListListener register db and table info");
        Constant.table2Db.forEach((k,v)->aggregationListener.register(v,k,this));


    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table= eventData.getTable();
        EventType eventType=eventData.getEventType();

        //包装成最后需要投递的数据
        MySqlRowData rowData= new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType= OpType.to(eventType);
        rowData.setOpType(opType);

        //取出模板中操作对应字段列表
        List<String> fieldList= table.getOpTypeFieldSetMap().get(opType);
        if(fieldList==null){
            log.warn("{} not support for {}", opType,table.getTableName());
            return;
        }
        for(Map<String,String> afterMap: eventData.getAfter()){
            Map<String,String> _afterMap= new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName=entry.getKey();
                String colValue=entry.getValue();
                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
