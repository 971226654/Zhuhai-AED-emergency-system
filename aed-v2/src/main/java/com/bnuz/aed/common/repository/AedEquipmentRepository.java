package com.bnuz.aed.common.repository;

import com.bnuz.aed.model.AedEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Leia Liang
 */
@Repository
public interface AedEquipmentRepository extends JpaRepository<AedEquipment, Long> {

    @Query(nativeQuery = true, value = "select * from aed_equipment e, aed_position p, aed_situation s where e.equipment_id = p.equipment_id and e.equipment_id = s.equipment_id")
    List<Object> findAllEquipments();

    @Query(nativeQuery = true,value = "select id from aed_equipment e where e.purchase_time=?1 " +
            "and e.facory_name=?2 and e.production_time=?3")
    Long findIdByTimes(String purchaseTime, String factoryName, String productionTime);

    @Query(nativeQuery = true, value = "select * from aed_equipment e, aed_position p, aed_situation s where " +
            "e.equipment_id = p.equipment_id and e.equipment_id = s.equipment_id " + "id=?1")
    Object findEquipmentById(Long id);

}
