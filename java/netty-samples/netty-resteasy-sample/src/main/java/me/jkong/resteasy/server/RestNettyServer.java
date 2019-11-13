package me.jkong.resteasy.server;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.Collection;

public class RestNettyServer {

    private String serverPort = "2365";

    private String hostName;

    private String rootResourcePath = "/rs";

    private int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 4;

    private SSLContext sslContext = null;

    private NettyJaxrsServer nettyServer;

    private Collection<Object> providers = new ArrayList<>();
    private Collection<Object> controllers = new ArrayList<>();

    /**
     * 启动服务
     */
    public void start() {
        nettyServer = new NettyJaxrsServer();
        nettyServer.setDeployment(initDeployment());
        nettyServer.setPort(Integer.parseInt(serverPort));
        nettyServer.setRootResourcePath(rootResourcePath);
        nettyServer.setIoWorkerCount(ioWorkerCount);
        nettyServer.setExecutorThreadCount(Runtime.getRuntime().availableProcessors());
        nettyServer.setMaxRequestSize(Runtime.getRuntime().availableProcessors() * 2);
        nettyServer.setSSLContext(sslContext);
        nettyServer.setKeepAlive(true);
        if (StringUtils.isNotBlank(hostName)) {
            nettyServer.setHostname(hostName.trim());
        }
        nettyServer.setSecurityDomain(null);
        nettyServer.start();
    }

    /**
     * 初始化 Deployment
     *
     * @return ResteasyDeployment
     */
    private ResteasyDeployment initDeployment() {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setAsyncJobServiceEnabled(true);
        deployment.setAsyncJobServiceMaxJobResults(100);
        deployment.setAsyncJobServiceMaxWait(300000);
        deployment.setAsyncJobServiceThreadPoolSize(100);


        // extract controller
        if (controllers.isEmpty()) {
            throw new IllegalArgumentException("controllers is empty!");
        }
        deployment.getResources().addAll(controllers);

        // extract providers
        if (providers != null) {
            deployment.getProviders().addAll(providers);
        }
        return deployment;
    }

    public void addProvider(Collection<Object> providers) {
        if (providers != null && !providers.isEmpty()) {
            providers.addAll(providers);
        }
    }

    public void addResources(Collection<Object> instances) {
        if (instances != null && !instances.isEmpty()) {
            controllers.addAll(instances);
        }
    }

}
