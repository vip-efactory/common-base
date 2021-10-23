package vip.efactory.common.base.valid;

import javax.validation.MessageInterpolator;
import java.util.Locale;

/**
 * 国际化的实体属性注解的消息解析器，使用hiberbnate-validator默认的资源文件
 * @author dusuanyun
 */
public class LocalizedMessageInterpolator implements MessageInterpolator {
    private final MessageInterpolator defaultInterpolator;
    private final Locale defaultLocale;

    public LocalizedMessageInterpolator(MessageInterpolator interpolator, Locale locale) {
        this.defaultLocale = locale;
        this.defaultInterpolator = interpolator;
    }

    /**
     * 将用户的 locale 信息传递给消息解释器，而不是使用默认的 JVM locale 信息
     */
    @Override
    public String interpolate(String message, Context context) {
        return defaultInterpolator.interpolate(message, context, this.defaultLocale);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        return defaultInterpolator.interpolate(message, context, locale);
    }
}
