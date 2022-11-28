package com.netwisd.base.file.constants;

import java.util.Arrays;

/**
 * 文件存储方式
 */
public enum FileStoreTypeEnum {
	/**
	 * 默认的
	 */
	DEFAULT,
	/**
	 * 本地
	 */
	LOCAL,
	/**
	 * minio
	 */
	MINIO;
	public static String getByKey(String key){
		return Arrays.stream(FileStoreTypeEnum.values()).filter(fileStoreType -> fileStoreType.name().equals(key)).findFirst().get().name();
	}
}
