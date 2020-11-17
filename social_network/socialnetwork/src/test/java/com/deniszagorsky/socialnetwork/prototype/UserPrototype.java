package com.deniszagorsky.socialnetwork.prototype;

import com.deniszagorsky.socialnetwork.domain.basic.User;
import com.deniszagorsky.socialnetwork.domain.embeddable.Email;
import com.deniszagorsky.socialnetwork.domain.embeddable.Sex;
import com.deniszagorsky.socialnetwork.dto.UserEditDto;
import com.deniszagorsky.socialnetwork.dto.UserPageDto;
import com.deniszagorsky.socialnetwork.dto.UserRegistrationDto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.UUID.fromString;

public class UserPrototype {

    public static UserPageDto userPageDto() {
        User user = user();

        UserPageDto userPageDto = new UserPageDto();
        userPageDto.setFirstName(user.getFirstName());
        userPageDto.setLastName(user.getLastName());
        userPageDto.setCity(user.getCity());
        userPageDto.setAge(user.getDateOfBirth());
        userPageDto.setInstagram(user.getInstagram());
        userPageDto.setWebsite(user.getWebsite());
        userPageDto.setPosts(user.getPosts());

        return userPageDto;
    }

    public static UserEditDto userEditDto() {
        User user = user();

        UserEditDto userEditDto = new UserEditDto();
        userEditDto.setEmail(user.getEmail().getEmail());
        userEditDto.setPassword(user.getPassword());
        userEditDto.setFirstName(user.getFirstName());
        userEditDto.setLastName(user.getLastName());
        userEditDto.setDayOfBirth(user.getDateOfBirth().getDayOfMonth());
        userEditDto.setMonthOfBirth(user.getDateOfBirth().getMonthValue());
        userEditDto.setYearOfBirth(user.getDateOfBirth().getYear());
        userEditDto.setSex(user.getSex());
        userEditDto.setCity(user.getCity());
        userEditDto.setInterests(user.getInterests());
        userEditDto.setInstagram("zagorsky.denis");
        userEditDto.setWebsite("deniszagorsky.com");

        return userEditDto;
    }

    public static User user() {
        String strId = "68fdb339-26ea-4218-8d28-b7171cad3a31";
        UUID id = fromString(strId);

        User user = userWithoutId();
        user.setId(id);

        return user;
    }

    public static User userWithoutId() {
        UserRegistrationDto userRegistrationDto = userRegistrationDto();

        User user = new User();
        user.setEmail(new Email(userRegistrationDto.getEmail()));
        user.setPassword(encrypt(userRegistrationDto.getPassword()));
        user.setLastName(userRegistrationDto.getLastName());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setDateOfBirth(LocalDate.of(
                userRegistrationDto.getYearOfBirth(),
                userRegistrationDto.getMonthOfBirth(),
                userRegistrationDto.getDayOfBirth()
        ));
        user.setSex(userRegistrationDto.getSex());
        user.setCity(userRegistrationDto.getCity());
        user.setInterests(userRegistrationDto.getInterests());

        return user;
    }

    public static UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto("donnchadh.mr@gmail.com", "qwerty12",
                "Zagorsky", "Denis",
                4, 2, 1998,
                Sex.MALE, "Saint-Petersburg", "Software engineering");
    }

    private static String encrypt(String password) {
        try {
            return new String(MessageDigest.getInstance("MD5").digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot encrypt password!");
        }
    }

}
