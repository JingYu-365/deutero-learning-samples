package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.security.UserProvider;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/2 10:45.
 */
public class TableInputFormatSelf extends TableInputFormat {
    private static final Logger LOG = LoggerFactory.getLogger(TableInputFormatSelf.class);

    public static String remote_user = "";
    public static final String DEFAULT_REMOTE_USER = "hbase";

    @Override
    protected void initialize(JobContext context) throws IOException {
        // Do we have to worry about mis-matches between the Configuration from setConf and the one
        // in this context?
        TableName tableName = TableName.valueOf(super.getConf().get(INPUT_TABLE));
        try {
            Configuration configuration = new Configuration(super.getConf());
            User user = UserProvider.instantiate(configuration)
                    .create(UserGroupInformation.createRemoteUser("".equals(remote_user) ? DEFAULT_REMOTE_USER : remote_user.trim()));
            initializeTable(ConnectionFactory.createConnection(configuration, user), tableName);
        } catch (Exception e) {
            LOG.error(StringUtils.stringifyException(e));
        }
    }
}