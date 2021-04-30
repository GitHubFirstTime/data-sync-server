package com.rlc.rlccmdbapi.modules.func.tibco.test;

import com.tibco.tibrv.*;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TibcoPublishTest implements TibrvMsgCallback {
    private TibrvRvdTransport transport = null;

    private String returnMessage;

    private static String mySubject = "EapSubject";
    private static String myDataField = "xmlData";

    private static String replySubject;

    public void init() {
        try {
            Tibrv.open(Tibrv.IMPL_NATIVE);
//            transport = new TibrvRvdTransport("7509","","tcp:10.170.7.21:7509");
            transport = new TibrvRvdTransport("7777",";239.77.77.77","7500");
            replySubject = transport.createInbox();
            TibrvQueue queue = new TibrvQueue();
            new TibrvListener(queue, this, transport, replySubject, null);
            new TibrvDispatcher(queue);
        } catch (TibrvException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMsg(TibrvListener tibrvListener, TibrvMsg tibrvMsg) {
        System.out.println("第一步,监控触发");
        if (tibrvMsg != null) {
            String receivedMsg = null;
            try {
                receivedMsg = (String) tibrvMsg.get(myDataField);
            } catch (TibrvException e) {
                e.printStackTrace();
            }
            this.setReturnMessage(receivedMsg);

        }
    }

    public void publish(String rsvMethod, String sendMessage) {
        String uid = "1234565432";

        TibrvMsg msg = new TibrvMsg();
        String reciveMsg = null;
        this.setReturnMessage(null);
        try {
            String xml = getXml(uid, rsvMethod, replySubject, sendMessage);
//             String xml = sendMessage;
            msg.setSendSubject(mySubject);
            msg.add(myDataField, xml);
            transport.send(msg);
        } catch (TibrvException e) {
            e.printStackTrace();
        }

        CompletableFuture<String> future =CompletableFuture.supplyAsync(() -> {
            long crTime =System.currentTimeMillis();
            long nxTime = 0;
            long diffTime = 0;
            while (true && diffTime < 10000){
                System.out.println("第二步");
                if(!StringUtils.isEmpty(this.getReturnMessage())){
                    System.out.println("第三步");
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nxTime = System.currentTimeMillis();
                diffTime = nxTime -crTime;
            }
            return returnMessage;
        });

        try {
            reciveMsg = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (!StringUtils.isEmpty(reciveMsg)) {
            Document document = null;
            try {
                document = DocumentHelper.parseText(reciveMsg);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            Element msgBody = root.element("msgBody");
            reciveMsg = msgBody.asXML();
        }
        System.out.println(reciveMsg);
    }

    public static void main(String args[]){
        String HH04 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><bodType>12332</bodType><eqpId>eqptId</eqpId><userId>MES</userId></msgBody></msg>";
        String HH08 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><storageRow>2</storageRow><storageColumn>3</storageColumn><baseId>baseId</baseId><eqpId>eqptId</eqpId><userId>MES</userId></msgBody></msg>";
        String HH0C = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><othOutPortNo>3</othOutPortNo><fixtureTyp>fixture</fixtureTyp><ohtPortNo>2</ohtPortNo><eqpId>eqptId</eqpId><userId>MES</userId></msgBody></msg>";
        String HH10 = "<msg><rvHeader><source><application/><msgBus/><domain/><address/></source><destination><application/><msgBus/><domain/><address/></destination><replyApplication><addressNo/><address applicationName=\"EAP\" seq=\"1\"/></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>eprecpvers</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprecpvers</txId><locale>EN</locale><retCode>2</retCode><retMsg/></msgHeader><msgBody><recipeList><recipeItem>000</recipeItem></recipeList><recipeList><recipeItem>001</recipeItem></recipeList><recipeList><recipeItem>002</recipeItem></recipeList><recipeList><recipeItem>003</recipeItem></recipeList><recipeList><recipeItem>004</recipeItem></recipeList><recipeList><recipeItem>005</recipeItem></recipeList><recipeList><recipeItem>006</recipeItem></recipeList><recipeList><recipeItem>007</recipeItem></recipeList><recipeList><recipeItem>008</recipeItem></recipeList><recipeList><recipeItem>009</recipeItem></recipeList><recipeList><recipeItem>010</recipeItem></recipeList><recipeList><recipeItem>011</recipeItem></recipeList><recipeList><recipeItem>012</recipeItem></recipeList><recipeList><recipeItem>013</recipeItem></recipeList><recipeList><recipeItem>014</recipeItem></recipeList><recipeList><recipeItem>015</recipeItem></recipeList><recipeList><recipeItem>016</recipeItem></recipeList><recipeList><recipeItem>017</recipeItem></recipeList><recipeList><recipeItem>018</recipeItem></recipeList><recipeList><recipeItem>019</recipeItem></recipeList><recipeList><recipeItem>020</recipeItem></recipeList><recipeList><recipeItem>021</recipeItem></recipeList><recipeList><recipeItem>022</recipeItem></recipeList><recipeList><recipeItem>023</recipeItem></recipeList><recipeList><recipeItem>024</recipeItem></recipeList><recipeList><recipeItem>025</recipeItem></recipeList><recipeList><recipeItem>026</recipeItem></recipeList><recipeList><recipeItem>027</recipeItem></recipeList><recipeList><recipeItem>028</recipeItem></recipeList><recipeList><recipeItem>029</recipeItem></recipeList><recipeList><recipeItem>030</recipeItem></recipeList><recipeList><recipeItem>031</recipeItem></recipeList><recipeList><recipeItem>032</recipeItem></recipeList><recipeList><recipeItem>033</recipeItem></recipeList><recipeList><recipeItem>034</recipeItem></recipeList><recipeList><recipeItem>035</recipeItem></recipeList><recipeList><recipeItem>036</recipeItem></recipeList><recipeList><recipeItem>037</recipeItem></recipeList><recipeList><recipeItem>038</recipeItem></recipeList><recipeList><recipeItem>039</recipeItem></recipeList><recipeList><recipeItem>040</recipeItem></recipeList><recipeList><recipeItem>041</recipeItem></recipeList><recipeList><recipeItem>042</recipeItem></recipeList><recipeList><recipeItem>043</recipeItem></recipeList><recipeList><recipeItem>044</recipeItem></recipeList><recipeList><recipeItem>045</recipeItem></recipeList><recipeList><recipeItem>046</recipeItem></recipeList><recipeList><recipeItem>047</recipeItem></recipeList><recipeList><recipeItem>048</recipeItem></recipeList><recipeList><recipeItem>049</recipeItem></recipeList><recipeList><recipeItem>050</recipeItem></recipeList><recipeList><recipeItem>051</recipeItem></recipeList><recipeList><recipeItem>052</recipeItem></recipeList><recipeList><recipeItem>053</recipeItem></recipeList><recipeList><recipeItem>054</recipeItem></recipeList><recipeList><recipeItem>055</recipeItem></recipeList><recipeList><recipeItem>056</recipeItem></recipeList><recipeList><recipeItem>057</recipeItem></recipeList><recipeList><recipeItem>058</recipeItem></recipeList><recipeList><recipeItem>059</recipeItem></recipeList><recipeList><recipeItem>060</recipeItem></recipeList><recipeList><recipeItem>061</recipeItem></recipeList><recipeList><recipeItem>062</recipeItem></recipeList><recipeList><recipeItem>063</recipeItem></recipeList><recipeList><recipeItem>064</recipeItem></recipeList><recipeList><recipeItem>065</recipeItem></recipeList><recipeList><recipeItem>066</recipeItem></recipeList><recipeList><recipeItem>067</recipeItem></recipeList><recipeList><recipeItem>068</recipeItem></recipeList><recipeList><recipeItem>069</recipeItem></recipeList><recipeList><recipeItem>070</recipeItem></recipeList><recipeList><recipeItem>071</recipeItem></recipeList><recipeList><recipeItem>072</recipeItem></recipeList><recipeList><recipeItem>073</recipeItem></recipeList><recipeList><recipeItem>074</recipeItem></recipeList><recipeList><recipeItem>075</recipeItem></recipeList><recipeList><recipeItem>076</recipeItem></recipeList><recipeList><recipeItem>077</recipeItem></recipeList><recipeList><recipeItem>078</recipeItem></recipeList><recipeList><recipeItem>079</recipeItem></recipeList><recipeList><recipeItem>080</recipeItem></recipeList><recipeList><recipeItem>081</recipeItem></recipeList><recipeList><recipeItem>082</recipeItem></recipeList><recipeList><recipeItem>083</recipeItem></recipeList><recipeList><recipeItem>084</recipeItem></recipeList><recipeList><recipeItem>085</recipeItem></recipeList><recipeList><recipeItem>086</recipeItem></recipeList><recipeList><recipeItem>087</recipeItem></recipeList><recipeList><recipeItem>088</recipeItem></recipeList><recipeList><recipeItem>089</recipeItem></recipeList><recipeList><recipeItem>090</recipeItem></recipeList><recipeList><recipeItem>091</recipeItem></recipeList><recipeList><recipeItem>092</recipeItem></recipeList><recipeList><recipeItem>093</recipeItem></recipeList><recipeList><recipeItem>094</recipeItem></recipeList><recipeList><recipeItem>095</recipeItem></recipeList><recipeList><recipeItem>096</recipeItem></recipeList><recipeList><recipeItem>097</recipeItem></recipeList><recipeList><recipeItem>098</recipeItem></recipeList><recipeList><recipeItem>099</recipeItem></recipeList><recipeList><recipeItem>100</recipeItem></recipeList><recipeList><recipeItem>101</recipeItem></recipeList><recipeList><recipeItem>102</recipeItem></recipeList><recipeList><recipeItem>103</recipeItem></recipeList><recipeList><recipeItem>104</recipeItem></recipeList><recipeList><recipeItem>105</recipeItem></recipeList><recipeList><recipeItem>106</recipeItem></recipeList><recipeList><recipeItem>107</recipeItem></recipeList><recipeList><recipeItem>108</recipeItem></recipeList><recipeList><recipeItem>109</recipeItem></recipeList><recipeList><recipeItem>110</recipeItem></recipeList><recipeList><recipeItem>111</recipeItem></recipeList><recipeList><recipeItem>112</recipeItem></recipeList><recipeList><recipeItem>113</recipeItem></recipeList><recipeList><recipeItem>114</recipeItem></recipeList><recipeList><recipeItem>115</recipeItem></recipeList><recipeList><recipeItem>116</recipeItem></recipeList><recipeList><recipeItem>117</recipeItem></recipeList><recipeList><recipeItem>118</recipeItem></recipeList><recipeList><recipeItem>119</recipeItem></recipeList><recipeList><recipeItem>120</recipeItem></recipeList><recipeList><recipeItem>121</recipeItem></recipeList><recipeList><recipeItem>122</recipeItem></recipeList><recipeList><recipeItem>123</recipeItem></recipeList><recipeList><recipeItem>124</recipeItem></recipeList><recipeList><recipeItem>125</recipeItem></recipeList><recipeList><recipeItem>126</recipeItem></recipeList><recipeList><recipeItem>127</recipeItem></recipeList><recipeList><recipeItem>128</recipeItem></recipeList><recipeList><recipeItem>129</recipeItem></recipeList><recipeList><recipeItem>130</recipeItem></recipeList><recipeList><recipeItem>131</recipeItem></recipeList><recipeList><recipeItem>132</recipeItem></recipeList><recipeList><recipeItem>133</recipeItem></recipeList><recipeList><recipeItem>134</recipeItem></recipeList><recipeList><recipeItem>135</recipeItem></recipeList><recipeList><recipeItem>136</recipeItem></recipeList><recipeList><recipeItem>137</recipeItem></recipeList><recipeList><recipeItem>138</recipeItem></recipeList><recipeList><recipeItem>139</recipeItem></recipeList><recipeList><recipeItem>140</recipeItem></recipeList><recipeList><recipeItem>141</recipeItem></recipeList><recipeList><recipeItem>142</recipeItem></recipeList><recipeList><recipeItem>143</recipeItem></recipeList><recipeList><recipeItem>144</recipeItem></recipeList><recipeList><recipeItem>145</recipeItem></recipeList><recipeList><recipeItem>146</recipeItem></recipeList><recipeList><recipeItem>147</recipeItem></recipeList><recipeList><recipeItem>148</recipeItem></recipeList><recipeList><recipeItem>149</recipeItem></recipeList><recipeList><recipeItem>150</recipeItem></recipeList><recipeList><recipeItem>151</recipeItem></recipeList><recipeList><recipeItem>152</recipeItem></recipeList><recipeList><recipeItem>153</recipeItem></recipeList><recipeList><recipeItem>154</recipeItem></recipeList><recipeList><recipeItem>155</recipeItem></recipeList><recipeList><recipeItem>156</recipeItem></recipeList><recipeList><recipeItem>157</recipeItem></recipeList><recipeList><recipeItem>158</recipeItem></recipeList><recipeList><recipeItem>159</recipeItem></recipeList><recipeList><recipeItem>160</recipeItem></recipeList><recipeList><recipeItem>161</recipeItem></recipeList><recipeList><recipeItem>162</recipeItem></recipeList><recipeList><recipeItem>163</recipeItem></recipeList><recipeList><recipeItem>164</recipeItem></recipeList><recipeList><recipeItem>165</recipeItem></recipeList><recipeList><recipeItem>166</recipeItem></recipeList><recipeList><recipeItem>167</recipeItem></recipeList><recipeList><recipeItem>168</recipeItem></recipeList><recipeList><recipeItem>169</recipeItem></recipeList><recipeList><recipeItem>170</recipeItem></recipeList><recipeList><recipeItem>171</recipeItem></recipeList><recipeList><recipeItem>172</recipeItem></recipeList><recipeList><recipeItem>173</recipeItem></recipeList><recipeList><recipeItem>174</recipeItem></recipeList><recipeList><recipeItem>175</recipeItem></recipeList><recipeList><recipeItem>176</recipeItem></recipeList><recipeList><recipeItem>177</recipeItem></recipeList><recipeList><recipeItem>178</recipeItem></recipeList><recipeList><recipeItem>179</recipeItem></recipeList><recipeList><recipeItem>180</recipeItem></recipeList><recipeList><recipeItem>181</recipeItem></recipeList><recipeList><recipeItem>182</recipeItem></recipeList><recipeList><recipeItem>183</recipeItem></recipeList><recipeList><recipeItem>184</recipeItem></recipeList><recipeList><recipeItem>185</recipeItem></recipeList><recipeList><recipeItem>186</recipeItem></recipeList><recipeList><recipeItem>187</recipeItem></recipeList><recipeList><recipeItem>188</recipeItem></recipeList><recipeList><recipeItem>189</recipeItem></recipeList><recipeList><recipeItem>190</recipeItem></recipeList><recipeList><recipeItem>191</recipeItem></recipeList><recipeList><recipeItem>192</recipeItem></recipeList><recipeList><recipeItem>193</recipeItem></recipeList><recipeList><recipeItem>194</recipeItem></recipeList><recipeList><recipeItem>195</recipeItem></recipeList><recipeList><recipeItem>196</recipeItem></recipeList><recipeList><recipeItem>197</recipeItem></recipeList><recipeList><recipeItem>198</recipeItem></recipeList><recipeList><recipeItem>199</recipeItem></recipeList><recipeVer>200416155910</recipeVer><eqpId>V88-01-MO-06</eqpId><userId>MES</userId><recipeId>s1</recipeId><workId>workId</workId></msgBody></msg>";
        String HH11 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>eprecpvers</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><buffLineId>buffLine</buffLineId><eqpId>eqptId</eqpId><outCnt>20</outCnt><userId>MES</userId></msgBody></msg>";
        String HH12 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><buffLineId>buffLine</buffLineId><boardTyp>U</boardTyp><eqpId>eqptId</eqpId><userId>MES</userId></msgBody></msg>";
        String HH13 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><buffLineId>buffLine</buffLineId><eqpId>eqptId</eqpId><outCnt>20</outCnt><userId>MES</userId></msgBody></msg>";
        String HH14 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><buffLineId>buffLine</buffLineId><freeFlag>true</freeFlag><eqpId>eqptId</eqpId><userId>MES</userId></msgBody></msg>";
        String HH15 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><labelType>1</labelType><unitId>unit</unitId><eqpId>eqptId</eqpId><userId>MES</userId><pointNo>2</pointNo><labelReady>true</labelReady></msgBody></msg>";
        String HH16 = "<msg><rvHeader><source><application></application><msgBus></msgBus><domain></domain><address></address></source><destination><application></application><msgBus></msgBus><domain></domain><address></address></destination><replyApplication><addressNo></addressNo><address applicationName=\"EAP\" seq=\"1\"></address></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg></retMsg></msgHeader><msgBody><boxCnt>2</boxCnt><buffLineId>buffLine</buffLineId><prdGrade>A</prdGrade><cartonCnt>1</cartonCnt><eqpId>eqptId</eqpId><cartonTyp>1</cartonTyp><userId>MES</userId></msgBody></msg>";
        String mesg = "<晶棒分流_request><rvHeader><source><application/><msgBus/><domain/><address/></source><destination><application/><msgBus/><domain/><address/></destination><replyApplication><addressNo/><address applicationName=\"EAP\" seq=\"1\"/></replyApplication></rvHeader><msgHeader><srvAddr>123</srvAddr><reqAddr>456</reqAddr><msgOwner>332</msgOwner><srvMethod>testMethod</srvMethod><srvId>MES</srvId><timeStamp>2020-02-18 12:00:00</timeStamp><timeOut>60</timeOut><txId>eprespltyp</txId><locale>EN</locale><retCode>2</retCode><retMsg/></msgHeader><msgBody><buffFullFlag>1</buffFullFlag><eqpId>123432</eqpId><workId>12345</workId></msgBody></晶棒分流_request>";
        String fdcTest = "<userId/><eqpId>XQ-B2-09</eqpId><lotId>BDBH2303933H66064</lotId><productId>M6P170</productId><planId>HZ-DW-1</planId><planVersion/><stepSeq>1.10.400</stepSeq><stepId>HZ-XQ-01</stepId><edcPlanName>HZ-EDC-XQ</edcPlanName><edcSpecListNo>12</edcSpecListNo><edcSpecList><edcSpecId>LEFT_CABLE_TENS</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>RIGHT_CABLE_TENS</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>LEFT_ROOM_TENS</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>RIGHT_ROOM_TENS</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>LEFT_ARM_TENS</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>RIGHT_ARM_TENS</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>LEFT_TENS_ARM_SWI</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>RIGHT_TENS_ARM_SWI</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>COL_FLOW</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>INPUT_COL_TEMP</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>OUTPUT_COL_TEMP</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList><edcSpecList><edcSpecId>WIR_NET_SLO</edcSpecId><measurementType>Lot</measurementType><numberOfUnits>0</numberOfUnits><numberOfSites>0</numberOfSites><numberOfSamples>2</numberOfSamples></edcSpecList>";
        String areYouWareRequest = "<userId>MES</userId><eqpId>eqptId</eqpId>";
        String moveOut = "<userId>MES</userId><eqpId>eqptId</eqpId>";
        TibcoPublishTest tibcoPublishTest = new TibcoPublishTest();
        tibcoPublishTest.init();
        tibcoPublishTest.publish("MESReqEDCData", fdcTest);
        System.exit(0);
    }


    public String getXml(String uid, String sevMethod, String replySubject, String bodyXml){

        //创建一个xml文档
        Document doc = DocumentHelper.createDocument();
        //创建一个名为students的节点，因为是第一个创建，所以是根节点,再通过doc创建一个则会报错。
        Element realMesg = doc.addElement("msg");

        Element rvHeadEle = realMesg.addElement("rvHeader");
        Element rvSourceEle = rvHeadEle.addElement("source");
        rvSourceEle.addElement("application").setText("EAP");
        rvSourceEle.addElement("msgBus").setText("Tibrv");
        rvSourceEle.addElement("domain");
        rvSourceEle.addElement("address").setText(mySubject);

        Element rvDestinationEle = rvHeadEle.addElement("destination");
        rvDestinationEle.addElement("application").setText("MES");
        rvDestinationEle.addElement("msgBus").setText("Tibrv");
        rvDestinationEle.addElement("domain");
        rvDestinationEle.addElement("address").setText("FWSRVdefault");

        Element rvReplyEle = rvHeadEle.addElement("replyApplication");
        rvReplyEle.addElement("addressNo").setText("1");
        Element rvReplyAddress = rvReplyEle.addElement("address");
        rvReplyAddress.addAttribute("applicationName", "EAP");
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


    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }


//    public void creatXml(){
//        MwbwpostsyncI mwbwpostsyncI = new MwbwpostsyncI();
//        mwbwpostsyncI.setServerId("WMS1.0");
//        mwbwpostsyncI.setStockerId("123");
//        List<MwbwpostsyncIA> iary = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            MwbwpostsyncIA mwbwpostsyncIA = new MwbwpostsyncIA();
//            mwbwpostsyncIA.setWhpsId(String.format("%03d", i));
//            iary.add(mwbwpostsyncIA);
//        }
//    }
}
