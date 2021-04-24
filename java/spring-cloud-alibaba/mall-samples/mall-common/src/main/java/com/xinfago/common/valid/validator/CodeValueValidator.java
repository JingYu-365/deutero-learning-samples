package com.xinfago.common.valid.validator;

import com.xinfago.common.valid.annotation.CodeValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 验证出入的值是否在定义的整型码表当中
 *
 * @author xinfago
 */
public class CodeValueValidator implements ConstraintValidator<CodeValue, Integer> {

    private final Set<Integer> codeSet = new HashSet<>();

    /**
     * 注解信息解析
     *
     * @param codeValue 用户配置的注解信息
     */
    @Override
    public void initialize(CodeValue codeValue) {
        int[] value = codeValue.value();
        for (int i : value) {
            codeSet.add(i);
        }
    }

    /**
     * 根据注解信息，验证用户传值信息
     *
     * @param value                      用户数据
     * @param constraintValidatorContext 验证器上下文
     * @return true：验证通过
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return codeSet.contains(value);
    }
}
