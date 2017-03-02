package cn.com.zenmaster.zookeeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

/**
 * 创建节点
 * @author TianYu
 *
 */
public class ExistNodeASync {

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

			ExecutorService es = Executors.newFixedThreadPool(5);
			
			client.checkExists().inBackground(new BackgroundCallback() {
				
				public void processResult(CuratorFramework arg0, CuratorEvent event)
						throws Exception {
					Stat stat = event.getStat();
					System.out.println(stat);
					System.out.println(event.getContext());
					
				}
			}, "123", es).forPath("/curator");
			
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
