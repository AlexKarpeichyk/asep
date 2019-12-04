package com.asep.app.testcontroller;

import com.asep.app.constants.TestConstant;
import com.asep.app.entity.Postcode;
import com.asep.app.entity.Property;
import com.asep.app.entity.RadiusRequest;
import com.asep.app.exceptions.DuplicatePostCodeException;
import com.asep.app.exceptions.NoPropertiesFoundException;
import com.asep.app.exceptions.PostcodeNotFoundException;
import com.asep.app.helper.JavaGeocode;
import com.asep.app.repository.PostcodeRepository;
import com.asep.app.service.PostcodeService;
import com.asep.app.service.PostcodeServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.bson.types.ObjectId;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostTestService {

    @InjectMocks
    PostcodeService postcodeService = new PostcodeServiceImpl();

    @Mock
    PostcodeRepository postcodeRepository;

    JavaGeocode javaGeocode = new JavaGeocode();

    private final Logger testLog = LoggerFactory.getLogger(getClass());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test Case to Add Postcode to Mock DB")
    public void addPostTestCase() {
        List<Postcode> postcodeList = new ArrayList<>();
        List<Property> propertyList = new ArrayList<>();
        Property property = new Property.PropertyBuilder()
                .setId(TestConstant.DEFAULT_PROPERTY_ID)
                .setDate("2016-07-26")
                .setDuration(TestConstant.FREEHOLD)
                .setNewProperty(true)
                .setPrice(200000)
                .setHouseType("Flat")
                .setAddress(TestConstant.DEFAULT_ADDRESS)
                .build();
        String address = property.getAddress();
        double[] value = javaGeocode.addressConversion(address);
        property.setLatitude(value[0]);
        property.setLongitude(value[1]);
        propertyList.add(property);
        Postcode postcode = new Postcode.PostcodeBuilder()
                .setPostcodeId(new ObjectId().toString())
                .setPostcode(TestConstant.DEFAULT_POSTCODE)
                .setProperties(propertyList)
                .build();
        try {
            testLog.info("Post Mock Data " +
                    new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(postcode));
        } catch (JsonProcessingException jsonProcessingException) {
            testLog.error("LOG OFF");
        }
        postcodeList.add(postcode);
        Mockito.when(postcodeRepository.save(Mockito.any(Postcode.class))).thenReturn(postcode);
        Assert.assertEquals(postcodeList.size(), postcodeService.addPostcode(postcodeList).size());
    }

    @Test
    @DisplayName("Test Case for Existing Postcode rejection.")
    public void addPostCodeNegativeTestCase() throws PostcodeNotFoundException {
        thrown.expect(DuplicatePostCodeException.class);
        thrown.expectMessage("DUPLICATE POSTCODE EXCEPTION");
        List<Postcode> postcodeList = new ArrayList<>();
        /*Generating data for First Post Code*/
        List<Property> propertyListPostCodeOne = new ArrayList<>();
        Property propertyOne = new Property.PropertyBuilder()
                .setId(TestConstant.DEFAULT_PROPERTY_ID)
                .setDate("2016-07-26")
                .setDuration(TestConstant.FREEHOLD)
                .setNewProperty(true)
                .setPrice(200000)
                .setHouseType("Flat")
                .setAddress(TestConstant.DEFAULT_ADDRESS)
                .build();
        propertyListPostCodeOne.add(propertyOne);
        Postcode postcodeOne = new Postcode.PostcodeBuilder()
                .setPostcodeId(new ObjectId().toString())
                .setPostcode(TestConstant.DEFAULT_POSTCODE)
                .setProperties(propertyListPostCodeOne)
                .build();
        postcodeList.add(postcodeOne);

        /*Generating data for Second Post Code*/
        List<Postcode> postcodeDatabaseList = new ArrayList<>();
        List<Property> propertyDatabaseList = new ArrayList<>();
        Property propertyTwo = new Property.PropertyBuilder()
                .setId(TestConstant.DEFAULT_PROPERTY_ID)
                .setDate("2016-07-26")
                .setDuration(TestConstant.FREEHOLD)
                .setNewProperty(true)
                .setPrice(200000)
                .setHouseType("Flat")
                .setAddress(TestConstant.DEFAULT_ADDRESS)
                .build();
        propertyDatabaseList.add(propertyTwo);
        Postcode postcodeTwo = new Postcode.PostcodeBuilder()
                .setPostcodeId(new ObjectId().toString())
                .setPostcode(TestConstant.DEFAULT_POSTCODE)
                .setProperties(propertyDatabaseList)
                .build();
        postcodeDatabaseList.add(postcodeTwo);

        for (Postcode postcode : postcodeDatabaseList) {
            OngoingStubbing<Boolean> value = Mockito.when(postcodeRepository.existsByPostcode(postcode.getPostcode())).thenReturn(true);
            if (value.getMock().equals(true)) {
                Assert.fail();
            }
        }
    }

    @Test
    @DisplayName("Test Case for Getting Properties within given Radius")
    public void propertiesWithinRadiusTestCase() {
        List<Property> propertyList = new ArrayList<>();
        Property property = new Property.PropertyBuilder()
                .setId(TestConstant.DEFAULT_PROPERTY_ID)
                .setDate("2016-07-26")
                .setDuration(TestConstant.FREEHOLD)
                .setNewProperty(true)
                .setPrice(200000)
                .setHouseType("Flat")
                .setAddress(TestConstant.DEFAULT_ADDRESS)
                .build();
        String address = property.getAddress();
        double[] value = javaGeocode.addressConversion(address);
        property.setLatitude(value[0]);
        property.setLongitude(value[1]);
        propertyList.add(property);
        Postcode postcode = new Postcode.PostcodeBuilder()
                .setPostcodeId(new ObjectId().toString())
                .setPostcode(TestConstant.DEFAULT_POSTCODE)
                .setProperties(propertyList)
                .build();
        RadiusRequest request = new RadiusRequest.RadiusRequestBuilder()
                .setRadius(5)
                .setLongitude(value[1])
                .setLatitude(value[0])
                .build();

        Mockito.when(postcodeRepository.findAll()).thenReturn(Stream.of(postcode).collect(Collectors.toList()));
        Assert.assertEquals(propertyList.get(0).getAddress(), postcodeService.getPropertiesWithinRadius(request).get(0).getAddress());
    }
}
