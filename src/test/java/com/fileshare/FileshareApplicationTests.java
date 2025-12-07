package com.fileshare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FileshareApplicationTests {

	@Test
	void contextLoads() {
	}

}

@SpringBootTest
class FileStorageServiceTests{

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMultipartFile mockFile;

    final String STORAGE_DIRECTORY = "C:\\upload";

    @BeforeEach
    void setup(){

        mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello World".getBytes()

        );

    }


    @Test
    void saveFileEndpointTest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/upload").file(mockFile)).andExpect(status().is2xxSuccessful());

    }

    @Test
    void getAllFilesEndpointTest() throws Exception {


        File targetfiles = new File(STORAGE_DIRECTORY + File.separator);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //ResultActions files = mockMvc.perform(multipart("/viewall").file(targetfiles));

    }

    @Test
    void getDownloadFileEndpointTest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/upload").file(mockFile)).andExpect(status().is2xxSuccessful());
    }

}
