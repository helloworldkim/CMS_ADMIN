package com.example.cms.system.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Message Util
 */
@Component
@RequiredArgsConstructor
public class MessageUtil {


	private final MessageSource messageSource;


	/**
	 * 현재 locale 정보를 반환한다.
	 *
	 * @return
	 */
	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}


	/**
	 * 메시지를 반환한다.
	 *
	 * @param	messageKey 메시지 키
	 * @return
	 */
	public String getMessage(String messageKey) {
		return messageSource.getMessage(messageKey, null, getLocale());
	}


	/**
	 * 메시지를 반환한다.
	 *
	 * @param messageKey 메시지 키
	 * @return
	 */
	public String getMessage(String messageKey, Object[] args) {
		return messageSource.getMessage(messageKey, args, getLocale());
	}

}
