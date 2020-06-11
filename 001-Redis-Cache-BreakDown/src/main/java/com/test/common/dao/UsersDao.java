package com.test.common.dao;

import com.test.common.entity.UserEntity;

import java.util.Map;

/**
 * @InterfaceName : UsersDao
 * @Author : QiaoGuangqing
 * @Date : 2020-06-08
 * @Description :
 */
public interface UsersDao {

	int insert(UserEntity userEntity);

	UserEntity getById(long userId);
}
