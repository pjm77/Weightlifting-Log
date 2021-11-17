package com.panpawelw.weightliftinglog.serviceIT;

import com.panpawelw.weightliftinglog.models.User;
import com.panpawelw.weightliftinglog.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.panpawelw.weightliftinglog.constants.TEST_USER;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

  @Autowired
  private UserService service;

  @Before
  public void setup() {
    populateDatabase();
  }

  @Test
  public void findUserByEmailShouldReturnUser() {
    assertEquals("test@email1", service.findUserByEmail("test@email1").getEmail());
  }

  @Test
  public void findUserByEmailShouldReturnNull() {
    assertNull(service.findUserByEmail("madeup@email.lol"));
  }

  @Test
  public void saveUserShouldSucceed() {
    Long count = service.count();
    User user = new User(TEST_USER);
    user.setId(null);
    assertNotNull(service.saveUser(user));
    assertEquals(service.count() - count, 1);
  }

  @Test
  public void deleteUserByIdShouldSucceed() {
    Long count = service.count();
    service.deleteUserById(1);
    assertEquals(count - service.count(), 1);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void deleteUserByIdShouldFail() {
    service.deleteUserById(100);
  }

  void populateDatabase() {
    for (Long i=1L; i<5; i++) {
      String suffix = String.valueOf(i);
      User user = new User(i, "Test name" + suffix, "Test password" + suffix,
          "Test password" + suffix, "test@email" + suffix, true,
          "Test First Name" + suffix, "Test Last Name" + suffix, i.intValue(),
          true, "USER", new ArrayList<>());
      service.saveUser(user);
    }
  }
}
