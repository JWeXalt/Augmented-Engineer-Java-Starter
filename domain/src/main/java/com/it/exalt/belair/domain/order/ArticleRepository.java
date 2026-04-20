package com.it.exalt.belair.domain.order;

public interface ArticleRepository {
    boolean isAvailable(String articleId);
}
