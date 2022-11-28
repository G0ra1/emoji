package com.netwisd.base.common.constants;

import java.io.Serializable;

public interface IEnum<T extends Serializable> {

    T getCode();

    String getMessage();
}
