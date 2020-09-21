package com.bnuz.aed.common.repository;

import com.bnuz.aed.model.AedPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Leia Liang
 */
@Repository
public interface AedPositionRepository extends JpaRepository<AedPosition, Long> {
}
