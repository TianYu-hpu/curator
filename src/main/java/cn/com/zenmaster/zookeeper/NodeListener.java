package cn.com.zenmaster.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * 创建节点
 * @author TianYu
 *
 */
public class NodeListener {

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
			
			final NodeCache cache = new NodeCache(client, "/curator");
			cache.start();
			cache.getListenable().addListener(new NodeCacheListener() {
				
				public void nodeChanged() throws Exception {
					byte[] ret = cache.getCurrentData().getData();
					System.out.println("new data:"+new String(ret));
				}
			});
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
