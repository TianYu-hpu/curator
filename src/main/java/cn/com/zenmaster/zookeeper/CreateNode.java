package cn.com.zenmaster.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * 创建节点
 * @author TianYu
 *
 */
public class CreateNode {

	public static void main(String[] args) {
		/*
		 * ZkClient 提供了一些序列化接口，zookeeper在设置值的时候是拿到字符串然后得到这个字符串的字节数组，
		 * 当传入对象数据的时候需要对对象进行序列化，在取数据的时候需要反序列化，在 zcclient 中已经封装了一些序列化接口，其中就有一个提供 Java 对象的序列化器
		 */
		ZkClient zc = new ZkClient("192.168.0.30:2181", 10000, 10000, new SerializableSerializer());
		System.out.println("连接成功");
		User user = new User("tianyu", "123456");
		String path = zc.create("/zkClient", user, CreateMode.PERSISTENT);
		/*ACL aclIp = new ACL(Perms.READ, new Id("ip", "192.168.30.86"));
		ACL aclDigest = new ACL(Perms.WRITE, new Id("digest", "tianyu:123456"));
		List<ACL> aclList = new ArrayList<ACL>();
		aclList.add(aclIp);
		aclList.add(aclDigest);*/
		//String path = zc.create("/zkClient", user, aclList, CreateMode.PERSISTENT);
		
		System.out.println(path);
	}
	

}
