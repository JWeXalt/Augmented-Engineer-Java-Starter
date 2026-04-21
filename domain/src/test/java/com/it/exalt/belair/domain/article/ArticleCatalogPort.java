package com.it.exalt.belair.domain.article;

import java.util.Optional;

public interface ArticleCatalogPort {

    Optional<Article> findById(String id);
}
