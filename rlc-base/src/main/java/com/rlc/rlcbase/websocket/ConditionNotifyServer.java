/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.rlcbase.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;


public class ConditionNotifyServer extends WebSocketServer{

	protected Logger logger = LoggerFactory.getLogger(ConditionNotifyServer.class);
	public ConditionNotifyServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public ConditionNotifyServer(InetSocketAddress address) {
		super(address);
	}

	/**
	 * 触发连接事件
	 */
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		
	}

	/**
	 * 触发关闭事件
	 */
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		ConditionNotifyServerPool.removeSocket(conn);
	}

	/**
	 * 客户端发送消息到服务器时触发事件
	 */
	@Override
	public void onMessage(WebSocket conn, String message){
		if(null != message && message.startsWith(Constant._online_id_)){//点对点连接
			String id = message.replaceFirst(Constant._online_id_, "");
			logger.info("socket_sn:",id);
			ConditionNotifyServerPool.addSocket(conn, id);
		}if(null != message && message.contains(Constant._msg_)){//
			String []arr = message.split(Constant._msg_);
			String fromObject = arr[0];
			String toObject = arr[1];
			String msg = arr[2];
			
			WebSocket toObjectConn = ConditionNotifyServerPool.getSocketObjectId(toObject);
			if(toObjectConn != null){
				ConditionNotifyServerPool.sendMessage(message, toObject);
			}
		}
	}
	
	@Override
	public void onMessage(WebSocket conn, ByteBuffer buffer){
	}

	public void onFragment( WebSocket conn, Framedata fragment ) {
	}

	/**
	 * 触发异常事件
	 */
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			//some errors like port binding failed may not be assignable to a specific websocket
		}
	}


}

