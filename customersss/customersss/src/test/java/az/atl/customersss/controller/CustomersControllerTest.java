package az.atl.customersss.controller;

import az.atl.customersss.dto.CustomersDto;
import az.atl.customersss.model.Customerss;
import az.atl.customersss.repository.CustomersRepository;
import az.atl.customersss.request.CustomersRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration")
@EnableConfigurationProperties
@EnableJpaRepositories
class CustomersControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomersRepository customersRepository;

    @Test
    @Sql(scripts = {"classpath:insert-orders.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenGetByIdWhenCustomerFoundThenReturnCustomersDto() throws Exception {
        ResponseEntity<CustomersDto> response = restTemplate.getForEntity("http://localhost:" + port + "/api/1", CustomersDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Assertions.assertEquals(1, response.getBody().getId());
        Assertions.assertEquals("Ismail", response.getBody().getName());
        Assertions.assertEquals("Azizov", response.getBody().getSurname());
        Assertions.assertEquals(25, response.getBody().getAge());
        Assertions.assertEquals("Baku", response.getBody().getAddress());
        Assertions.assertEquals(100, response.getBody().getBalance());
        Assertions.assertEquals("7LKKKKK", response.getBody().getPinCode());
    }

    @Test
    @Sql(scripts = {"classpath:insert-orders.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenGetAllWhenCustomerFoundThenReturnCustomersDto() throws Exception {
        ResponseEntity<CustomersDto[]> response = restTemplate.getForEntity("http://localhost:" + port + "/api", CustomersDto[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        List<CustomersDto> customersDtos = Arrays.asList(response.getBody());
        Assertions.assertEquals(1, customersDtos.get(0).getId());
        Assertions.assertEquals("Ismail", customersDtos.get(0).getName());
        Assertions.assertEquals("Azizov", customersDtos.get(0).getSurname());
        Assertions.assertEquals(25, customersDtos.get(0).getAge());
        Assertions.assertEquals("Baku", customersDtos.get(0).getAddress());
        Assertions.assertEquals(100, customersDtos.get(0).getBalance());
        Assertions.assertEquals("7LKKKKK", customersDtos.get(0).getPinCode());

        Assertions.assertEquals(2, customersDtos.get(1).getId());
        Assertions.assertEquals("Ismail2", customersDtos.get(1).getName());
        Assertions.assertEquals("Azizov2", customersDtos.get(1).getSurname());
        Assertions.assertEquals(26, customersDtos.get(1).getAge());
        Assertions.assertEquals("Baku", customersDtos.get(1).getAddress());
        Assertions.assertEquals(200, customersDtos.get(1).getBalance());
        Assertions.assertEquals("5LKKKKK", customersDtos.get(1).getPinCode());
    }

    @Test
    @Sql(scripts = {"classpath:insert-orders.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenGetSaveWhenCustomerRequestFoundThenReturnOrderDto() throws Exception {
        CustomersRequest customersRequest = new CustomersRequest();
        customersRequest.setName("Ismail3");
        customersRequest.setSurname("Azizov3");
        customersRequest.setAge(27);
        customersRequest.setAddress("Baku");
        customersRequest.setBalance(300);
        customersRequest.setPinCode("7CKKKKK");
        ResponseEntity<CustomersDto> response = restTemplate.postForEntity("http://localhost:" + port, customersRequest, CustomersDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Customerss customerss = customersRepository.getById(1L);
        Assertions.assertNotNull(customerss);

        Assertions.assertEquals(customerss.getName(), customersRequest.getName());
        Assertions.assertEquals(customerss.getSurname(), customersRequest.getSurname());
        Assertions.assertEquals(customerss.getAge(), customersRequest.getAge());
        Assertions.assertEquals(customerss.getAddress(), customersRequest.getAddress());
        Assertions.assertEquals(customerss.getBalance(), customersRequest.getBalance());
        Assertions.assertEquals(customerss.getPinCode(), customersRequest.getPinCode());

        CustomersDto customersDto = response.getBody();
        Assertions.assertEquals(customersDto.getName(), customersRequest.getName());
        Assertions.assertEquals(customersDto.getSurname(), customersRequest.getSurname());
        Assertions.assertEquals(customersDto.getAge(), customersRequest.getAge());
        Assertions.assertEquals(customersDto.getAddress(), customersRequest.getAddress());
        Assertions.assertEquals(customersDto.getBalance(), customersRequest.getBalance());
        Assertions.assertEquals(customersDto.getPinCode(), customersRequest.getPinCode());

    }

//        @Test
//        @Sql(scripts = {"classpath:insert-orders.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//        public void givenGetBalanceLogicWhenCustomerRequestFoundThenReturnOrderDto () throws Exception {
//            Long price = 10L;
//            Long id = 1L;
//        restTemplate.patchForObject("http://localhost:" + port + "/" + id + "/" + price, );
//        }
}

