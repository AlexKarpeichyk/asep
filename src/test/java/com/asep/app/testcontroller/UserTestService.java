package com.asep.app.testcontroller;

import com.asep.app.AsepApplicationTests;
import com.asep.app.annotations.TestInformation;
import com.asep.app.bean.MockUserDaoSingleton;
import com.asep.app.constants.TestConstant;
import com.asep.app.entity.AuthUser;
import com.asep.app.entity.Device;
import com.asep.app.entity.Location;
import com.asep.app.entity.User;
import com.asep.app.exceptions.EmailNotFoundException;
import com.asep.app.exceptions.UserNotFoundException;
import com.asep.app.helper.UserDataBinder;
import com.asep.app.repository.UserRepository;
import com.asep.app.service.UserService;
import com.asep.app.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;
import org.bson.types.ObjectId;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestInformation(priority = TestInformation.Priority.HIGH,
        description = "Test For User Service Layer",
        members = {"Dominik Wasilewski",
                "Harrys Pitsillides",
                "Ben Windle",
                "Tashi Namgyal",
                "Alex Karp",
                "Arindam Dutta"})
public class UserTestService extends AsepApplicationTests {

    private static MockUserDaoSingleton mockUserDaoSingleton;

    private static Set<User> users;

    @InjectMocks
    UserService userService = new UserServiceImpl();

    @Mock
    UserRepository userRepository;

    @BeforeAll
    @DisplayName("Init Operation")
    public static void init() {
        System.out.println("Initializing Test Case :");
    }

    private Faker faker = new Faker();

    private ObjectId objectId = new ObjectId();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    @DisplayName("User Setup Operation")
    public static void setup() {
        //  mockUserDaoSingleton = Mockito.mock(MockUserDaoSingleton.class);
        //  mockUserDaoSingleton = MockUserDaoSingleton.getInstance();
    }

    @Test
    @DisplayName("Test For Valid User Name.")
    public void testValidUserName() throws Exception {
        users = UserDataBinder.userDataBinderForBestCase();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        for (User testUser : users) {
            String userInformation = objectMapper.writeValueAsString(testUser);
            System.out.println("User Created : " + userInformation);
            Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(testUser);
            Assert.assertEquals(true, userService.createUser(testUser));
        }
    }

    @Test
    @DisplayName("Test For Invalid User Name.")
    public void testInvalidUserName() throws Exception {
        users = UserDataBinder.userDataBinderWorstCase();
        String checkParam = TestConstant.TEST_NAME;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        for (User testUser : users) {
            String userInformation = objectMapper.writeValueAsString(testUser);
            System.out.println("User Created : " + userInformation);
            Mockito.when(mockUserDaoSingleton.checkUserName(testUser)).thenReturn(testUser);
            User user = mockUserDaoSingleton.checkUserName(testUser);
            if (!user.getName().equalsIgnoreCase(checkParam)) {
                //  UserDataBinder.write(userInformation, TestConstant.INVALID_USER);
                Assert.fail("The User Name is Invalid");
            }
        }
    }

