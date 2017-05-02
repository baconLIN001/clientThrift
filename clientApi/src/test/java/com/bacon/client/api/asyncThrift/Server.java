package com.bacon.client.api.asyncThrift;

import com.bacon.client.api.service.ClientAsyncServiceImpl;
import com.bacon.client.api.service.ClientServiceImpl;
import com.bacon.client.common.service.ClientService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by bacon on 2017/4/25.
 */
public class Server {
    public final static int PORT = 8989;
    @SuppressWarnings({"rawtypes","unchecked"})
    private void start(){
        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(PORT);
            final ClientService.AsyncProcessor processor = new ClientService.AsyncProcessor(new ClientAsyncServiceImpl());
            THsHaServer.Args args = new THsHaServer.Args(socket);
            // 高效率的、密集的二进制编码格式进行数据传输
            // 使用非阻塞方式，按块的大小进行传输，类似于 Java 中的 NIO
            args.protocolFactory(new TCompactProtocol.Factory());
            args.transportFactory(new TFramedTransport.Factory());
            args.processorFactory(new TProcessorFactory(processor));
            TServer server = new THsHaServer(args);
            server.serve();
            System.out.println("#服务启动-使用:非阻塞&高效二进制编码");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        Server server = new Server();
        server.start();
    }
}
