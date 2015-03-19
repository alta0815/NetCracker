package ru.ncedu.java.tasks;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.lang.reflect.*;
import java.util.*;

/**
 * un1acker
 * 12.03.2015
 */
public class ReflectionsImpl implements Reflections {

    @Override
    public Object getFieldValueByName(Object object, String fieldName) throws NoSuchFieldException {
        if(object == null || fieldName == null) throw new NullPointerException();
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object fieldValue = null;
        try {
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValue;
    }

    @Override
    public Set<String> getProtectedMethodNames(Class clazz) {
        if(clazz == null) throw new NullPointerException();
        Set<String> allProtectedMethodName = new TreeSet<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isProtected(method.getModifiers())) {
                allProtectedMethodName.add(method.getName());
            }
        }
        return allProtectedMethodName;
    }

    @Override
    public Set<Method> getAllImplementedMethodsWithSupers(Class clazz) {
        if(clazz == null) throw new NullPointerException();
        Set<Method> allMethods = new HashSet<>();
        Class c = clazz;
        while (c != null) {
            Collections.addAll(allMethods, c.getDeclaredMethods());
            c = c.getSuperclass();
        }
        return allMethods;
    }

    @Override
    public List<Class> getExtendsHierarchy(Class clazz) {
        if(clazz == null) throw new NullPointerException();
        List<Class> extendsHierarchyForClazz = new ArrayList<>();
        Class parent = clazz.getSuperclass();
        while (parent != null) {
            extendsHierarchyForClazz.add(parent);
            parent = parent.getSuperclass();
        }
        return extendsHierarchyForClazz;
    }

    @Override
    public Set<Class> getImplementedInterfaces(Class clazz) {
        if(clazz == null) throw new NullPointerException();
        Set<Class> implementedInterfaces = new HashSet<>();
        Collections.addAll(implementedInterfaces, clazz.getInterfaces());
        return implementedInterfaces;
    }

    @Override
    public List<Class> getThrownExceptions(Method method) {
        if(method == null) throw new NullPointerException();
        List<Class> allThrownException = new ArrayList<>();
        Collections.addAll(allThrownException, method.getExceptionTypes());
        return allThrownException;
    }

    @Override
    public String getFooFunctionResultForDefaultConstructedClass() {
        Class clazz = SecretClass.class;
        String resultInvokeFoo = null;
        Object secretInstance;
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            secretInstance = constructor.newInstance();
            Method methodFoo = clazz.getDeclaredMethod("foo");
            methodFoo.setAccessible(true);
            resultInvokeFoo = (String) methodFoo.invoke(secretInstance);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return resultInvokeFoo;
    }

    @Override
    public String getFooFunctionResultForClass(String constructorParameter, String string, Integer... integers) {
        Class clazz = SecretClass.class;
        String resultInvokeFoo = null;
        Object secretInstance;
        try {
            Constructor constructor = clazz.getConstructor(constructorParameter.getClass());
            secretInstance = constructor.newInstance(constructorParameter);
            Method methodFoo = clazz.getDeclaredMethod("foo", string.getClass(), integers.getClass());
            methodFoo.setAccessible(true);
            resultInvokeFoo = (String)methodFoo.invoke(secretInstance, string, integers);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return resultInvokeFoo;
    }
}
