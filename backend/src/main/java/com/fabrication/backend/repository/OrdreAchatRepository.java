package com.fabrication.backend.repository;

import com.fabrication.backend.entity.OrdreAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdreAchatRepository extends JpaRepository<OrdreAchat, Long> {
}
