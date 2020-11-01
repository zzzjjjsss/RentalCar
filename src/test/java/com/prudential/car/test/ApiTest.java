package com.prudential.car.test;

import com.prudential.car.MyApplication;
import com.prudential.car.controller.bean.*;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    private String base;

    @Before
    public void setUp() throws Exception {
       this.base=String.format("http://localhost:%d", port);
    }
    @Test
    public void testListCars() {
        ResponseEntity<ListCarResponse> wrongPageIndex=restTemplate.getForEntity(base+"/cars/page/-1", ListCarResponse.class);
        Assert.assertTrue(wrongPageIndex.getBody().getCars()==null);
        ResponseEntity<ListCarResponse> contentResponse=restTemplate.getForEntity(base+"/cars/page/0", ListCarResponse.class);
        Assert.assertTrue(contentResponse.getBody().getCars().size()>0);
        ResponseEntity<ListCarResponse> emptyResponse=restTemplate.getForEntity(base+"/cars/page/100", ListCarResponse.class);
        Assert.assertTrue(emptyResponse.getBody().getCars().size()==0);
    }

    @Test
    public void testRentalCar() {



        RentalCarRequest rentalCarRequest =new RentalCarRequest();
        rentalCarRequest.setCarId(1);
        rentalCarRequest.setRentalCode("abc");
        rentalCarRequest.setStartRentalDate(LocalDate.parse("2020-01-01").toDate());
        rentalCarRequest.setEndRenttalDate(LocalDate.parse("2020-02-01").toDate());
        ResponseEntity<RentalCarResponse> rentalResponse=restTemplate.postForEntity(base+"/rentalCar",rentalCarRequest, RentalCarResponse.class);
        //rental Code is wrong
        Assert.assertTrue(rentalResponse.getBody().getResultCode()==ApiResponse.ResultCode.FAIL);
        System.out.println(rentalResponse.getBody().getMes());

        //rental car success
        ResponseEntity<RentalCodeResponse> rentalCodeResponse=restTemplate.getForEntity(base+"/rentalCode", RentalCodeResponse.class);
        rentalCarRequest.setRentalCode(rentalCodeResponse.getBody().getRentalCode());
        ResponseEntity<RentalCarResponse> rentalSucResponse=restTemplate.postForEntity(base+"/rentalCar",rentalCarRequest, RentalCarResponse.class);
        Assert.assertTrue(rentalSucResponse.getBody().getRentalCarRecordId()!=null);
        System.out.println(rentalSucResponse.getBody().getMes());

        //the car doesn't exist
        rentalCarRequest.setCarId(100);
        ResponseEntity<RentalCarResponse> carNotExist=restTemplate.postForEntity(base+"/rentalCar",rentalCarRequest, RentalCarResponse.class);
        Assert.assertTrue(carNotExist.getBody().getResultCode()== ApiResponse.ResultCode.FAIL);


        //The car has rented over
        rentalCarRequest.setCarId(1);
        for(int i=1;i<100;i++){
            ResponseEntity<RentalCarResponse> rental=restTemplate.postForEntity(base+"/rentalCar",rentalCarRequest, RentalCarResponse.class);
        }
        ResponseEntity<RentalCarResponse> noCar=restTemplate.postForEntity(base+"/rentalCar",rentalCarRequest, RentalCarResponse.class);
        Assert.assertTrue(carNotExist.getBody().getResultCode()== ApiResponse.ResultCode.FAIL);



    }
}
