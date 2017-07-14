package test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PrivateMethodAndField {
	public static Field getPrivateFieldAndSetAccessible(Object classInstance, String fieldName)
			throws Exception {
		Field field = classInstance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field;
	}

	public static Method getPrivateMethodAndSetAccessible(Object classInstance, String methodName, Class<?>... parameterTypes)
			throws Exception {
		Method method = classInstance.getClass().getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return method;
	}
}
