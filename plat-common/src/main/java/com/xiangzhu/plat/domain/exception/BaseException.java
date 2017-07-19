
package com.xiangzhu.plat.domain.exception;

import com.xiangzhu.plat.domain.enums.HttpCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

/**
 * 
 * @author ShenHuaJie
 * @version 2016年6月7日 下午8:43:02
 */
@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {
	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}

	public void handler(ModelMap modelMap) {
		modelMap.put("httpCode", getHttpCode().value());
		if (StringUtils.isNotBlank(getMessage())) {
			modelMap.put("msg", getMessage());
		} else {
			modelMap.put("msg", getHttpCode().msg());
		}
		modelMap.put("timestamp", System.currentTimeMillis());
	}

	protected abstract HttpCode getHttpCode();
}
