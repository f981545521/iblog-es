package cn.acyou.ibloges.demo;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-18 下午 06:02]
 **/
public class ElasticsearchTest1 {
    private Logger logger = LoggerFactory.getLogger(ElasticsearchTest1.class);
    public final static String HOST = "192.168.1.13";
    public final static int PORT = 9300;//http请求的端口是9200，客户端是9300
    private TransportClient client = null;

    @SuppressWarnings("resource")
    @Before
    public void beforeProduce() throws UnknownHostException {
        //创建客户端
        Settings settings = Settings.builder().put("cluster.name", "iblog-es").build();
        client = new PreBuiltTransportClient(settings).addTransportAddresses(
                new InetSocketTransportAddress(InetAddress.getByName(HOST),PORT));
        logger.debug("Elasticsearch connect info:" + client.toString());
    }

    @After
    public void afterProduce(){
        client.close();
    }

    @Test
    public void prepareTest(){
        System.out.println("SUCCESS");
    }

    /**
     * 创建索引库
     * 需求:创建一个索引库为：msg消息队列,类型为：tweet,id为1
     * 索引库的名称必须为小写
     */
    @Test
    public void addIndex1() throws IOException {
        IndexResponse response = client.prepareIndex("msg", "tweet", "1").setSource(XContentFactory.jsonBuilder()
                .startObject().field("userName", "张三")
                .field("sendDate", new Date())
                .field("msg", "你好李四")
                .endObject()).get();

        logger.info("索引名称:" + response.getIndex() + "\n类型:" + response.getType()
                + "\n文档ID:" + response.getId() + "\n当前实例状态:" + response.status());
    }


    /**
     * 测试Elasticsearch客户端连接
     * @Title: test1
     * @author sunt
     * @date 2017年11月22日
     * @return void
     * @throws UnknownHostException
     */
    @SuppressWarnings("resource")
    @Test
    public void test1() throws UnknownHostException {

    }

}
