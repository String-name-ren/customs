package com.leader.ren.component.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.leader.ren.common.utils.CommUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "oss", name = "enable", havingValue = "true")
public class OSSComponent {
	@Value("${oss.url}")
	private String url;

	@Value("${oss.key}")
	private String key;

	@Value("${oss.secret}")
	private String secret;

	@Value("${oss.bucket}")
	private String bucket;

	@Value("${oss.endpoint}")
	private String endpoint;

	@Value("${oss.img.catalog}")
	private String catalog;

	@Value("${oss.file.catalog}")
	private String fileCatalog;

	/**
	 * 获取阿里云OSS客户端对象
	 *
	 * @return
	 */
	@Bean
	public OSSClient ossClient(){
		return new OSSClient(url, key, secret);
	}

	/**
	 * 向阿里云的OSS存储中上传文件
	 *
	 * @param filePath
	 * @param saveName
	 * @return
	 */
	public boolean upload(String filePath, String saveName){
		try{
			// 创建OSSClient实例
			OSSClient client = ossClient();

			//将图片路径转化为文件
			File file = new File(filePath);

			//将文件传给输入流
			InputStream ins = new FileInputStream(file);

			//创建上传Object的Metadata
			 client.putObject(bucket, saveName, ins);

			ins.close();

			// 关闭client
			client.shutdown();
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}

		return true;
	}

    /**
     * 向阿里云的OSS存储中上传文件
     *
     * @param ins
     * @param saveName
     * @return
     */
    public boolean upload(InputStream ins, String saveName){
        try{
            // 创建OSSClient实例
            OSSClient client = ossClient();

            //创建上传Object的Metadata
            client.putObject(bucket, saveName, ins);

            ins.close();

            // 关闭client
            client.shutdown();
        } catch (Exception e) {
			log.info(e.getMessage());
			return false;
        }

        return true;
    }

	/**
	 * 向阿里云的OSS存储中上传Base64文件
	 *
	 * @param base64Data
	 * @param saveName
	 * @return
	 */
	public boolean uploadBase64(String base64Data, String saveName){
		try{
			// 创建OSSClient实例
			OSSClient client = ossClient();

			// 图片数据解码
			byte[] asBytes = Base64.getDecoder().decode(base64Data.getBytes());

			//创建上传Object的Metadata
			 client.putObject(bucket, saveName, new ByteArrayInputStream(asBytes));

			// 关闭client
			client.shutdown();
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 阿里云的OSS存储中文件访问路径，图片访问使用
	 *
	 * @param savePath
	 * @return
	 */
	public String fmtAccessUrl(String savePath){

		if(CommUtils.isNull(savePath)){
			return "";
		}
		return endpoint + catalog + savePath;

	}

	/**
	 * 阿里云的OSS存储中文件访问路径，文件访问使用
	 *
	 * @param savePath
	 * @return
	 */
	public String fmtAccessFileUrl(String savePath){

		if(CommUtils.isNull(savePath)){
			return "";
		}
		return endpoint + fileCatalog + savePath;


	}

	/**
	 * 阿里云的OSS存储中文件访问路径，文件访问使用
	 *
	 * @param savePath
	 * @return
	 */
	public String fmtAccessFileUrl(String savePath, String filePath){

		if(CommUtils.isNull(savePath)){
			return "";
		}
		return endpoint + filePath + savePath;

	}

	/**
	 * 从阿里云的OSS存储中下载文件
	 *
	 * @param filePath
	 * @param saveName
	 * @return
	 */
	public boolean download(String filePath, String saveName){
		try{
			// 创建OSSClient实例
			OSSClient client = ossClient();

			// 将图片路径转化为文件
			File file = new File(filePath);

			// 下载object到文件
			client.getObject(new GetObjectRequest(bucket, saveName), file);

			// 关闭client
			client.shutdown();
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 从阿里云的OSS存储中删除文件
	 *
	 * @param saveName
	 * @return
	 */
	public boolean delete(String saveName){
		try{
			// 创建OSSClient实例
			OSSClient client = ossClient();

			// 下载object到文件
			client.deleteObject(bucket, saveName);

			// 关闭client
			client.shutdown();
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 判断文件是否存在
	 * @param saveName
	 * @return
	 */
	public boolean isExist(String saveName) {
		boolean found = false;
		try{
			// 创建OSSClient实例
			OSSClient client = ossClient();

			// 判断文件是否存在
			found = client.doesObjectExist(bucket, saveName);

			// 关闭client
			client.shutdown();
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return found;
	}
}
