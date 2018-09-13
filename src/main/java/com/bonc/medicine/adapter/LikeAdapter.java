package com.bonc.medicine.adapter;

import com.bonc.medicine.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @ClassName: LikeAdapter 
 * @Description: TODO
 * @author: hejiajun
 * @date: 2018年8月2日 上午10:49:00  
*/

@Service
public class LikeAdapter {

	@Autowired
	JedisAdapter jedisAdapter;

/*
	 * 判断是点赞还是点反对
	 * 
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
*/

	public int getLikeStatus(int userId, String entityType, int entityId) {
		// 根据当前用户的userid分别生成一个likeKey 和
		// disLikeKey,再分别判断这两个值是否在对应的Like集合中和disLikeKey集合中
		
		// 比如如果在likeKey集合中，就返回一个1，否则返回-1
		String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		
		// 判断值为userId 的用户是否在key为listKey 的集合中
		if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
	}

/*
	 * 点赞：即当前用户点赞后，被点赞用户的like集合中就会加上一个该点赞的用户信息
	 * 
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 * */


	public long like(int userId, String entityType, int entityId) {
		// 在当前news上点赞后获取key: LIKE:ENTITY_NEWS:2
		String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		// 在喜欢集合中添加当前操作用户的userId(即当前用户点赞后，被点赞用户的like集合中就会加上一个点赞的用户信息)
		jedisAdapter.sadd(likeKey, String.valueOf(userId));

		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		jedisAdapter.srem(disLikeKey, String.valueOf(userId));

		// 返回点赞数量
		return jedisAdapter.scard(likeKey);
	}

/*
	 * 反对 ：即当前用户点反对后，被点反对用户的like集合中就会加上一个该点反对的用户信息
	 * 
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
*/

	public long disLike(int userId, String entityType, int entityId) {

		// 谁点击反对，谁就出现在key为dislikeKey的Set集合中
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

		// 从赞中删除
		String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		jedisAdapter.srem(likeKey, String.valueOf(userId));

		return jedisAdapter.scard(likeKey);
	}
}
