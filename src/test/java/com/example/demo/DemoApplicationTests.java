package com.example.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private RestHighLevelClient client;

	@Test
	public void save() throws Exception{
		IndexRequest indexRequest = new IndexRequest("users");
		indexRequest.type("doc");
		indexRequest.id("1");
		User user = new User();
		user.setAge(12);
		user.setGender("11");
		user.setUserName("zxl");
		String json = JSON.toJSONString(user);
		indexRequest.source(json, XContentType.JSON);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(indexResponse);
		/*Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("user", "kimchy");
		jsonMap.put("postDate", new Date());
		jsonMap.put("message", "trying out Elasticsearch");
		IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
				.source(jsonMap);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(indexResponse);*/

	}

	@Data
	class User{
		private String userName;
		private String gender;
		private Integer age;
	}

	@Test
	public void contextLoads() {
		System.out.println(client);
	}

}
