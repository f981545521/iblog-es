package cn.acyou.ibloges.demo;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
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
import java.util.HashMap;
import java.util.Map;

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
     *  3、Elasticsearch索引库的创建
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
     * 4、向索引库中添加json字符串
     */
    @Test
    public void addIndex2() {
        String jsonStr = "{" +
                "\"userName\":\"张三\"," +
                "\"sendDate\":\"2017-11-30\"," +
                "\"msg\":\"你好李四\"" +
                "}";
        IndexResponse response = client.prepareIndex("weixin", "tweet").setSource(jsonStr, XContentType.JSON).get();
        logger.info("json索引名称:" + response.getIndex() + "\njson类型:" + response.getType()
                + "\njson文档ID:" + response.getId() + "\n当前实例json状态:" + response.status());

    }


    /**
     * 5、向索引库添加一个Map集合
     */
    @Test
    public void addIndex3() {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("userName", "张三");
        map.put("sendDate", new Date());
        map.put("msg", "你好李四");

        IndexResponse response = client.prepareIndex("momo", "tweet").setSource(map).get();

        logger.info("map索引名称:" + response.getIndex() + "\n map类型:" + response.getType()
                + "\n map文档ID:" + response.getId() + "\n当前实例map状态:" + response.status());
    }
    /**
     * 6、向索引库添加JsonObject
     * 传递json对象
     */
    @Test
    public void addIndex4() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "张三");
        jsonObject.put("sendDate", "2017-11-23");
        jsonObject.put("msg","你好李四");

        IndexResponse response = client.prepareIndex("qq", "tweet").setSource(jsonObject, XContentType.JSON).get();

        logger.info("jsonObject索引名称:" + response.getIndex() + "\n jsonObject类型:" + response.getType()
                + "\n jsonObject文档ID:" + response.getId() + "\n当前实例jsonObject状态:" + response.status());
    }


    /**
     * 7、从索引库获取数据
     */
    @Test
    public void getData1() {
        GetResponse getResponse = client.prepareGet("msg", "tweet", "1").get();
        logger.info("索引库的数据:" + getResponse.getSourceAsString());
    }

    /**
     * 8、更新索引库数据
     */
    @Test
    public void updateData() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userName", "王五");
        jsonObject.put("sendDate", "2008-08-08");
        jsonObject.put("msg","你好,张三，好久不见");

        UpdateResponse updateResponse = client.prepareUpdate("msg", "tweet", "1")
                .setDoc(jsonObject.toString(),XContentType.JSON).get();

        logger.info("updateResponse索引名称:" + updateResponse.getIndex() + "\n updateResponse类型:" + updateResponse.getType()
                + "\n updateResponse文档ID:" + updateResponse.getId() + "\n当前实例updateResponse状态:" + updateResponse.status());
    }

    /**
     * 9、删除索引库的数据
     * 根据索引名称，类别，文档ID 删除索引库的数据
     */
    @Test
    public void deleteData() {
        DeleteResponse deleteResponse = client.prepareDelete("msg", "tweet", "1").get();

        logger.info("deleteResponse索引名称:" + deleteResponse.getIndex() + "\n deleteResponse类型:" + deleteResponse.getType()
                + "\n deleteResponse文档ID:" + deleteResponse.getId() + "\n当前实例deleteResponse状态:" + deleteResponse.status());
    }

}
