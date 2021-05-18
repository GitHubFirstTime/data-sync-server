package com.rlc.rlcframework.transaction;

import com.rlc.rlcframework.transaction.MultiDataSourceTransaction;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 支持Service内多数据源切换的Factory
 * @date 2021/5/18 11:55
 */
public class MultiDataSourceTransactionFactory extends SpringManagedTransactionFactory {
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new MultiDataSourceTransaction(dataSource);
    }
}
