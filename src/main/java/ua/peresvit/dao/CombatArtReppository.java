package ua.peresvit.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.entity.CombatArt;

public interface CombatArtReppository extends JpaRepository<CombatArt, Long> {

    <S extends CombatArt> S save(S arg0);

    CombatArt findOne(Long arg0);

    java.util.List<CombatArt> findAll();
}
