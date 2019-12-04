package com.asep.app.helper;

import com.asep.app.constants.TestConstant;
import com.asep.app.entity.Device;
import com.asep.app.entity.Location;
import com.asep.app.entity.User;
import com.github.javafaker.Faker;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDataBinder {

    private static ObjectId objectId = new ObjectId();
    private static List<Location> locationList = new ArrayList<>();
    private static Set<User> users = new HashSet<>();


    public static Set<User> userDataBinderWorstCase() {
        Device device = new Device.DeviceBuilder().setDeviceId("as12033239jjidwd").setPhoneType("Iphone").build();
        Location location = new Location.LocationBuilder().setTimestamp(System.currentTimeMillis()).setLatitude(TestConstant.DEFAULT_LATITUDE).setLongitude(TestConstant.DEFAULT_LONGITUDE).build();
        locationList.add(location);
        Faker faker = new Faker();
        User user_two = new User.UserBuilder().setId(objectId.toString()).setName(faker.name().fullName()).setCountry("United Kingdom").setCity("London").setDevice(device).setLocation(locationList).setPassword("password1223").build();
        User user_three = new User.UserBuilder().setId(objectId.toString()).setName(faker.name().fullName()).setCountry("United Kingdom").setCity("London").setDevice(device).setLocation(locationList).setPassword("password1223diwnc").build();
        users.add(user_two);
        users.add(user_three);
        return users;
    }

    public static Set<User> userDataBinderForBestCase() {
        Device device = new Device.DeviceBuilder().setDeviceId("powqmnzx1209878mza").setPhoneType("Android").build();
        Location location = new Location.LocationBuilder().setTimestamp(System.currentTimeMillis()).setLatitude(TestConstant.DEFAULT_LATITUDE).setLongitude(TestConstant.DEFAULT_LONGITUDE).build();
        locationList.add(location);
        User user_one = new User.UserBuilder().setId(objectId.toString()).setName("Arindam Dutta").setCountry("United Kingdom").setCity("London").setDevice(device).setLocation(locationList).setPassword("password").build();
        users.add(user_one);
        return users;
    }

/*    public static void write(String userInformation, String validator) throws IOException {
        if (validator.equalsIgnoreCase(TestConstant.VALID_USER)) {
            Files.write(Paths.get("validuser.properties"), userInformation.getBytes());
        } else if (validator.equalsIgnoreCase(TestConstant.INVALID_USER)) {
            Files.write(Paths.get("invaliduser.properties"), userInformation.getBytes());
        }
    }*/
}
