package thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.service.Apple;

/**
 * @author JKong
 * @version v1.0
 * @description 消费方
 * @date 2019/10/8 16:09.
 */
public class ServiceClient {
    public static void main(String[] args) {
        System.out.println("client starting...");
        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 9000, 3000);
            TProtocol protocol = new TBinaryProtocol(transport);
            Apple.Client client = new Apple.Client(protocol);
            transport.open();

            String result = client.appleString("abc");
            System.out.println("server return：" + result);

            int a = client.appleAdd(8);
            int b = client.appleMult(29, 3);
            System.out.println("a= " + a + "  b=" + b);

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}