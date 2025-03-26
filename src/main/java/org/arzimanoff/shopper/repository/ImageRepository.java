package org.arzimanoff.shopper.repository;

import org.arzimanoff.shopper.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
