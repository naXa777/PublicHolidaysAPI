package com.avk.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(CopyrightController.class)
public class CopyrightControllerTest
{
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    public void testGetCopyright() throws Exception
    {
		ResultActions actions = mockMvc.perform(get("/copyright").accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
