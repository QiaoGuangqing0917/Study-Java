package com.qgq.common.dao;

import com.qgq.common.entity.UserEntity;

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
