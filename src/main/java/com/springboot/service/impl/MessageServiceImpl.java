package com.springboot.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mapper.MessageMapper;
import com.springboot.model.Message;
import com.springboot.service.IMessageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service(value = "messageService")
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public Message selectByPrimaryKey(Integer id) {
		return messageMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertMessage() {
		Message message = new Message();
		message.setIp("adfasd");
		message.setNickName("hb");
		message.setInsertDate(new Date());
		messageMapper.insert(message);
		int i = 1 / 0;
	}

	@Override
	public void insert() {
		IMessageService service = (IMessageService)AopContext.currentProxy();
		service.insertMessage();
	}
}
