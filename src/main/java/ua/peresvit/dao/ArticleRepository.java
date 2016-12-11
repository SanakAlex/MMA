package ua.peresvit.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.entity.Article;
import ua.peresvit.entity.ResourceGroupType;
import ua.peresvit.entity.Role;

import java.util.Collection;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    <S extends Article> S save(S arg0);

    Article findOne(Long arg0);

    java.util.List<Article> findAll();

    void delete(Article arg0);

    boolean equals(Object obj);

    Article findByArticleId(String resourceGroupId);

    Collection<Article> findAllByResourceGroupTypeAndRang(ResourceGroupType type, Role role);

    Collection<Article> findAllByChapterIdAndResourceGroupTypeAndRang(long chapterId, ResourceGroupType type, Role role);

    Article findByChapterId(long chapterId);
}