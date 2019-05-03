package com.leyou.user.service;

import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:phone:";

    /**
     * 用户数据校验
     */
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        //判断数据类型
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnums.INVALID_USER_DATA_TYPE);
        }
        record.setUsername(data);
        return userMapper.selectCount(record) == 0;
    }

    /**
     * 发送短信验证码
     */
    public void sendCode(String phone) {
        //生成key（用于保存验证码到redis）
        String key = KEY_PREFIX + phone;
        //生成验证码 6位随机数
        String code = NumberUtils.generateCode(6);

        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);

        //发送验证码
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);//应抽取到配置文件

        //保存验证码
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);//时长应抽取到配置文件

    }

    /**
     * 实现用户注册
     */
    public void register(User user, String code) {
        //从redis取出验证码
        String cacheCode = redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        //校验验证码
        if (!StringUtils.equals(code, cacheCode)){
            throw new LyException(ExceptionEnums.INVALD_VERIFY_CODE);
        }
        //对密码进行加密 加盐并MD5加密多次
//        DigestUtils.md5Hex("214312412" + DigestUtils.md5Hex(user.getPassword()) + "jfileawjilfewa");
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        //写入数据库
        user.setCreated(new Date());
        userMapper.insert(user);
    }
}
