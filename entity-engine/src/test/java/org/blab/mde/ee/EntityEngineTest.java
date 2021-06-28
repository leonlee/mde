package org.blab.mde.ee;

import org.blab.mde.ee.demo.entity.Account;
import org.blab.mde.ee.demo.entity.user.User;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.junit.Test;

import java.util.Arrays;

import static org.blab.mde.ee.util.EntityUtil.createOf;
import static org.blab.mde.ee.util.EntityUtil.singleton;
import static org.junit.Assert.*;


public class EntityEngineTest extends BaseEntityEngineTest {

    @Test
    public void newEntityTest() {
        Account account = engine.newEntity(Account.class);
        assertNotNull("create entity", account);

        User user = engine.newEntity(User.class);
        assertNotNull("create embedded entity", user);
    }

    @Test
    public void entityPropertiesTest() throws NoSuchFieldException {
        Account account = engine.newEntity(Account.class);
        assertNotNull(account);

        assertNotNull(account.getClass().getDeclaredField("id"));
        assertNotNull(account.getClass().getDeclaredField("name"));

        account.setCreatedBy(1);
        account.setId(1L);
        account.setName("Kawasaki");

        assertEquals(1, account.getCreatedBy());
        assertEquals(Long.valueOf(1), account.getId());
        assertEquals("Kawasaki", account.getName());
    }

    @Test
    public void IdentifiableTest() {
        Account account1 = engine.newEntity(Account.class);
        assertNotNull(account1);

        Account account2 = engine.newEntity(Account.class);
        assertNotNull(account2);

        Account account3 = engine.newEntity(Account.class);
        assertNotNull(account2);

        account1.setId(123L);
        assertEquals(Long.valueOf(123), account1.getId());

        account2.setId(123L);
        assertEquals(Long.valueOf(123), account2.getId());

        account3.setId(323L);
        assertEquals(Long.valueOf(323), account3.getId());

//    assertEquals(account1, account2);
        assertEquals(0, account1.compareTo(account2));

        assertEquals(-1, account1.compareTo(account3));
    }

    @Test
    public void defineFieldTest() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?> clazz = new ByteBuddy()
                .subclass(Object.class)
                .defineField("id", long.class, Visibility.PRIVATE)
                .defineField("name", String.class, Visibility.PRIVATE)
                .make()
                .load(Thread.currentThread().getContextClassLoader(), ClassReloadingStrategy.Default.WRAPPER)
                .getLoaded();

        Object entity = clazz.newInstance();
        assertNotNull(entity);

        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(System.out::println);

        assertNotNull(entity.getClass().getDeclaredField("id"));
        assertNotNull(entity.getClass().getDeclaredField("name"));
    }

    @Test
    public void entityHelperTest() {
        Account account1 = createOf(Account.class);
        assertNotNull(account1);

        Account account2 = createOf(Account.class);
        assertNotNull(account2);
        assertNotEquals(account1.hashCode(), account2.hashCode());

        Account account3 = createOf(Account.class);
        assertNotNull(account3);

        Account account4 = createOf(Account.class);
        assertNotNull(account4);
        assertNotEquals(account3.hashCode(), account4.hashCode());

        Account account5 = singleton(Account.class);
        assertNotNull(account5);
        assertNotEquals(account5.hashCode(), account4.hashCode());
        assertNotEquals(account5.hashCode(), account3.hashCode());
        assertNotEquals(account5.hashCode(), account2.hashCode());
        assertNotEquals(account5.hashCode(), account1.hashCode());

        assertEquals(account5.hashCode(), singleton(Account.class).hashCode());

        Account account6 = singleton(Account.class);
        assertNotNull(account6);
        assertNotEquals(account6.hashCode(), account4.hashCode());
        assertNotEquals(account6.hashCode(), account3.hashCode());
        assertNotEquals(account6.hashCode(), account2.hashCode());
        assertNotEquals(account6.hashCode(), account1.hashCode());

        assertEquals(account6.hashCode(), singleton(Account.class).hashCode());

        assertEquals(account5.hashCode(), account6.hashCode());
    }
}
