package com.rlc.rlcbase.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 分布式事务管理
 * @date 2021/5/12 10:13
 */
@Configuration
public class TXManagerConfig {
    @Bean(name = "transactionManager")
    @Primary
    public JtaTransactionManager regTransactionManager () throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        UserTransaction userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(10000);
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
