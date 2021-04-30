package com.rlc.rlcbase.common;

import com.rlc.rlcbase.utils.Encodes;
import org.apache.catalina.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@Lazy(false)
public class IdGen implements IdGenerator {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }

    /**
     * Activiti ID 生成
     */

    public String getNextId() {
        return IdGen.uuid();
    }


    public Serializable generateId(Session session) {
        return IdGen.uuid();
    }

    /**
     * getRandomNo:(生成纯数字随机数). <br/>
     * 作为复制流程图节点id用
     *
     * @return String
     * @author RLC_ZYC
     * @since JDK 1.8
     */
    public static String getRandomNo() {
        String returnRandomNo = "";
        String randomNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
        if (randomNo.contains(".")) {
            randomNo = randomNo.replace(".", "");
        }
        String ms = String.valueOf(System.currentTimeMillis());
        randomNo = randomNo.toString().substring(0, 5);
        returnRandomNo = ms + randomNo;
        return returnRandomNo;
    }

    public static void main(String[] args) {
        System.out.println(IdGen.getRandomNo());
        System.out.println(IdGen.uuid());
        System.out.println(IdGen.uuid().length());
        System.out.println(new IdGen().getNextId());
        for (int i = 0; i < 1000; i++) {
            System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
        }
    }

    @Override
    public UUID generateId() {

        // TODO Auto-generated method stub
        return null;
    }
}