    @Test
    @DisplayName("Test for Get User Test")
    public void getUserTest() {
        System.out.println("Initializing  Unit Test Case for Getting all Users.");
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setLatitude(TestConstant.DEFAULT_LATITUDE)
                .setLongitude(TestConstant.DEFAULT_LONGITUDE)
                .build();
        locationList.add(location);
        User user_one = new User.UserBuilder()
                .setId(objectId.toString())
                .setName(faker.name().fullName())
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList)
                .setPassword("password1223")
                .build();
        User user_two = new User.UserBuilder()
                .setId(objectId.toString())
                .setName(faker.name().fullName())
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList)
                .setPassword("password1223diwnc")
                .build();
        Mockito.when(userRepository.findAll()).thenReturn(Stream.of(user_one, user_two).collect(Collectors.toList()));
        Assert.assertEquals(2, userService.getUser().size());
    }

    @Test
    @DisplayName("Test for Get User By Id")
    public void getUserByIdTest() {
        System.out.println("Initializing  Unit Test Case for Getting Users By User Id.");
        String USER_ID = objectId.toString();
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setLatitude(TestConstant.DEFAULT_LATITUDE)
                .setLongitude(TestConstant.DEFAULT_LONGITUDE)
                .build();
        locationList.add(location);
        User user_one = new User.UserBuilder()
                .setId(USER_ID)
                .setName(faker.name().fullName())
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList).setPassword("password1223").build();
        try {
            Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user_one));
            Assert.assertEquals(USER_ID, user_one.getId());
            userService.findById(USER_ID).ifPresent(value -> Assert.assertEquals(user_one, value));
            userService.findById(USER_ID).ifPresent(value -> Assert.assertEquals(user_one.getName(), value.getName()));
            userService.findById(USER_ID).ifPresent(value -> Assert.assertEquals(user_one.getLocation(), value.getLocation()));
            userService.findById(USER_ID).ifPresent(value -> Assert.assertEquals(user_one.getLocation().size(), value.getLocation().size()));
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    @DisplayName("Test for GetUserById Not Found")
    public void getUserById_NegativeTest_UserNotFoundException() {
        System.out.println("Initializing  Unit Test Case for Getting Users By User Id Not Found.");
        thrown.expect(UserNotFoundException.class);
        thrown.expectMessage(CoreMatchers.is(" |USER NOT FOUND|"));
        String userId = new ObjectId().toString();
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setLatitude(TestConstant.DEFAULT_LATITUDE)
                .setLongitude(TestConstant.DEFAULT_LONGITUDE)
                .build();
        locationList.add(location);
        User user_one = new User.UserBuilder()
                .setId(userId)
                .setName(faker.name().fullName())
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList)
                .setPassword("password1223")
                .build();
        try {
            Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user_one));
            userService.findById(TestConstant.TEST_OBJECT_ID).ifPresent(value ->
                    Assert.assertEquals(TestConstant.TEST_OBJECT_ID, value.getId()));
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    @DisplayName("Test for Get User By Email")
    public void getUserByEmailTest() throws EmailNotFoundException {
        System.out.println("Initializing  Unit Test Case for Getting Users By User Email.");
        String USER_ID = new ObjectId().toString();
        AuthUser authUser = new AuthUser.AuthUserBuilder()
                .setId(USER_ID)
                .setName(TestConstant.TEST_EMAIL)
                .setPassword("password")
                .build();
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setLatitude(TestConstant.DEFAULT_LATITUDE)
                .setLongitude(TestConstant.DEFAULT_LONGITUDE)
                .build();
        locationList.add(location);
        User user_one = new User.UserBuilder()
                .setId(USER_ID)
                .setName(TestConstant.TEST_EMAIL)
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList)
                .setPassword("password")
                .build();
        Mockito.when(userRepository.findAll()).thenReturn(Stream.of(user_one).collect(Collectors.toList()));
        Assert.assertEquals(user_one.getName(), userService.getUserByEmail(authUser).getName());
    }

    @Test
    @DisplayName("Test For User Not Found by Email")
    public final void testGetUser_NegativeTest_EmailNotFoundException() throws EmailNotFoundException {
        System.out.println("Initializing  Unit Test Case for User Not Found By Email.");
        thrown.expect(EmailNotFoundException.class);
        thrown.expectMessage(CoreMatchers.is("EMAIL ID NOT FOUND"));
        String USER_ID = new ObjectId().toString();
        AuthUser authUser = new AuthUser.AuthUserBuilder()
                .setId(USER_ID)
                .setName(TestConstant.TEST_EMAIL)
                .setPassword("password")
                .build();
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setLatitude(TestConstant.DEFAULT_LATITUDE)
                .setLongitude(TestConstant.DEFAULT_LONGITUDE)
                .build();
        locationList.add(location);
        User user_one = new User.UserBuilder()
                .setId(USER_ID)
                .setName(TestConstant.TEST_MISMATCH_EMAIL)
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList)
                .setPassword("password")
                .build();
        Mockito.when(userRepository.findAll()).thenReturn(Stream.of(user_one).collect(Collectors.toList()));
        Assert.assertEquals(user_one.getName(), userService.getUserByEmail(authUser).getName());
    }

    @Test
    @DisplayName("Test for Deleting the User")
    public void deleteUser() {
        String id = "5de314d94b4f7b5220e77fdb";
        System.out.println("Initializing  Unit Test Case for Deleting a User By ID.");
        List<Location> locationList = new ArrayList<>();
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setLatitude(TestConstant.DEFAULT_LATITUDE)
                .setLongitude(TestConstant.DEFAULT_LONGITUDE)
                .build();
        locationList.add(location);
        User user_one = new User.UserBuilder()
                .setId(id)
                .setName(faker.name().fullName())
                .setCountry(TestConstant.TEST_COUNTRY)
                .setCity(TestConstant.TEST_CITY)
                .setDevice(device)
                .setLocation(locationList)
                .setPassword("password1223")
                .build();
        userService.deleteUserById(id);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.eq(user_one.getId()));
    }
}
