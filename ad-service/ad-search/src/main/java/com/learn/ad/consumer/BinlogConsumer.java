package com.learn.ad.consumer;


import com.alibaba.fastjson.JSON;
import com.learn.ad.dto.MySqlRowData;
import com.learn.ad.search.ISearch;
import com.learn.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BinlogConsumer {

    ISender sender;

    public BinlogConsumer(ISender sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = {"ad-search-mysql-data"},groupId = "ad-search")
    public  void processMysqlRowData(ConsumerRecord<?,?> record){
        Optional<?> kafkaMessage= Optional.ofNullable(record.value());
        if(kafkaMessage.isPresent()){
            Object message= kafkaMessage.get();
            MySqlRowData rowData= JSON.parseObject( message.toString(),MySqlRowData.class);
            log.info("kafka process MysqlRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }
    }
}
