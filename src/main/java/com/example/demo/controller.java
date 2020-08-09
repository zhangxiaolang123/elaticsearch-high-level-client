package com.example.demo;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Created by zxl on 2020/6/17.
 */
@RestController
public class controller {

    @Autowired
    private RestHighLevelClient client;

    @GetMapping("/xx")
    public void get()throws Exception{
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String path1 = ResourceUtils.getURL("classpath:").getPath();
		File upload = new File(new File(path).getAbsolutePath(),"static/images/downLoad/");
		if(!upload.exists()) upload.mkdirs();
		System.out.println("upload url:"+upload.getAbsolutePath());
        System.out.println(path1);
        File file = new File(path);


        File pathr = new File(ResourceUtils.getURL("classpath:").getPath());
        String s = pathr.getParentFile().getParentFile().getParent() + File.separator + "uploads" + File.separator;
        s=s.substring(5,s.length());
        System.out.println(s);
    }

    /**
     * es保存数据
     * @throws Exception
     */
    @GetMapping("/save")
    public void save()throws Exception{
        IndexRequest indexRequest = new IndexRequest("users");
        //6.8.7版本的ES，如果不指定文档类型，那么汇报错：org.elasticsearch.action.ActionRequestValidationException: Validation Failed: 1: type is missing;
        indexRequest.type("doc");
        indexRequest.id("1");
        //如果出现：java.net.SocketTimeoutException: 30,000 milliseconds timeout on connection http-outgoing-0 [ACTIVE]
        //如果还是不行就重启ElasticSearch，就解决了
        indexRequest.timeout(TimeValue.timeValueSeconds(60));
        User user = new User();
        user.setAge(12);
        user.setGender("11");
        user.setUserName("zxl");
        String json = JSON.toJSONString(user);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    /**
     * es查询数据
     * @throws Exception
     */
    @GetMapping("/query")
    public void query()throws Exception{
        /*String keywords = "";
        SearchRequest searchRequest = new SearchRequest("users");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(query);
        searchSourceBuilder.from(0).size(2);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        System.out.println(JSON.toJSON(hits));*/

        GetRequest getRequest = new GetRequest(
                "users",
                "doc",
                "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSON(getResponse));
    }
}
