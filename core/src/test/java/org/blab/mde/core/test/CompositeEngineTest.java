package org.blab.mde.core.test;

import com.google.common.collect.Lists;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.This;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.SortedSet;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.meta.BaseComposite;
import org.blab.mde.core.meta.ByteBuddyVisitor;
import org.blab.mde.core.meta.CompositeMeta;
import org.blab.mde.core.meta.ExtensionMeta;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;
import org.blab.mde.core.test.composite.BigDog;
import org.blab.mde.core.test.composite.Cat;
import org.blab.mde.core.test.composite.Cow;
import org.blab.mde.core.test.composite.Dog;
import org.blab.mde.core.test.composite.FlyCow;
import org.blab.mde.core.test.composite.SeaCow;
import org.blab.mde.core.test.composite.Zookeeper;
import org.blab.mde.core.util.CompositeUtil;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.Assert.assertFalse;


@RunWith(BlockJUnit4ClassRunner.class)
public class CompositeEngineTest {
  private static CompositeEngine engine;

  @BeforeClass
  public static void setup() {
    String compositePackage = "org.blab.mde.core.test.composite";
    String mixinPackage = "org.blab.mde.core.test.mixin";
    engine = new CompositeEngine(compositePackage, mixinPackage)
        .withBuilderVisitors(new TestVisitor())
        .start();
  }

  @Test
  public void assembleTest() {
    Dog dog = engine.createOf(Dog.class);
    assertNotNull(dog);

    verifyDog(dog);
  }

  private void verifyDog(Dog dog) {
    assertEquals("Mr None", dog.getName());
    assertEquals(1, dog.getX());
    assertEquals(1, dog.getY());
    assertEquals(2, dog.getAge());

    dog.setName("doudou");
    assertEquals("doudou", dog.getName());
    assertEquals("doudou: www", dog.bark());

    dog.moveTo(10, 20);
    assertEquals(10, dog.getX());
    assertEquals(20, dog.getY());

    dog.reset();
    assertEquals(0, dog.getX());
    assertEquals(0, dog.getY());

    CompositeMeta meta = engine.metaOf(Dog.class);
    assertNotNull(meta);

    assertEquals(Dog.class, meta.getSource());
    assertEquals(CompositeUtil.nameOf(Dog.class), meta.getType().getCanonicalName());
  }

  @Test
  public void createByNameTest() {
    Dog dog = engine.createOf(Dog.class);
    assertNotNull(dog);

    verifyDog(dog);
  }

  @Test
  public void delegationTest() {
    Dog dog = engine.createOf(Dog.class);
    assertNotNull(dog);

    dog.setName("koukou");
    assertEquals("hello: Mr koukou", dog.greeting());
  }

  @Test
  public void catTest() {
    Cat cat = engine.createOf(Cat.class);
    assertNotNull(cat);

    cat.setName("huahua");
    assertEquals("huahua: mmm", cat.meow());

    cat.moveTo(10, 20);
    assertEquals(11, cat.getX());
    assertEquals(21, cat.getY());

    cat.reset();
    assertEquals(-1, cat.getX());
    assertEquals(-1, cat.getY());

    cat.moveTo(1, 2);
    assertEquals(2, cat.getX());
    assertEquals(3, cat.getY());
  }

  @Test
  public void extensionTest() {
    Cow cow = engine.createOf(Cow.class);
    assertNotNull(cow);

    cow.setName("ox");
    cow.setColor("black");

    assertEquals("ox", cow.getName());
    assertEquals("black", cow.getColor());
    assertEquals("ox: [[I'm black]]", cow.cowsay());
    assertEquals("ox: meow...", cow.greeting());

    cow.setX(1);
    cow.setY(2);
    assertEquals(1, cow.getX());
    assertEquals(2, cow.getY());

    cow.moveTo(2, 3);
    assertEquals(2, cow.getX());
    assertEquals(3, cow.getY());

    cow.reset();
    assertEquals(0, cow.getX());
    assertEquals(0, cow.getY());

    FlyCow flyCow = FlyCow.class.cast(cow);
    flyCow.setZ(3);
    assertEquals(3, flyCow.getZ());

    flyCow.fly(4, 5, 6);
    flyCow.tryIt(1);
    assertEquals(4, flyCow.getX());
    assertEquals(5, flyCow.getY());
    assertEquals(6, flyCow.getZ());
    assertEquals("ox: meow...", cow.greeting());

    cow.makeFriends();
    assertEquals(3, cow.getFriends().size());
    assertEquals("puppy", cow.getFriends().get(0));
    assertEquals("piggy", cow.getFriends().get(1));
    assertEquals("kitty", cow.getFriends().get(2));
  }

  @Test
  public void extensionPriorityTest() {
    Cow cow = engine.createOf(Cow.class);
    assertNotNull(cow);

    CompositeMeta meta = engine.metaOf(Cow.class);
    SortedSet<ExtensionMeta> extensions = meta.getExtensions();
    assertEquals(2, extensions.size());

    List<Class<?>> extClasses = Lists.newArrayList(FlyCow.class, SeaCow.class);
    extensions.forEach(it -> assertTrue(extClasses.contains(it.getSource())));
    assertTrue(extensions.first().compareTo(extensions.last()) < 0);
  }

  @Test
  public void objectTargetTest() {
    Cow cow = engine.createOf(Cow.class);
    assertNotNull(cow);

    cow.setColor("red");

    assertEquals("red", cow.getColor());
    assertEquals("red", cow.toString());
    assertEquals("red".hashCode(), cow.hashCode());
  }

  @Test
  public void visitorTest() throws NoSuchFieldException {
    Cow cow = engine.createOf(Cow.class);
    assertNotNull(cow);

    assertNotNull(cow.getClass().getDeclaredField("$$_test_field"));

    assertTrue(cow instanceof TestIt);
    assertEquals("ggg", ((TestIt) cow).gangangan());
  }

