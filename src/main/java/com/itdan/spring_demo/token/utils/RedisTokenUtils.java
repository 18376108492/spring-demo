/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.itdan.spring_demo.token.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RedisTokenUtils {
	private long timeout = 60 * 60;
	@Autowired
	private BaseRedisService baseRedisService;

	// 将token存入在redis
	public String getToken() {
		String token = "token" + System.currentTimeMillis();
		baseRedisService.setString(token, token, timeout);
		return token;
	}

	public boolean findToken(String tokenKey) {
		String token = (String) baseRedisService.getString(tokenKey);
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		// token 获取成功后 删除对应tokenMapstoken
		baseRedisService.delKey(token);
		return true;
	}

}
