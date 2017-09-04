package in.novopay.accounting.internalaccount.repository;


import in.novopay.accounting.internalaccount.entity.InternalAccountTemplateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface InternalAccountTemplateRepository extends CrudRepository<InternalAccountTemplateEntity, Integer> {

	List<InternalAccountTemplateEntity> findAll();
	InternalAccountTemplateEntity findOneById(Integer id);
	
}
