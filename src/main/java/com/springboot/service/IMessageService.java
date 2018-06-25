package com.springboot.service;

import com.springboot.model.Message;

public interface IMessageService {

	Message selectByPrimaryKey(Integer id);

	void insertMessage();

	void insert();
}
