package cn.com.zenmaster.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 获取节点数据发生改变
 * @author TianYu
 *
 */
public class SubscribeDataChange {

	static class ZKDataListener implements IZkDataListener {

		public void handleDataChange(String parentPath, Object data) throws Exception {
			System.out.println("path:" + parentPath);
			System.out.println("data:" + data.toString());
		}

		public void handleDataDeleted(String datapath) throws Exception {
			System.out.println("data delete:" + datapath);
		}
		
	}
	
	public static void main(String[] args) {
		try {
			/*
			 * ZkClient 提供了一些序列化接口，zookeeper在设置值的时候是拿到字符串然后得到这个字符串的字节数组，
			 * 当传入对象数据的时候需要对对象进行序列化，在取数据的时候需要反序列化，在 zcclient 中已经封装了一些序列化接口，其中就有一个提供 Java 对象的序列化器
			 */
			ZkClient zc = new ZkClient("192.168.0.30:2181", 10000, 10000, new BytesPushThroughSerializer());
			System.out.println("连接成功");
			zc.subscribeDataChanges("/zkClient", new ZKDataListener());
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
