/**
 * Project Name:watchDog
 * File Name:KafkaConsumer.java
 * Package Name:com.jeeplus.modules.watchdog.utils.kafka
 * Date:2020年3月25日上午9:37:49
 * Copyright (c) 2020, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.kafka.consumer;
/**
 * ClassName:KafkaConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年3月25日 上午9:37:49 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

import com.rlc.rlcbase.config.Global;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class KafkaConsumerThreadHandler<K,V> {
	private Logger logger = LoggerFactory.getLogger(KafkaConsumerThreadHandler.class);
	// KafkaConsumer实例
    final KafkaConsumer<K, V> consumer;
    // ExecutorService实例
    private ExecutorService executors;
    private String topic;
    // 位移信息offsets
    private final HashMap<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
	/*public Consumer<String, Object> getConsumer() {
		System.out.println("进入getConsumer1");
		Properties properties = new Properties();
		//第一步 初始化化kafka服务配置Properties--具体配置可以抽到实际的Property配置文件
		//设备地址
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.66:9092");
		//反序列化器
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		//offset自动提交
		//properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		//重置offset---当团体名发生改变时且消费者保存的初始offset未过期时，消费者会从头消费
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		//初始化消费者
		consumer=new KafkaConsumer<>(properties);
		System.out.println("进入getConsumer2");
		return consumer;
	}*/
	public KafkaConsumerThreadHandler(String brokerList, String groupId, String topic) {
		logger.info("进入getConsumer1");
		Properties properties = new Properties();
		//第一步 初始化化kafka服务配置Properties--具体配置可以抽到实际的Property配置文件
		//设备地址
//		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.66:9092");//本地
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.170.6.80:9092");
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Global.getConfig("spring.kafka.bootstrap-servers"));
		//反序列化器
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		//非自动提交位移信息
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		//重置offset---当团体名发生改变时且消费者保存的初始offset未过期时，消费者会从头消费
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		//初始化消费者
		consumer=new KafkaConsumer<>(properties);
		logger.info("进入getConsumer2");
		// 消费者订阅消息，并实现重平衡rebalance
        // rebalance监听器，创建一个匿名内部类。使用rebalance监听器前提是使用消费者组（consumer group）。
        // 监听器最常见用法就是手动提交位移到第三方存储以及在rebalance前后执行一些必要的审计操作。
        consumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener() {
            /**
             * 在coordinator开启新一轮rebalance前onPartitionsRevoked方法会被调用。
             */
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                // 提交位移
                consumer.commitSync(offsets);
            }
            /**
             * rebalance完成后会调用onPartitionsAssigned方法。
             */
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                // 清除位移信息
                offsets.clear();
            }
        });
	}
	 /**
     * 消费主方法
     * 
     * @param threadNumber
     *            线程池中的线程数
     */
    public void consume(int threadNumber) {
        executors = new ThreadPoolExecutor(
                threadNumber, 
                threadNumber, 
                0L, 
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000), 
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            // 消费者一直处于等待状态，等待消息消费
            while (true) {
                // 从主题中获取消息
                ConsumerRecords<K, V> records = consumer.poll(Duration.ofSeconds(1000L));
                // 如果获取到的消息不为空
                if (!records.isEmpty()) {
                    // 将消息信息、位移信息封装到ConsumerWorker中进行提交
                    executors.submit(new ConsumerWorker<>(records, offsets,topic));
                }
                // 调用提交位移信息、尽量降低synchronized块对offsets锁定的时间
                this.commitOffsets();
            }
        } catch (WakeupException e) {
            // 此处忽略此异常的处理.WakeupException异常是从poll方法中抛出来的异常
            //如果不忽略异常信息，此处会打印错误哦，亲
            //e.printStackTrace();
        } finally {
            // 调用提交位移信息、尽量降低synchronized块对offsets锁定的时间
            this.commitOffsets();
            // 关闭consumer
            consumer.close();
            close();
        }
    }

    /**
     * 尽量降低synchronized块对offsets锁定的时间
     */
    private void commitOffsets() {
        // 尽量降低synchronized块对offsets锁定的时间
        Map<TopicPartition, OffsetAndMetadata> unmodfiedMap;
        // 保证线程安全、同步锁，锁住offsets
        synchronized (offsets) {
            // 判断如果offsets位移信息为空，直接返回，节省同步锁对offsets的锁定的时间
            if (offsets.isEmpty()) {
                return;
            }
            // 如果offsets位移信息不为空，将位移信息offsets放到集合中，方便同步
            unmodfiedMap = Collections.unmodifiableMap(new HashMap<>(offsets));
            // 清除位移信息offsets
            offsets.clear();
        }
        // 将封装好的位移信息unmodfiedMap集合进行同步提交
        // 手动提交位移信息
        consumer.commitSync(unmodfiedMap);
    }

    /**
     * 关闭消费者
     */
    public void close() {
        // 在另一个线程中调用consumer.wakeup();方法来触发consume的关闭。
        // KafkaConsumer不是线程安全的，但是另外一个例外，用户可以安全的在另一个线程中调用consume.wakeup()。
        // wakeup()方法是特例，其他KafkaConsumer方法都不能同时在多线程中使用
        consumer.wakeup();
        // 关闭ExecutorService实例
        if(null!=executors) {
        	executors.shutdown();
        }
    }
}

