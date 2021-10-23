package vip.efactory.common.base.bean;

import lombok.Getter;

/**
 * Description:服务层操作方法类型枚举，以便明确通知观察者!
 *
 * @Author dbdu
 * @Date 2020-09-06
 */
@Getter
public enum OperateTypeEnum {
    //
    SAVE(1, "增加类操作"),
    UPDATE(2, "修改类操作"),
    DELETE(3, "删除类操作"),
    ;

    OperateTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 类型的编码
     */
    private final Integer code;

    /**
     * 类型的描述
     */
    private final String desc;
}
