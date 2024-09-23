package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.diplom.common.model.Image;

public interface ImageRepo extends JpaRepository<Image, Integer> {
}
