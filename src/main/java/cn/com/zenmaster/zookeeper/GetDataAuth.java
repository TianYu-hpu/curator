package cn.com.zenmaster.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

/**
 * 创建节点
 * @author TianYu
 *
 */
public class GetDataAuth {

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
										.connectString("192.168.0.30:2181")
										.connectionTimeoutMs(1000)
										.sessionTimeoutMs(1000)
										.authorization("ip", "192.168.0.30".getBytes())
										.retryPolicy(retryPolicy)
										.build();
			client.start();
			byte[] data = client.getData().forPath("/curator");
			Stat stat = new Stat();
			client.getData().storingStatIn(stat).forPath("/curator");
			
			System.out.println(new String(data));
			
			System.out.println(stat);
			
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
