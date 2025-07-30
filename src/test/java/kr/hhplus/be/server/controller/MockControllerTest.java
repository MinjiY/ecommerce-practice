package kr.hhplus.be.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MockControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getPayMoney() throws Exception{

        mockMvc.perform(post("/coupons")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("{\"userId\": 1, \"couponType\": \"DISCOUNT\", \"amount\": 1000}"))
                .andExpect(status().isOk());

    }

}