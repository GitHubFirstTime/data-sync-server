package com.rlc.rlccmdbapi;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.rlc.rlccmdbapi.modules.datasource.prop.DBConfig_CMDB;
import com.rlc.rlccmdbapi.modules.datasource.prop.DBConfig_FMB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class, DataSourceTransactionManagerAutoConfiguration.class},
scanBasePackages = {"com.rlc.rlcbase","com.rlc.rlccmdbapi"}
)
//@MapperScan({"com.rlc.cmdbServer.modules.test.dao","com.rlc.cmdbServer.modules.cmdb.dao"})
@EnableConfigurationProperties({DBConfig_CMDB.class, DBConfig_FMB.class})
@EnableTransactionManagement(proxyTargetClass=true)//开启事务 用CGLib代理方式
public class RlcCmdbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RlcCmdbApiApplication.class, args);
       /* int port = 0;
        int defaultPort = 8090;
        Future<Integer> future = ThreadUtil.execAsync(() ->{
            int p = 0;
            System.out.println("请于5秒钟内输入端口号, 推荐  8090 、 8091  或者  8092，超过5秒将默认使用 " + defaultPort);
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String strPort = scanner.nextLine();
                if(!NumberUtil.isInteger(strPort)) {
                    System.err.println("只能是数字");
                    continue;
                }
                else {
                    p = Convert.toInt(strPort);
                    scanner.close();
                    break;
                }
            }
            return p;
        });
        try{
            port=future.get(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e){
            port = defaultPort;
        }

        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(RlcCmdbApiApplication.class).properties("server.port=" + port).run(args);*/
    }

}
