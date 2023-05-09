package project7.clonecoding.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project7.clonecoding.game.entity.Images;

public interface ImageRepository extends JpaRepository<Images, Long> {
}
