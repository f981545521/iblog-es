package cn.acyou.ibloges.demo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-20 下午 05:17]
 **/
public class ESTest {

    @Test
    public void test1() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client =  new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.13"),9300));
    }
}
