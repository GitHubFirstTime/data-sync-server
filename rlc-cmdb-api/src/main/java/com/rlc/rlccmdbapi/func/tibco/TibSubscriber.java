package com.rlc.rlccmdbapi.func.tibco;

import com.rlc.rlccmdbapi.func.tibco.config.Constants;
import com.tibco.tibrv.*;

public class TibSubscriber implements TibrvMsgCallback {
    private TibrvRvdTransport transport = null;

    //初始化TibrvListener
    public TibSubscriber() {
        try {
            Tibrv.open(Tibrv.IMPL_NATIVE);
            transport = new TibrvRvdTransport(
                    Constants.TIBCO_SERVICE,//服务IP
                    Constants.TIBCO_NETWORK,//
                    Constants.TIBCO_DAEMON);// 守护进程；后台程序
//                    ConfigUtil.getProperty(Constants.TIBCO_SERVICE),//服务IP
//                    ConfigUtil.getProperty(Constants.TIBCO_NETWORK),//
//                    ConfigUtil.getProperty(Constants.TIBCO_DAEMON));// 守护进程；后台程序
            new TibrvListener(Tibrv.defaultQueue(), this, transport, Constants.TIBCO_SUBJECT, null);
        } catch (TibrvException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        while (true) {
            try {
                Tibrv.defaultQueue().dispatch();
            }
            catch(TibrvException ex) {
                ex.printStackTrace();
            }
            catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //回调方法，监听到指定Subject时触发
    @Override
    public void onMsg(TibrvListener listener, TibrvMsg msg) {
        if (msg != null) {
            String receivedMsg;
            try {
                receivedMsg = (String)msg.get("msg");
                System.out.println(receivedMsg);
            } catch (TibrvException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        TibSubscriber tibListener = new TibSubscriber();
        tibListener.listen();
    }

}
