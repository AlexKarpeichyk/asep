package com.asep.app.bean;

import com.asep.app.constants.TestConstant;
import com.asep.app.entity.Device;
import com.asep.app.entity.Location;
import com.asep.app.entity.User;
import com.asep.app.repository.UserRepository;
import com.asep.app.service.UserService;
import com.asep.app.service.UserServiceImpl;
import com.github.javafaker.Faker;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockUserDaoSingleton {

    @InjectMocks
    UserService userService = new UserServiceImpl();

    @Mock
    UserRepository userRepository;

    private static MockUserDaoSingleton mockUserDaoInstance;

    private MockUserDaoSingleton() {
    }

    synchronized public static MockUserDaoSingleton getInstance() {
        if (mockUserDaoInstance == null) {
            mockUserDaoInstance = new MockUserDaoSingleton();
        }
        return mockUserDaoInstance;
    }

    public User checkUserName(User testUser) {
        boolean response = userService.createUser(testUser);
        if (response == true) {
            Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
        }
        return testUser;
    }

    public void getUser() {
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder().setTimestamp(System.currentTimeMillis()).setLatitude(TestConstant.DEFAULT_LATITUDE).setLongitude(TestConstant.DEFAULT_LONGITUDE).build();
        locationList.add(location);
        Faker faker = new Faker();
        ObjectId objectId = new ObjectId();
        User user_one = new User.UserBuilder().setId(objectId.toString()).setName(faker.name().fullName()).setCountry("United Kingdom").setCity("London").setDevice(device).setLocation(locationList).setPassword("password1223").build();
        User user_two = new User.UserBuilder().setId(objectId.toString()).setName(faker.name().fullName()).setCountry("United Kingdom").setCity("London").setDevice(device).setLocation(locationList).setPassword("password1223diwnc").build();
        Mockito.when(userRepository.findAll()).thenReturn(Stream.of(user_one, user_two).collect(Collectors.toList()));
        Assert.assertEquals(2, userService.getUser().size());
    }

}
