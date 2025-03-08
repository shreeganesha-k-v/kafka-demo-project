package com.example.kafkademo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

    @Value("${file.output.path}")
    private String outputFilePath;

    @KafkaListener(topics = "${kafka.topic.name}",groupId = "${kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String,String> record){
        String value = record.value();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath,true))){

            writer.write(value);
            writer.newLine();
            LOG.info("Writing this message {} to file {}",value,outputFilePath);

        }catch (IOException e){
            LOG.error("Something went wrong while writing to file {} \n Error : {}",outputFilePath,e.getMessage());
        }

        LOG.info("Written successfully to file : {}",outputFilePath);
    }
}
