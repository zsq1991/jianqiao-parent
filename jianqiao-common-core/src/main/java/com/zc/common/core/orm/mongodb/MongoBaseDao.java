package com.zc.common.core.orm.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * mongoDB数据库的dao类
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月19日 下午10:20:02
 * 
 */
public interface MongoBaseDao<T> extends MongoRepository<T, ObjectId> {

}
