package com.deniszagorsky.socialnetwork.repository;

import com.deniszagorsky.socialnetwork.domain.basic.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с отношением "дружба" между пользователями
 */

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, UUID>, JpaSpecificationExecutor<Friendship> { }
