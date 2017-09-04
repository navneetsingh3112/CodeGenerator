package in.novopay.accounting.internalaccount.daoservice;


import in.novopay.accounting.internalaccount.entity.InternalAccountTemplateEntity;
import in.novopay.infra.platform.exception.NovopayFatalException;
import in.novopay.infra.platform.navigation.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static in.novopay.accounting.constants.NovopayAccountingCommonConstants.PAGE_SIZE;

@Service
public class InternalAccountTemplateDAOService {

	@Autowired
	private InternalAccountTemplateRepository internalAccountTemplateRepository;
		
	@Autowired
	private InternalAccountTemplateRowMapper internalAccountTemplateMapper;

	public List<InternalAccountTemplateEntity> getAllInternalAccountTemplate() {
		return internalAccountTemplateRepository.findAll();
	}
	
	public List<Map<String, Object>> getAllInternalAccountTemplate(ExecutionContext executionContext) throws NovopayFatalException {
		List<Map<String, Object>> internalAccountTemplateList = internalAccountTemplateMapper.getDetails(executionContext);
		executionContext.put(PAGE_SIZE, internalAccountTemplateList.size());
		return internalAccountTemplateList;
	}

	public InternalAccountTemplateEntity saveInternalAccountTemplate(InternalAccountTemplateEntity templateEntity) {
		return internalAccountTemplateRepository.save(templateEntity);
	}
	
	public InternalAccountTemplateEntity getOneWithId(Integer templateId){
		return internalAccountTemplateRepository.findOneById(templateId);
	}
	
	
	public Long getInternalAccountTemplateCount(ExecutionContext executionContext){
		return internalAccountTemplateMapper.getCount(executionContext);
	}
}
