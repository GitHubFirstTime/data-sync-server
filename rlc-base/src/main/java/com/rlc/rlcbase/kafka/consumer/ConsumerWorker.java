package com.rlc.rlcbase.kafka.consumer;

import com.rlc.rlcbase.cache.CacheUtils;
import com.rlc.rlcbase.utils.Base64Util;
import com.rlc.rlcbase.utils.Encodes;
import com.rlc.rlcbase.websocket.ConditionNotifyServerPool;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConsumerWorker<K, V> implements Runnable {
    Logger logger  = LoggerFactory.getLogger(ConsumerWorker.class);
    // 获取到的消息
    private final ConsumerRecords<K, V> records;
    // 位移信息
    private final Map<TopicPartition, OffsetAndMetadata> offsets;

    private String topic;

    /**
     * ConsumerWorker有参构造方法
     *
     * @param records
     *            获取到的消息
     * @param offsets
     *            位移信息
     */
    public ConsumerWorker(ConsumerRecords<K, V> records, Map<TopicPartition, OffsetAndMetadata> offsets, String topic) {
        this.records = records;
        this.offsets = offsets;
        this.topic = topic;
    }

    /**
     *
     */
    @Override
    public void run() {
        // 获取到分区的信息
        for (TopicPartition partition : records.partitions()) {
            // 获取到分区的消息记录
            List<ConsumerRecord<K, V>> partConsumerRecords = records.records(partition);
            // 遍历获取到的消息记录
            for (ConsumerRecord<K, V> record : partConsumerRecords) {
                // 打印消息
                logger.info("topic: {},key:{},partition: {},offset: {},消息记录: {}",record.topic(),record.key(),record.partition(),record.offset(),record.value());

                String sn = String.valueOf(CacheUtils.get(record.topic(), String.valueOf(record.key())));
                logger.info("消费者：sn:{},record.key:{}",sn,record.key());
                if(Objects.equals(sn, record.key())) {
                    logger.info("消费者：key equals");
                    String result = Encodes.unescapeHtml(String.valueOf(record.value()));
                    logger.info("消费者：key equals result："+result);
                    String base64 = Base64Util.DECODE(result);
                    logger.info("消费者：key equals base64："+base64);
                    JSONObject resultJson = JSONObject.fromObject(base64);
                    logger.info("消费者：key equals resultJson："+resultJson.toString());
                    if(1==resultJson.getInt("success")) {
                        logger.info("消费者：success1");
                        JSONObject returnMsg = new JSONObject();
                        returnMsg.put("success", resultJson.getString("success"));
                        String cmd=resultJson.getString("cmd");
						/*if(Objects.equals("open_door", cmd)) {
							if(1==resultJson.getInt("success")) {
								returnMsg.put("msg", "远程开门成功");
							}else {
								returnMsg.put("msg", "远程开门失败");
							}
						}*/
                        String cmdNik="";
                        switch (cmd) {
                            case "open_door"://配置变更通知
                                if(1==resultJson.getInt("success")) {
                                    returnMsg.put("msg", "远程开门成功");
                                }else {
                                    returnMsg.put("msg", "远程开门失败");
                                }
                                cmdNik="openDoor";
                                break;
                            case "reboot"://重启系统
                                if(1==resultJson.getInt("success")) {
                                    returnMsg.put("msg", "设备重启成功");
                                }else {
                                    returnMsg.put("msg", "设备重启失败");
                                }
                                cmdNik="reboot";
                                break;
                            case "get_config"://配置变更通知
                                if(1==resultJson.getInt("success")) {
                                    returnMsg.put("msg", "设备配置更新成功");
                                }else {
                                    returnMsg.put("msg", "设备配置更新失败");
                                }
                                cmdNik="getConfig";
                                break;
                            case "refreshFacesList"://清空人脸白名单
                                if(1==resultJson.getInt("success")) {
                                    returnMsg.put("msg", "设备清空人脸白名单成功");
                                }else {
                                    returnMsg.put("msg", "设备清空人脸白名单失败");
                                }
                                cmdNik="refreshFacesList";
                                break;
                            case "resetData"://清除设备数据
                                if(1==resultJson.getInt("success")) {
                                    returnMsg.put("msg", "设备数据清除成功");
                                }else {
                                    returnMsg.put("msg", "设备数据清除失败");
                                }
                                cmdNik="resetData";
                                break;
                            case "resetLogData"://清空门禁缓存的积压数据
                                if(1==resultJson.getInt("success")) {
                                    returnMsg.put("msg", "设备缓存清除成功");
                                }else {
                                    returnMsg.put("msg", "设备缓存清除失败");
                                }
                                cmdNik="resetLogData";
                                break;
                            default:
                                break;
                        }

                        try {
                            Thread.sleep(500l);
                        } catch (InterruptedException e) {
                            logger.info("消费监听并消费成功，并开始给前端相应websocket消息");
                        }
                        ConditionNotifyServerPool.sendMessage(returnMsg.toString(), sn);
                        logger.info("消费者：success2");
                        CacheUtils.remove(cmdNik, sn);
//						consumer.commitAsync();
//						returnStr = "0";
//						break;
                    }
                }
            }
            // 上报位移信息。获取到最后的位移消息，由于位移消息从0开始，所以最后位移减一获取到位移位置
            long lastOffset = partConsumerRecords.get(partConsumerRecords.size() - 1).offset();
            // 同步锁，锁住offsets位移
            synchronized (offsets) {
                // 如果offsets位移不包含partition这个key信息
                if (!offsets.containsKey(partition)) {
                    // 就将位移信息设置到map集合里面
                    offsets.put(partition, new OffsetAndMetadata(lastOffset + 1));
                } else {
                    // 否则，offsets位移包含partition这个key信息
                    // 获取到offsets的位置信息
                    long curr = offsets.get(partition).offset();
                    // 如果获取到的位置信息小于等于上一次位移信息大小
                    if (curr <= lastOffset + 1) {
                        // 将这个partition的位置信息设置到map集合中。并保存到broker中。
                        offsets.put(partition, new OffsetAndMetadata(lastOffset + 1));
                    }
                }
            }
        }
    }
}