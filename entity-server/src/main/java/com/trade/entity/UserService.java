package com.trade.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserService {
	void save(User user);

	void saveAll(List<? extends User> users);

	void delete(UUID userId);

	User getById(UUID userId);

	User getByLoginName(String loginName);

	public Collection<User> getAll();
}
