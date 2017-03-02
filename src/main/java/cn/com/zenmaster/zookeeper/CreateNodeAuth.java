package cn.com.zenmaster.zookeeper;

import java.util.ArrayList;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

/**
 * 创建节点
 * @author TianYu
 *
 */
public class CreateNodeAuth {

	public static void main(String[] args) {
		try {
			/**
			 * 重试策略
			 * 重试次数
			 * 重试超时时间
			 * 重试时间间隔
			 */
			//RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
			//RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
			RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
			/**
			 * CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.0.30:2181", retryPolicy);
			 */
			
			CuratorFramework client = CuratorFrameworkFactory
										.builder()
										.connectString("192.168.0.30")
										.connectionTimeoutMs(1000)
										.sessionTimeoutMs(1000)
										.retryPolicy(retryPolicy)
										.build();
			client.start();
			System.out.println("连接成功");
			
			ACL aclIp = new ACL(Perms.READ,new Id("ip","192.168.1.105"));
			ACL aclDigest = new ACL(Perms.READ|Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("jike:123456")));
			ArrayList<ACL> acls = new ArrayList<ACL>();
			acls.add(aclDigest);
			acls.add(aclIp);
			
			String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.PERSISTENT)
							.withACL(acls)
							.forPath("/curator", "curator".getBytes());
			System.out.println(path);
			
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
