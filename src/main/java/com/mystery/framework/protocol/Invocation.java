package com.mystery.framework.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Mystery
 * @description Dubbo传输对象
 */
public class Invocation implements Serializable {

    private String interfaceName;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] params;

    public Invocation() {
    }

    public Invocation(String interfaceName, String methodName, Class<?>[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
