package com.trade.entity;

import java.util.Collection;
import java.util.UUID;

public interface UserService {
	public void save(User user);

	public void delete(UUID userId);

	public User getById(UUID userId);

	public Collection<User> getAll();
}
