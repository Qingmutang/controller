package com.bjpowernode.p2p.admin.fastdfs;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * fastdfs 操作
 * 
 * @author Administrator
 *
 */
public class FastdfsClient {

	/**
	 * fastdfs文件上传
	 * 
	 * @param fileBytes
	 * @param fileExtend
	 * @return
	 */
	public static String[] uploadFile (byte[] fileBytes, String fileExtend) {
		String[] strArray = null;
		TrackerClient trackerClient = null;
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		StorageClient storageClient = null;
		try {
			//1、加载fastdfs客户端配置文件
			ClientGlobal.init("fastdfs_client.conf");
			
			//2、根据上面加载的配置文件，可以创建一个Tracker客户端
			trackerClient = new TrackerClient();
			
			//3、通过Tracker客户端可以获得一个Tracker服务器
			trackerServer = trackerClient.getConnection();
			
			//4、通过Tracker客户端可以获取一个Storage服务器
			storageServer = trackerClient.getStoreStorage(trackerServer);
			
			//5、通过Tracker服务器和Storage服务器可以创建一个storage客户端
			storageClient = new StorageClient(trackerServer, storageServer);
			
			//上传文件到fastdfs文件系统
			strArray = storageClient.upload_file(fileBytes, fileExtend, null);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != storageServer) {
					storageServer.close();
				}
				if (null != trackerServer) {
					trackerServer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strArray;
	}
	
	/**
	 * fastdfs文件删除
	 * 
	 * @param fileBytes
	 * @param fileExtend
	 * @return
	 */
	public static int deleteFile (String group, String filepath) {
		int rtn = -1;
		try {
			//1、加载fastdfs客户端配置文件
			ClientGlobal.init("fastdfs_client.conf");
			
			//2、根据上面加载的配置文件，可以创建一个Tracker客户端
			TrackerClient trackerClient = new TrackerClient();
			
			//3、通过Tracker客户端可以获得一个Tracker服务器
			TrackerServer trackerServer = trackerClient.getConnection();
			
			//4、通过Tracker客户端可以获取一个Storage服务器
			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			
			//5、通过Tracker服务器和Storage服务器可以创建一个storage客户端
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			
			//从fastdfs文件系统删除文件
			rtn = storageClient.delete_file(group, filepath);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
