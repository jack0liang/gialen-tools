package com.gialen.tools.common.lock;

import com.gialen.common.model.GLResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


@Slf4j
@Component
@Aspect
public class RepeaterLockAop {

    @Autowired
    private MapLock mapLock;

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.gialen.tools..*.*Controller.*(..)) && @annotation(RepeaterLock)")
    public void pointcut() {
    }

    /**
     * 切切切
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object invoker(ProceedingJoinPoint pjp) throws Throwable {
        Class<?>[] paramCls = new Class[pjp.getArgs().length];
        for (int i = 0; i < paramCls.length; i++) {
            paramCls[i] = pjp.getArgs()[i].getClass();
        }

        Method method = MethodUtils.getMatchingMethod(pjp.getSignature().getDeclaringType(), pjp.getSignature().getName(), paramCls);
        RepeaterLock annotation = method.getAnnotation(RepeaterLock.class);

        String key = resolveRepeaterKey(method, pjp.getArgs(), annotation);
        if (!StringUtils.isEmpty(key) && !mapLock.tryLock(key, annotation.timeout())) {
            return GLResponse.fail("兄dei~~,请让俺缓缓！");
        }
        Object object = null;
        try {
            object = pjp.proceed();
        }finally {
            mapLock.unlock(key);
        }
        return object;
    }

    private String resolveRepeaterKey(Method method, Object[] args, RepeaterLock annotation) {
        StringBuilder keyBuilder = new StringBuilder(annotation.keyPrefix());
        if (!StringUtils.isEmpty(annotation.fieldName())) {
            String[] parameterNames = lookupParameterNames(method);
            for (int i = 0; i < args.length; i++) {
                String fieldValue = getArgsFieldValue(args[i], parameterNames[i], annotation.fieldName());
                if (!StringUtils.isEmpty(fieldValue)) {
                    keyBuilder.append(fieldValue);
                    break;
                }
            }
        }
        return keyBuilder.toString();
    }


    private String getArgsFieldValue(Object args, String argsName, String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }
        if (fieldName.equals(argsName)) {
            return String.valueOf(args);
        }

        try {
            Field field = args.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(args);
            return null == value ? null : String.valueOf(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }

    }

    /**
     * 获取当前方法的参数名(pjp.getArgs()返回的参数已经排序过了)
     *
     * @param method
     * @return
     */
    private String[] lookupParameterNames(Method method) {
        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        return parameterNameDiscoverer.getParameterNames(method);
    }


}
