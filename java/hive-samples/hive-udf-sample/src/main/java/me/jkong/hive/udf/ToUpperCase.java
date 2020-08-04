package me.jkong.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

/**
 * 自定义 UDF 实现将英文小写转为英文大写
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/16 17:26.
 */
public class ToUpperCase extends GenericUDF {

    /**
     * 初始化 GenericUDF 实例对象，每个 GenericUDF 被实例化之后只会被调用一次。并且在evaluate()方法之前调用。
     * <p>
     * 该方法检查接受正确的参数类型和参数个数。
     *
     * @param arguments 参数
     * @return 检测结果
     * @throws UDFArgumentException 如果参数的类型或者参数的个数出现错误可以抛出此异常。
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        return null;
    }

    /**
     * 这个方法类似UDF的evaluate()方法。
     * <p>
     * 它处理真实的参数，并返回最终结果。
     *
     * @param arguments 参数
     * @return 处理后的结果
     * @throws HiveException 异常
     */
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        return null;
    }


    /**
     * 这个方法用于当实现的GenericUDF出错的时候，打印出提示信息。
     * <p>
     * 而提示信息就是你实现该方法最后返回的字符串。
     *
     * @param children
     * @return
     */
    @Override
    public String getDisplayString(String[] children) {
        return null;
    }
}