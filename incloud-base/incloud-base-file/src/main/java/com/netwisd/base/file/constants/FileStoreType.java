package com.netwisd.base.file.constants;

import java.util.Arrays;

/**
 * 文件存储方式
 */
public enum FileStoreType {
	/**
	 * 默认的
	 */
	DEFAULT,
	/**
	 * 本地
	 */
	LOCAL,
	/**
	 * 阿里云
	 */
	ALIYUN,
	/**
	 * minio
	 */
	MINIO;

	public static String getByKey(String key){
		return Arrays.stream(FileStoreType.values()).filter(fileStoreType -> fileStoreType.name().equals(key)).findFirst().get().name();
	}
}
