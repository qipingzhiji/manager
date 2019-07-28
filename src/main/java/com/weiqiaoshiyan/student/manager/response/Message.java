package com.weiqiaoshiyan.student.manager.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhang_htao on 2019/7/28.
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {
    private String status;
    private Object message;
}
