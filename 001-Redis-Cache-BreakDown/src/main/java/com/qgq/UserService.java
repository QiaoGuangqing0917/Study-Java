package com.qgq;

import com.qgq.common.dao.UsersDao;
import com.qgq.common.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : UserService
 * @Author : QiaoGuangqing
 * @Date : 2020-06-10
 * @Description :
 */
@Service
public class UserService {

	@Autowired
	UsersDao usersDao;
	@Autowired
	RedisTemplate redisTemplate;
	//------------------------------------Version 1.0------------------------------------
//	public UserEntity getById(long userId){
//
//		Object userObj = redisTemplate.opsForValue().get("user-"+userId);
//		if(userObj == null){
//			UserEntity userEntity = usersDao.getById(userId);
//			if(userEntity == null){
//				userEntity = new UserEntity();
//			}
//			userObj = userEntity;
//			redisTemplate.opsForValue().set("user-"+userId, userEntity, 5000, TimeUnit.MILLISECONDS);
//		}
//
//		return (UserEntity) userObj;
//	}
	//------------------------------------Version 2.0------------------------------------
//	public UserEntity getById(long userId){
//
//		Object userObj = redisTemplate.opsForValue().get("user-"+userId);
//		if(userObj == null){
//			//线程1 线程2
//			synchronized (this){
//				userObj = redisTemplate.opsForValue().get("user-"+userId);
//				if(userObj == null){
//					UserEntity userEntity = usersDao.getById(userId);
//					if(userEntity == null){
//						userEntity = new UserEntity();
//					}
//					userObj = userEntity;
//					redisTemplate.opsForValue().set("user-"+userId, userEntity, 5000, TimeUnit.MILLISECONDS);
//				}
//			}
//		}
//
//		return (UserEntity) userObj;
//	}
	//------------------------------------Version 3.0------------------------------------
//	static Map<Long, ReentrantLock> lockMap = new ConcurrentHashMap<>();
//	public UserEntity getById(long userId){
//		Object userObj = redisTemplate.opsForValue().get("user-"+userId);
//
//		if(userObj == null){
//			ReentrantLock lock = lockMap.get(userId);
//			if(lock == null){
//				lockMap.putIfAbsent(userId, new ReentrantLock());
//			}
//			lock = lockMap.get(userId);
//
//			try{
//				if(lock.tryLock()){
//					userObj = usersDao.getById(userId); //20ms
//					if(userObj == null){
//						userObj = new UserEntity();
//					}
//					redisTemplate.opsForValue().set("user-"+userId, userObj, 5000, TimeUnit.MILLISECONDS);
//				}else{
//					//没抢到锁
//					try {
//						Thread.sleep(500);
//						userObj = getById(userId);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			} finally {
//				lockMap.remove(userId);
//				lock.unlock();
//			}
//		}
//
//		return (UserEntity) userObj;
//	}

	//------------------------------------Version 4.0------------------------------------
	static Map<Long, Semaphore> lockMap = new ConcurrentHashMap<>();
	public UserEntity getById(long userId){
		Object userObj = redisTemplate.opsForValue().get("user-"+userId);

		if(userObj == null){
			Semaphore lock = lockMap.get(userId);
			if(lock == null){
				lockMap.putIfAbsent(userId, new Semaphore(1));
			}
			lock = lockMap.get(userId);

			try {
				//99个线程
				lock.acquire();
				//1个线程

				userObj = redisTemplate.opsForValue().get("user-"+userId);
				if(userObj == null){
					userObj = usersDao.getById(userId);
					if(userObj == null){
						userObj = new UserEntity();
					}
					redisTemplate.opsForValue().set("user-"+userId, userObj, 5000, TimeUnit.MILLISECONDS);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lockMap.remove(userId);
				lock.release();
			}

		}

		return (UserEntity) userObj;
	}
}
