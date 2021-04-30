package com.rlc.rlccmdbapi.modules.func.tibco.test;

import com.tibco.tibrv.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class TibcoReceiveTest implements TibrvMsgCallback {
    private TibrvRvdTransport transport = null;

    private String listenerSubject = "MesSubject";
    private String dataField = "xmlData";

    public void init() {
        try {
            Tibrv.open(Tibrv.IMPL_NATIVE);
            transport = new TibrvRvdTransport("7777",";239.77.77.77","7500");
            TibrvQueue queue = new TibrvQueue();
            new TibrvListener(queue, this, transport, listenerSubject, null);
            new TibrvDispatcher(queue);
        } catch (TibrvException e) {
            e.printStackTrace();
        }
    }

    //回调方法，监听到指定Subject时触发
    @Override
    public void onMsg(TibrvListener listener, TibrvMsg msg) {
        if (msg != null) {
            String receivedMsg = null;
            try {
                receivedMsg = (String)msg.get(dataField);
                System.out.println(receivedMsg);
            } catch (TibrvException e) {
                e.printStackTrace();
            }
            System.out.println("接收消息:["+ receivedMsg + "]");
            Document document = null;
            try {
                document = DocumentHelper.parseText(receivedMsg);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            Element msgHeader = root.element("msgHeader");
            Element msgBody = root.element("msgBody");

            String trxId = msgHeader.elementText("txId");
            String rsvMethod = msgHeader.elementText("srvMethod");
            String mesEqptId = msgBody.elementText("eqpId");
//            String eqptId = PropertiesUtil.getEqptId(mesEqptId);

            String returnXml = getResponse(rsvMethod);

            String replySubject = null;
            String application = null;
            List<String> addressList = new ArrayList<>();
            Element rvHeader = root.element("rvHeader");
            Element replyApplication = rvHeader.element("replyApplication");
            int addressCnt  = Integer.valueOf(replyApplication.elementText("addressNo"));
            List<Element> addressEles = replyApplication.elements("address");
            if (addressCnt > 1){
                for(Element e:addressEles){
                    addressList.add(e.getTextTrim());
                    int seqNo = Integer.valueOf(e.attributeValue("seq"));
                    if(1== seqNo){
                        replySubject = e.getTextTrim();
                        application = e.attributeValue("applicationName");
                    }
                }
            }else{
                Element e = addressEles.get(0);
                addressList.add(e.getTextTrim());
                replySubject = e.getTextTrim();
                application = e.attributeValue("applicationName");
            }

            String realXml = getXml(trxId, rsvMethod, replySubject,application, returnXml);
            returnMesg(addressList,  realXml);
            System.out.println("返回消息:[" + realXml + "]");
        }
    }

    public void returnMesg(List<String> addressList, String xmlMesg){
        TibrvMsg msg = new TibrvMsg();
        try {
            msg.add(dataField,xmlMesg);
            for(String address:addressList){
                msg.setSendSubject(address);
                transport.send(msg);
            }
        } catch (TibrvException e) {
//            System.out.println(StackTrackUtil.stackToString(e));
        }

    }

    public String getXml(String uid, String sevMethod, String replySubject,String applicaiton, String bodyXml){

        //创建一个xml文档
        Document doc = DocumentHelper.createDocument();
        //创建一个名为students的节点，因为是第一个创建，所以是根节点,再通过doc创建一个则会报错。
        Element realMesg = doc.addElement("msg");

        Element rvHeadEle = realMesg.addElement("rvHeader");
        Element rvSourceEle = rvHeadEle.addElement("source");
        rvSourceEle.addElement("application").setText("EAP");
        rvSourceEle.addElement("msgBus").setText("Tibrv");
        rvSourceEle.addElement("domain");
        rvSourceEle.addElement("address").setText(listenerSubject);

        Element rvDestinationEle = rvHeadEle.addElement("destination");
        rvDestinationEle.addElement("application").setText("MES");
        rvDestinationEle.addElement("msgBus").setText("Tibrv");
        rvDestinationEle.addElement("domain");
        rvDestinationEle.addElement("address").setText("FWSRVdefault");

        Element rvReplyEle = rvHeadEle.addElement("replyApplication");
        rvReplyEle.addElement("addressNo").setText("1");
        Element rvReplyAddress = rvReplyEle.addElement("address");
        rvReplyAddress.addAttribute("applicationName", applicaiton);
        rvReplyAddress.addAttribute("seq","1");
        rvReplyAddress.setText(replySubject);

        Element msgHeadEle = realMesg.addElement("msgHeader");
        msgHeadEle.addElement("srvAddr").setText("");
        msgHeadEle.addElement("reqAddr").setText("");
        msgHeadEle.addElement("msgOwner").setText("");
        msgHeadEle.addElement("srvMethod").setText(sevMethod);
        msgHeadEle.addElement("srvId").setText("FWSRVdefault");
        msgHeadEle.addElement("timeStamp").setText("");
        msgHeadEle.addElement("timeOut").setText("");
        msgHeadEle.addElement("txId").setText(uid);
        msgHeadEle.addElement("locale").setText("EN");
        msgHeadEle.addElement("retCode").setText("0");
        msgHeadEle.addElement("retMsg");


        OutputFormat xmlFormat = new OutputFormat();
        xmlFormat.setEncoding("UTF-8");
        xmlFormat.setExpandEmptyElements(true);  //设置为false 就是<name/> 设

        String xml = null;
        try {
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);
            xmlWriter.write(doc);
            xml = stringWriter.toString();
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(xml == null){
            return null;
        }
        StringBuilder realXml  = new StringBuilder(xml.substring(0, xml.length()-6));

        realXml.append("<msgBody>").append(bodyXml).append("</msgBody>").append("</msg>");

        return realXml.toString();
    }

    public String getResponse(String rsvMethod){
        String message = null;
//        switch (rsvMethod){
//            case "EAPReqDateTime":
//
//                MeqptDateO meqptDateO = new MeqptDateO();
//                meqptDateO.setRetCode("11");
//                meqptDateO.setDateTime(DateUtil.getCurrentDateStr3());
//                meqptDateO.setRetMessage("345676543");
//                message = XmlUtils.toXml(meqptDateO);
//                break;
//            case"AreYouThereReply":
//                MalivechkO malivechkO = new MalivechkO();
//                malivechkO.setUserId("MES");
//                malivechkO.setEqpId("222222");
//                message = XmlUtils.toXml(malivechkO);
//                break;
//            default:
//                break;
//        }
        message = rsvMethod;
        return message;
    }

    public static void main(String args[]){
        TibcoReceiveTest tibcoReceiveTest = new TibcoReceiveTest();
        tibcoReceiveTest.init();
    }
}
