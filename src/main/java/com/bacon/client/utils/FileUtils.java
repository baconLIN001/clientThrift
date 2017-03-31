package com.bacon.client.utils;

import com.b3434.Factory.ProcessorFactory;
import com.b3434.Processor.Processor;
import com.bacon.client.avro.AvroProducer;
import com.bacon.client.pojo.Parameter;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.lang.SerializationException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by bacon on 2017/3/29.
 */
public class FileUtils {

    Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * 调用数据处理器，将文件转化成avro
     * @param parameter
     * @param fileLine
     */
//    public static void fileProcess(Parameter parameter, String fileLine)
//    {
//        String parameterString = JsonHandleUtils.parameterBeanToJson(parameter);
//        Processor processor = ProcessorFactory.createOfflineProcessor(parameterString);
//        GenericRecord dataMap = null;
//        dataMap = processor.process(fileLine);
//        System.out.println(dataMap);
//        String key = "key";
//        AvroUtils.avroProducer(dataMap,parameter.getTopic(),key);
//    }

    /**
     * 使用BufferReader读取文件
     * @param parameter
     * @param filePath
     */
    public static void bufferReadFile(Parameter parameter, String filePath){
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader =null;
        String parameterString = JsonHandleUtils.parameterBeanToJson(parameter);
        Processor processor = ProcessorFactory.createOfflineProcessor(parameterString);

        AvroProducer avroProducer = new AvroProducer();
        try {
            fileInputStream = new FileInputStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            for (String line = bufferedReader.readLine();line!=null;line=bufferedReader.readLine()){
                System.out.println(line);
                //change to avro data
                GenericRecord dataMap = null;
                dataMap = processor.process(line);
                System.out.println("the GenericRecord is : ");
                String key = "key";
                String topic = parameter.getTopic();
                try {
                    avroProducer.sendData(dataMap,topic,key);
                }catch (SerializationException e){
                    System.out.println("Serialization Error");
                    e.printStackTrace();
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Scanner读取文件
     * @param parameter
     * @param filePath
     * @return String
     */
    public static String scannerReadFile(Parameter parameter, String filePath){
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        String parameterString = JsonHandleUtils.parameterBeanToJson(parameter);
        Processor processor = ProcessorFactory.createOfflineProcessor(parameterString);

        AvroProducer avroProducer = new AvroProducer();
        try {
            fileInputStream = new FileInputStream(filePath);
            scanner = new Scanner(fileInputStream,"UTF-8");
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                //do action
//                System.out.println(line);
                //change to avro data
                GenericRecord dataMap = null;
                dataMap = processor.process(line);
                System.out.println("the GenericRecord is : " + dataMap.toString());
                String key = "key";
                String topic = parameter.getTopic();
                try {
                    avroProducer.sendData(dataMap,topic,key);
                }catch (SerializationException e){
                    System.out.println("Serialization Error");
                    e.printStackTrace();
                }
            }
            if (scanner.ioException()!=null){
                throw scanner.ioException();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "File Not Found";
        } catch (IOException e) {
            e.printStackTrace();
            return "File IO Error";
        }
        return "Success";
    }
}
