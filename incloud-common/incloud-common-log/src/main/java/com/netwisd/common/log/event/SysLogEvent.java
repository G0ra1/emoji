package com.netwisd.common.log.event;

import com.netwisd.common.log.entity.SystemLog;
import org.springframework.context.ApplicationEvent;

public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SystemLog source) {
		super(source);
	}

}
