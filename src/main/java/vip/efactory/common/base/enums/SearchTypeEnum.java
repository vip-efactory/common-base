package vip.efactory.common.base.enums;

import lombok.Getter;

/**
 * Description: 实体属性的查询条件,例如:age ge 35
 *
 * @author dbdu
 * Created at:2019-03-09 16:06,
 */
@Getter
public enum SearchTypeEnum {
    FUZZY(0, "模糊查询"),
    EQ(1, "等于查询"),
    RANGE(2, "范围查询"),
    NE(3, "不等于查询"),
    LT(4, "小于查询"),
    LE(5, "小于等于查询"),
    GT(6, "大于查询"),
    GE(7, "大于等于查询"),
    // 3.0+ 开始
    IS_NULL(8, "Null值查询"),
    // 3.0+
    NOT_NULL(9, "非Null值查询"),
    // 3.0+
    LEFT_LIKE(10, "左模糊查询"),
    // 3.0+
    RIGHT_LIKE(11, "右模糊查询"),
    // 3.4+ ,内置暂不支持not in查询！除非手写hql或sql实现
    IN(12, "包含查询"),
    // mbp支持,jpa内置暂不支持not in查询！除非手写hql或sql实现，
    NOT_IN(13, "不包含查询"),
    IS_EMPTY_STRING(14, "是空串"),
    NOT_EMPTY_STRING(15, "非空串"),
    ;

    /**
     * 枚举值
     */
    private final int value;

    /**
     * 枚举说明
     */
    private final String desc;

    SearchTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 这个方法目前没有用到
     *
     * @param value 类型值
     * @return SearchTypeEnum
     */
    public static SearchTypeEnum getByValue(int value) {
        switch (value) {
            case 1:
                return EQ;
            case 2:
                return RANGE;
            case 3:
                return NE;
            case 4:
                return LT;
            case 5:
                return LE;
            case 6:
                return GT;
            case 7:
                return GE;
            case 8:
                return IS_NULL;
            case 9:
                return NOT_NULL;
            case 10:
                return LEFT_LIKE;
            case 11:
                return RIGHT_LIKE;
            case 12:
                return IN;
            case 13:
                // ejpa 不支持
                return NOT_IN;
            case 14:
                return IS_EMPTY_STRING;
            case 15:
                return NOT_EMPTY_STRING;
            default:
                // 0 或其他情况,则为模糊查询
                return FUZZY;
        }

    }

}
