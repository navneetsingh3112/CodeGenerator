package in.novopay.heirarchymanagement.heirarchylevel.repository;


import in.novopay.heirarchymanagement.heirarchylevel.entity.HeirarchyLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * 
 * @author Manoj
 *
 */

interface HeirarchyLevelRepository extends JpaRepository<HeirarchyLevelEntity, Integer> {

	List<HeirarchyLevelEntity> findAll();
	HeirarchyLevelEntity findOneById(Integer id);
	
}
