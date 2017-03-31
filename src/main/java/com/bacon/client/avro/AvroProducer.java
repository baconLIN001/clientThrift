package com.bacon.client.avro;

import com.alibaba.dcm.DnsCacheManipulator;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by bacon on 2017/3/31.
 */
public class AvroProducer {
    Logger logger = Logger.getLogger(AvroProducer.class);
    private KafkaProducer producer;

    public KafkaProducer getProducer() {
        return producer;
    }

    public void setProducer(KafkaProducer producer) {
        this.producer = producer;
    }

    public AvroProducer(){
        Properties properties = new Properties();
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream("server.properties"));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,properties.getProperty("bsUrl"));
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        producerProperties.put("schema.registry.url",properties.getProperty("schema_url"));
        producer = new KafkaProducer(producerProperties);
        Properties dns = new Properties();
        dns.put("CP1","192.168.10.150");
        dns.put("CP2","192.168.10.151");
        dns.put("CP3","192.168.10.152");
        DnsCacheManipulator.setDnsCache(dns);
    }

    public void sendData(GenericRecord data,String topic,String key){
        ProducerRecord<Object, Object> record = new ProducerRecord<Object, Object>(topic,key,data);
        try {
            this.producer.send(record);
        }catch (SerializationException e){
            logger.error("Serialization Error");
            System.out.println("Serialization Error");
            e.printStackTrace();
        }
    }
}
