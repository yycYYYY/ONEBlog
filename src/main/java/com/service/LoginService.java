package com.service;

import com.entity.Users;
import com.entity.UsersExample;
import com.mapper.UsersMapper;
import com.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.SaslException;

@Service
public class LoginService {
	@Autowired
	private UsersMapper usersMapper;

	public boolean validate(String username, String password) throws SaslException {
		Users user;
		UsersExample example  = new UsersExample();
		example.createCriteria().andUsernameEqualTo(username);

		if(usersMapper.selectByExample(example) != null) {
			user = usersMapper.selectByExample(example).get(0);
		} else {
			// 用户不存在，登陆失败！
			return false;
		}

		// 获取数据库的密码
		String passwordDB = user.getPassword();
		// password md5 加密
		String passmd5 = MD5.getMD5(password);
		System.out.println(passmd5);
        return passwordDB.equals(passmd5);
		// 密码错误！
    }

    public boolean newUser(String username ,String password) throws SaslException {
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(MD5.getMD5(password));
		try{
            usersMapper.insert(user);
        }catch (Exception e){
		    return false;
        }
	    return true;
	}
}
