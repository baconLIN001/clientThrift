package com.bacon.client.core.avro;

import com.alibaba.fastjson.JSON;
import com.b3434.Factory.ProcessorFactory;
import com.b3434.Processor.Processor;
import com.bacon.client.common.entity.Parameter;
import com.bacon.client.core.base.FileUploadCode;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.io.*;
import java.util.Scanner;

/**
 * Created by bacon on 2017/4/6.
 */
public class FileHandle {

    /**
     * 使用BufferReader读取文件
     * @param parameter
     * @param filePath
     */
    public static void bufferReadFile(Parameter parameter, String filePath){
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader =null;
        String parameterString = JSON.toJSONString(parameter);
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
    public static FileUploadCode scannerReadFile(Parameter parameter, String filePath){
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        String parameterString = JSON.toJSONString(parameter);
        Processor processor = ProcessorFactory.createOfflineProcessor(parameterString);

        AvroProducer avroProducer = new AvroProducer();
        try {
            fileInputStream = new FileInputStream(filePath);
            scanner = new Scanner(fileInputStream,"UTF-8");
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                GenericRecord dataMap = null;
                dataMap = processor.process(line);
//                System.out.println("the GenericRecord is : " + dataMap.toString());
                String key = "key";
                String topic = parameter.getTopic();
                try {
                    avroProducer.sendData(dataMap,topic,key);
                }catch (SerializationException e){
                    System.out.println("Serialization Error");
                    e.printStackTrace();
                    return FileUploadCode.UPLOAD_ERROR;
                }
            }
            if (scanner.ioException()!=null){
                throw scanner.ioException();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FileUploadCode.FILE_NOT_FOUND;
        } catch (IOException e) {
            e.printStackTrace();
            return FileUploadCode.FILE_IOEXCEPTION;
        }
        return FileUploadCode.UPLOAD_SUCCESS;
    }

}
