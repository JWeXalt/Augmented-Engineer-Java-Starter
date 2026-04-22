package com.it.exalt.belair.infrastructure.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, String> {
}
