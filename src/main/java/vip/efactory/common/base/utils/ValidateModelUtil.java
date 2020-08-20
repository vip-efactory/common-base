package vip.efactory.common.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.ValidationUtils;
import vip.efactory.common.i18n.service.ILocaleMsgSourceService;

import javax.validation.*;
import java.util.*;

/**
 * Description:this util is used for Bean annotation validate by manual
 *
 * @author dbdu
 */
@Component
@Slf4j
public class ValidateModelUtil {
    private static ILocaleMsgSourceService localeMessageSourceService;

    //通过下面的方法为静态成员赋值!!!
    @Autowired
    public void setLocaleMessageSourceService(ILocaleMsgSourceService localeMessageSourceService) {
        ValidateModelUtil.localeMessageSourceService = localeMessageSourceService;
    }


    //验证某一个对象,可以指定激活哪个校验组，例如Update.class
    public static Map<String, String> validateModel(Object obj,Class<?>... groups) {

        //用于存储验证后的错误信息
        Map<String, String> errors = new TreeMap<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        //验证某个对象,，其实也可以只验证其中的某一个属性的
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj, groups);
        Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();

        while (iter.hasNext()) {
            ConstraintViolation<Object> currentObj = iter.next();   //再执行一次就会跳到下一个对象
            // 判断是否有用户自定义的message值,如果有就进行处理，如果没有就进行获取默认的国际化对应的key然后手动处理以期达到跟随国际化区域变化的效果
            // 得到检查注解里的参数
            Map<String, Object> params = currentObj.getConstraintDescriptor().getAttributes();
            // 校验注解的message属性是否y
            String messagePropertyValue = (String) params.get("message");
            // 注意:currentObj.getMessage()得到的信息是message属性用户定义的值或者是已经默认的国际化的值，不是我们想要跟随国际化环境变化的值。
            String message = messagePropertyValue;
            // 错误信息对应的字段名
            String property = currentObj.getPropertyPath().toString();

            // log.info("property：" + property + ",check failed:" + message);

            // 处理属性切分,例如：message="AAA{student.age}BBB {property.not.allow.negative}CCCC"
            // 说明包含占位符{}
            if (message.contains("{") && message.contains("}")) {
                //得到：AAA{student.age、BBB {property.not.allow.negative、CCCC
                String[] rawKeys = message.split("}");
                // 得到所有的key
                List<String> keys = new ArrayList<>();
                for (String rawKey : rawKeys) {
                    if (rawKey.contains("{")) {
                        //获取key,例如：student.age
                        String key = rawKey.substring(rawKey.lastIndexOf("{") + 1);
                        keys.add(key);
                    }
                }
                // 替换message里的占位符
                for (String key : keys) {
                    String value = localeMessageSourceService.getMessage(key);
                    if (null == value || value.equals(key)) {
                        value = key;
                        log.warn("missing key [{}]", key);
                    }
                    message = message.replace("{" + key + "}", value);
                }

                // 替换message 里国际化信息里的占位符,使用注解自带的属性值占位，例如：{min}-{max}
                if (message.contains("{") && message.contains("}")) {
                    // 再次替换模板里的占位符
                    for (Map.Entry param : params.entrySet()) {
                        message = message.replace("{" + param.getKey() + "}", param.getValue().toString());
                    }
                }

                errors.put(property, message);
            } else {
                //是硬编码的校验信息，直接取过来
                errors.put(property, message);
            }

        }
        return errors;
    }

    public static void main(String[] args) {
        String message = "AAA{student.age}BBB {property.not.allow.negative}CCCC";
        String[] rawKeys = message.split("}"); //得到：AAA{student.age、BBB {property.not.allow.negative、CCCC
        List<String> keys = new ArrayList<>();
        for (String rawKey : rawKeys) {
            if (rawKey.contains("{")) {
                String key = rawKey.substring(rawKey.lastIndexOf("{") + 1); //获取key,例如：student.age
                keys.add(key);
            }
        }

        System.out.println(keys);

    }
}
