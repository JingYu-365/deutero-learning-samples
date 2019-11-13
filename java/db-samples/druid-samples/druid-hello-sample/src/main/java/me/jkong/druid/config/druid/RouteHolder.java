package me.jkong.druid.config.druid;

/**
 * 保存当前线程数据源的key
 */
public class RouteHolder {

    private static ThreadLocal<String> routeKey = new ThreadLocal<String>();

    /**
     * 获取当前线程的数据源路由的key
     *
     * @return String
     */
    public static String getRouteKey() {
        return routeKey.get();
    }

    /**
     * 绑定当前线程数据源路由的key
     * 在使用完成之后，必须调用removeRouteKey()方法删除
     *
     * @param key
     */
    public static void setRouteKey(String key) {
        routeKey.set(key);
    }

    /**
     * 删除与当前线程绑定的数据源路由的key
     */
    public static void removeRouteKey() {
        routeKey.remove();
    }
}
