package com.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mapper.MessageMapper;
import com.springboot.model.Message;
import com.springboot.service.IMessageService;

@Service(value = "messageService")
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public Message selectByPrimaryKey(Integer id) {
		return messageMapper.selectByPrimaryKey(id);
	}

}
