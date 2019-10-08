package thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.service.Apple;
import thrift.service.impl.AppleServiceImpl;

/**
 * @author JKong
 * @version v1.0
 * @description 服务启动
 * @date 2019/10/8 16:06.
 */
public class ServiceServer {
    public static void main(String[] args) throws TTransportException {
        TProcessor tprocessor = new Apple.Processor<Apple.Iface>(new AppleServiceImpl());
        TServerSocket serverTransport = new TServerSocket(9000);
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TSimpleServer(tArgs);
        System.out.println("server started at：9000");
        server.serve();
    }
}