package com.rlc.rlccmdbapi.func.tibco;

import com.rlc.rlccmdbapi.func.tibco.config.Constants;
import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;

public class TibPublisher {
    private TibrvRvdTransport transport = null;

    public TibPublisher() {
        try {
            Tibrv.open(Tibrv.IMPL_NATIVE);
            transport = new TibrvRvdTransport(
                    Constants.TIBCO_SERVICE,//服务IP
                    Constants.TIBCO_NETWORK,//
                    Constants.TIBCO_DAEMON);// 守护进程；后台程序
        } catch (TibrvException e) {
            e.printStackTrace();
        }
    }

    public void publish(String sendMessage) {
        try {
            TibrvMsg msg = new TibrvMsg();
            msg.setSendSubject(Constants.TIBCO_SUBJECT);
            msg.add("msg", sendMessage);
            transport.send(msg);
        } catch (TibrvException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TibPublisher tibPublisher = new TibPublisher();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            tibPublisher.publish("Hello, World。第"+i+"次");
        }
    }
}