  @Test
  public void nameTest() {
    Cow cow = engine.createOf(Cow.class);
    assertNotNull(cow);

    assertEquals("org.blab.mde.core.test.composite.Cow$_$cmpt", cow.getClass().getCanonicalName());
  }

  @Test
  public void testPrintable() {
    Zookeeper zookeeper = engine.createOf(Zookeeper.class);
    assertNotNull(zookeeper);

    zookeeper.setName("Avalon");
    zookeeper.setMale(true);

    assertEquals("[name=Avalon]", zookeeper.toString());
  }

  @Test
  public void createdByNameTest() {
    BigDog dog = engine.createOf(BigDog.class);
    assertNotNull(dog);

    dog.setName("black");

    assertEquals("hello: Big black", dog.greeting());

    dog = engine.createOf(BigDog.class);
    assertNotNull(dog);

    dog.setName("white");

    assertEquals("hello: Big white", dog.greeting());

    assertEquals("na.", dog.getDescription());
  }

  @Test
  public void initTest() {
    Cow cow = engine.createOf(Cow.class);
    assertNotNull(cow);

    assertEquals("red", cow.getColor());
  }

  @Test
  public void getPrefixMethodTest() {
    Dog dog = engine.createOf(Dog.class);
    assertEquals("leon", dog.getMaster());
  }

  @Test
  public void singletonTest() {
    Zookeeper lary = engine.createOf(Zookeeper.class);
    assertNotNull(lary);

    lary.setName("lary");

    assertEquals(lary, engine.createOf(Zookeeper.class));
    assertEquals(lary.getName(), engine.createOf(Zookeeper.class).getName());
  }

  private static class TestVisitor implements MetaElementBuilderVisitor {
    @Override
    public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, CompositeMeta element) {
      if (element.getSource().equals(Cow.class)) {
        builder = builder.defineField("$$_test_field", String.class, Visibility.PUBLIC);
        builder = builder.implement(TestIt.class);
      }
      System.out.println("builder = " + builder.make().getBytes().length);


      return builder;
    }
  }

  public interface TestIt {
    default String gangangan() {
      return "ggg";
    }
  }

  @Test
  public void testSerializable() throws NoSuchFieldException, IllegalAccessException {
    Cow cow = engine.create(Cow.class);
    assertEquals(cow.getClass().getField(ByteBuddyVisitor.SERIAL_VERSION_UID).getLong(cow), ByteBuddyVisitor.DEFAULT_SERIAL_VER);

    Dog dog = engine.create(Dog.class);
    assertEquals(dog.getClass().getField(ByteBuddyVisitor.SERIAL_VERSION_UID).getLong(dog), 2L);
  }

  @Test
  public void testModifiers() throws NoSuchFieldException {
    Cat cat = engine.create(Cat.class);
    int nameModifiers = cat.getClass().getDeclaredField("name").getModifiers();
    assertTrue(Modifier.isVolatile(nameModifiers));
    assertTrue(Modifier.isTransient(nameModifiers));

    int xModifiers = cat.getClass().getDeclaredField("x").getModifiers();
    assertFalse(Modifier.isVolatile(xModifiers));
    assertFalse(Modifier.isTransient(xModifiers));
  }

  @Test
  public void testGeneric() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
    Class<?> resolveReturnType = TypeUtils.getRawType(TestBox.class.getMethod("getData").getGenericReturnType(), TestBox.class);

    Class<?> type = new ByteBuddy()
        .subclass(Object.class)
        .implement(new TypeDescription.ForLoadedType(TestBox.class))
        .defineField("data", resolveReturnType)
        .implement(new TypeDescription.ForLoadedType(Box.class))
        .method(isGetter())
        .intercept(FieldAccessor.ofBeanProperty())
        .make()
        .load(getClass().getClassLoader())
        .getLoaded();

    Object inst = type.newInstance();
    assertTrue(inst instanceof Box);
    assertTrue(inst instanceof TestBox);
  }

  public interface Box<T> {
    T getData();

    void setData(T data);
  }

  public class TestData {
    private int a;

    public int getA() {
      return a;
    }

    public void setA(int a) {
      this.a = a;
    }
  }

  public interface TestBox extends Box<TestData> {
  }

  @Test
  public void testExtensions() throws IllegalAccessException, InstantiationException {
    Class<?> type = new ByteBuddy()
        .subclass(Object.class)
        .implement(new TypeDescription.ForLoadedType(ModDefault.class))
        .defineField("data", List.class)
        .method(isGetter().or(isSetter()))
        .intercept(FieldAccessor.ofBeanProperty())
        .method(named("someString"))
        .intercept(SuperMethodCall.INSTANCE
            .andThen(MethodDelegation.to(ModExtA.class)
                .andThen(MethodDelegation.to(ModExtB.class))))
        .make()
        .load(getClass().getClassLoader())
        .getLoaded();

    Object inst = type.newInstance();
    ModDefault mod = ((ModDefault) inst);
    mod.someString();

    assertNotNull(mod.getData());
    assertEquals(3, mod.getData().size());
    assertTrue(mod.getData().contains("default"));
    assertTrue(mod.getData().contains("extA"));
    assertTrue(mod.getData().contains("extB"));
  }

  public interface ModDefault {
    List<String> getData();

    void setData(List<String> data);

    default void someString() {
      setData(Lists.newArrayList("default"));
    }
  }

  public interface ModExtA {
    static void someString(@This ModDefault root) {
      root.getData().add("extA");
    }
  }

  public interface ModExtB {
    static void someString(@This ModDefault root) {
      root.getData().add("extB");
    }
  }
}

