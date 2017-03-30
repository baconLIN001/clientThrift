package com.bacon.client.thrift;

import com.bacon.client.sevice.ClientService;
import com.bacon.client.sevice.impl.ClientServiceImpl;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by bacon on 2017/3/17.
 */
public class RunningServer implements Runnable {

    Logger logger = Logger.getLogger(RunningServer.class);

    private int port;

    public RunningServer(int port){
        this.port = port;
    }

    @Override
    public void run() {
        logger.info("\nRunning Server in : " + port );

        ClientService.Processor<ClientService.Iface> processor
                = new ClientService.Processor<ClientService.Iface>(new ClientServiceImpl());
        TServerSocket serverSocket = null;
        try {
            serverSocket = new TServerSocket(port);
            TServer.Args targs = new TServer.Args(serverSocket);
            targs.processor(processor);
            targs.protocolFactory(new TBinaryProtocol.Factory());
            TServer tServer = new TSimpleServer(targs);
            tServer.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
            logger.info("Server Start Fail!");
        }
    }
}
