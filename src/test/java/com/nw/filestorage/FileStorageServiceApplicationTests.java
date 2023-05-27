package com.nw.filestorage;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class FileStorageServiceApplicationTests {

	@Autowired
	private DocumentRepository documentRepository;

	@InjectMocks
	private MainRestController restController;

	@Test
	void contextLoads() {
	}

	@Test
	void getAllFiles() {

	}

}
