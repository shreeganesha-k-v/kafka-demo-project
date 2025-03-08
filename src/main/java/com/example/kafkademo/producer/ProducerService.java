package com.example.kafkademo.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerService.class);
    private final KafkaTemplate<String,String> kafkaTemplate;
    @Value("${kafka.topic.name}")
    private  String topicName;

    @Value("${file.input.path}")
    private String inputFilePath;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishData(){
        LOG.info(" {} file is ready to be read ",inputFilePath);

        try(BufferedReader reader=new BufferedReader(new FileReader(inputFilePath))){
            String line;
            while((line=reader.readLine())!=null){
                kafkaTemplate.send(new ProducerRecord<>(topicName,line));
            }

            LOG.info(" {} file is successfully read ",inputFilePath);

        }catch (FileNotFoundException e){
            LOG.error("{} no file found here",inputFilePath);
        }catch (IOException e){
            LOG.error("{} : something went wrong while reading file {}",e.getMessage(),inputFilePath);
        }
    }
}
