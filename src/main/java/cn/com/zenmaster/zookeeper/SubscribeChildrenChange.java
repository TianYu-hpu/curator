package cn.com.zenmaster.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 获取节点子节点列表改变(增加节点、删除节点)
 * @author TianYu
 *
 */
public class SubscribeChildrenChange {

	static class ZKChildListener implements IZkChildListener {

		public void handleChildChange(String parentPath, List<String> children) throws Exception {
			System.out.println("path:" + parentPath);
			System.out.println("children:" + children.toString());
		}
		
	}
	
	public static void main(String[] args) {
		try {
			/*
			 * ZkClient 提供了一些序列化接口，zookeeper在设置值的时候是拿到字符串然后得到这个字符串的字节数组，
			 * 当传入对象数据的时候需要对对象进行序列化，在取数据的时候需要反序列化，在 zcclient 中已经封装了一些序列化接口，其中就有一个提供 Java 对象的序列化器
			 */
			ZkClient zc = new ZkClient("192.168.0.30:2181", 10000, 10000, new SerializableSerializer());
			System.out.println("连接成功");
			zc.subscribeChildChanges("/zkClient", new ZKChildListener());
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
