/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.rlcbase.websocket;

import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ConditionNotifyServerPool {
	
	protected static final Logger loger = LoggerFactory.getLogger(ConditionNotifyServerPool.class);

	private static final Map<String,WebSocket> socketMap = new HashMap<String,WebSocket>();
	
	/**
	 * 向连接池中添加连接
	 * @param conn
	 * @param id
	 */
	public static void addSocket(WebSocket conn,String id){
		socketMap.put(id,conn);	//添加连接
	}
	
	/**
	 * 移除连接池中的连接
	 * @param conn
	 */
	public static void removeSocket(WebSocket conn){
		String temp = "";
		Set<String> keySet = socketMap.keySet();
		for(String id:keySet){
			WebSocket webSocket = socketMap.get(id);
			if(conn.equals(webSocket)){
				temp = id;
			}
		}
		socketMap.remove(temp);	//移除连接
	}
	/**
	 * 移除连接池中的连接
	 * @param id
	 */
	public static WebSocket getSocketObjectId(String id){
		Set<String> keySet = socketMap.keySet();
		synchronized (keySet) {
			for (String obejctId : keySet) {
				if(obejctId.equals(id)){
					return socketMap.get(obejctId);
				}
			}
		}
		return null;
	}
	/**
	 * 向指定Socket通道发送消息
	 * @param message
	 */
	public static void sendMessage(String message,String id){
		WebSocket webSocket = socketMap.get(id);
		if(webSocket != null){
			loger.info("id:{}",id);
			webSocket.send(message);
		}
	}
}
