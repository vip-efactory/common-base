package vip.efactory.common.base.bean;

import lombok.Data;

/**
 * Description: 这个类是缓存一致性维护时使用，
 * 如果A组件需要精细地维护B组件的缓存就需要本类，以便B组件根据本类的信息进行精细化更新自己的缓存数据。
 * 当然，如果不需要精细，直接通知B清除B所有缓存。
 *
 * @Author dbdu
 * @Date 2020-09-06
 */
@Data
public class ObserveData {
    /**
     * 哪个类更新了,例如：User
     * 之所以有这个字段，是因为一个组件可能观察多个其他组件，同过本字段就可以清楚知道谁更新了
     */
    private String className;

    /**
     * 操作的类型是什么，例如：增--1;改--2;删--3
     */
    private Integer operType;

    /**
     * 更新的主键集合是什么，多个主键用分隔符分割，然后解析时使用对应的分隔符即可！
     * 建议支持的分隔符：中英文的逗号分号，和中文的顿号！
     * String[] keys = data.split(",|;|、|，|；");
     */
    private String data;

}
