package com.windstatsapp.backend.service;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;


@SpringBootTest
public class SpotServiceTest {

    @Autowired
    SpotService spotService;

    @Test
public void parameterFromJsonTest() throws Throwable {
        String json = "{\"currently\":{ \"name\":\"John\", \"age\":30, \"car\":null }}";
        JSONObject result = new JSONObject(json);
        Double expectedAge = 30.0;
        Double resultAge = spotService.parameterFromJson("age", json);
        assertEquals(" ", expectedAge, resultAge);
    }

@Test
public void testFilterText() {

}



}
