package com.emicsto.users.user;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, Long> {
}
