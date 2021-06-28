package org.blab.mde.core.test;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;


public class DynamicBeanTest {
    @Test
    public void test() {
        Order order = new Order();
        order.id = "123";
        order.amount = 100;
        order.status = "created";

        HotelExt hotelExt = new HotelExt();
        hotelExt.room = "202";

        order.ext = hotelExt;

        EntityExt ext = order.getExt();

        FooDao dao = new FooDao();
        System.out.println(dao.clazz);

    }
}

interface BaseDao<E> {
    void setClazz(Class<E> clazz);

    @SuppressWarnings("unchecked")
    default void infer() {
        Class<?> c = this.getClass();
        Type[] interfaces = c.getGenericInterfaces();
        System.out.println("interfaces = " + Arrays.toString(interfaces));
        Type type = interfaces[0];
        setClazz((Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0]);
    }
}

class FooDao implements BaseDao<FooDao> {
    protected Class<?> clazz;

    public FooDao() {
        infer();
    }

    @Override
    public void setClazz(Class<FooDao> clazz) {
        this.clazz = clazz;
    }
}

class Order extends BaseEntity<HotelExt> {
    String id;
    String status;
    long amount;
}

abstract class BaseEntity<X extends EntityExt> implements WithExt<X> {
    public X ext;

    @Override
    public X getExt() {
        return ext;
    }
}

interface WithExt<X> {
    X getExt();
}

class HotelExt implements EntityExt {
    String room;
}

interface EntityExt {

}