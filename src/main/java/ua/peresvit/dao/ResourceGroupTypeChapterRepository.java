package ua.peresvit.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.entity.ResourceGroupType;
import ua.peresvit.entity.ResourceGroupTypeChapter;

import java.util.List;

public interface ResourceGroupTypeChapterRepository extends JpaRepository<ResourceGroupTypeChapter, Long> {
    <S extends ResourceGroupTypeChapter> S save(S arg0);

    ResourceGroupTypeChapter findOne(Long arg0);

    List<ResourceGroupTypeChapter> findAll();

    void delete(ResourceGroupTypeChapter arg0);

    boolean equals(Object obj);

    List<ResourceGroupTypeChapter> findAllByResourceGroupType(ResourceGroupType id);
}