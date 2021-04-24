package com.xinfago.mall.product.exception;

import com.xinfago.common.exception.BizErrorCodeEnum;
import com.xinfago.common.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一请求参数异常处理控制器
 *
 * @author xinfago
 */
//@ResponseBody
//@ControllerAdvice
@RestControllerAdvice(value = "com.xinfago.mall.product.controller")
public class MallControllerExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(item -> {
                errMap.put(item.getField(), item.getDefaultMessage());
            });
        } else {
            return R.error();
        }
        return R.error(BizErrorCodeEnum.VAILD_EXCEPTION.getCode(), BizErrorCodeEnum.VAILD_EXCEPTION.getMsg()).put("data", errMap);
    }

    /**
     * 处理其他异常信息 {@link BizErrorCodeEnum#UNKNOW_EXCEPTION}
     *
     * @param e ex
     * @return r
     */
    @ExceptionHandler(value = Exception.class)
    public R handleUnknownException(Exception e) {
        return R.error(BizErrorCodeEnum.UNKNOW_EXCEPTION.getCode(), BizErrorCodeEnum.VAILD_EXCEPTION.getMsg());
    }
}
