package org.blab.mde.ee.spring;

import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class EntityEngineSpringTest {
  protected static CompositeEngine engine;

  @BeforeClass
  public static void setup() {
    engine = new CompositeEngine("org.blab.mde.ee.spring")
        .start();
  }

  @Test
  public void dtoTest() {
    EntityAbc abc = new EntityAbc();
    abc.setName("abc");
    abc.setSize(12);
    abc.setCreatedOn(LocalDateTime.now());

    EntityXyz xyz = new EntityXyz();
    xyz.setDescription("just a test");
    abc.setXyz(xyz);

    DTOAbc dtoAbc = Mapper.mapTo(abc, DTOAbc.class);

    assertNotNull(dtoAbc);
    assertEquals(abc.getName(), dtoAbc.getName());
    assertEquals(abc.getSize(), dtoAbc.getSize());
    assertEquals(abc.getName().length(), dtoAbc.getLen());
    assertEquals(xyz.getDescription(), dtoAbc.getDescription());
  }

  @Test
  public void compositeDTOTest() {
    Address address = engine.create(Address.class);
    address.setCountry("China");

    User user = engine.create(User.class);
    user.setUsername("adam");
    user.setAddress(address);

    UserDTO userDTO = Mapper.mapTo(user, UserDTO.class);
    assertNotNull(userDTO);
    assertEquals(user.getUsername(), userDTO.getUsername());
    assertEquals(user.getAddress().getCountry(), userDTO.getCountry());
  }

  @Test
  public void compositeDTOMapTest() {
    Address address = engine.create(Address.class);
    address.setCountry("China");

    User user = engine.create(User.class);
    user.setUsername("adam");
    user.setAddress(address);

    Map<String, Object> userDTO = Mapper.mapOf(user, UserDTO.class);
    assertNotNull(userDTO);
    assertEquals(user.getUsername(), userDTO.get("username"));
    assertEquals(user.getAddress().getCountry(), userDTO.get("country"));
  }
}

class EntityAbc {
  private String name;
  private long size;
  private LocalDateTime createdOn;
  private EntityXyz xyz;

  public EntityXyz getXyz() {
    return xyz;
  }

  public void setXyz(EntityXyz xyz) {
    this.xyz = xyz;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }
}

class EntityXyz {
  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

class DTOAbc {
  private String name;
  private long size;
  private long len;
  private String description;

  public String getDescription() {
    return description;
  }

  @Mapper.Binding("xyz.description")
  public void setDescription(String description) {
    this.description = description;
  }

  public long getLen() {
    return len;
  }

  @Mapper.Binding("name.length()")
  public void setLen(long len) {
    this.len = len;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

@Composite
interface User {
  @Property
  String getUsername();

  void setUsername(String username);

  @Property
  Address getAddress();

  void setAddress(Address address);
}

@Composite
interface Address {
  @Property
  String getCountry();

  void setCountry(String country);
}

@Composite
interface UserDTO {
  @Property
  String getUsername();

  void setUsername(String username);

  @Property
  @Mapper.Binding("address.country")
  String getCountry();

  void setCountry(String country);
}
