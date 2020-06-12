package com.qgq.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName : UserEntity
 * @Author : QiaoGuangqing
 * @Date : 2020-06-08
 * @Description :
 */
@ToString
@Getter
@Setter
public class UserEntity implements Serializable {

	private Long userId;
	private String userName;
	private String userPwd;
}
