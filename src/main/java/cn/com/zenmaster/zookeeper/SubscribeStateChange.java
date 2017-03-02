package cn.com.zenmaster.zookeeper;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 获取节点状态发生改变
 * @author TianYu
 *
 */
public class SubscribeStateChange {

	static class ZKStateListener implements IZkStateListener {

		public void handleNewSession() throws Exception {
			System.out.println("处理新的回话");
			
		}

		public void handleSessionEstablishmentError(Throwable arg0)throws Exception {
			System.out.println("处理回话建立出错");
		}

		public void handleStateChanged(KeeperState state) throws Exception {
			System.out.println("状态改变:" + state.toString());
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
			zc.subscribeStateChanges(new ZKStateListener());
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
