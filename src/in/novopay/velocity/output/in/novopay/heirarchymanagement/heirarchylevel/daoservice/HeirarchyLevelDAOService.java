package in.novopay.heirarchymanagement.heirarchylevel.daoservice;


import in.novopay.heirarchymanagement.heirarchylevel.entity.HeirarchyLevelEntity;
import in.novopay.infra.platform.exception.NovopayFatalException;
import in.novopay.infra.platform.navigation.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static in.novopay.accounting.constants.NovopayAccountingCommonConstants.PAGE_SIZE;

/***
 * 
 * @author Manoj
 *
 */

@Service
public class HeirarchyLevelDAOService {

	@Autowired
	private HeirarchyLevelRepository heirarchyLevelRepository;
		
	@Autowired
	private HeirarchyLevelRowMapper heirarchyLevelMapper;

	public HeirarchyLevelEntity saveHeirarchyLevel(HeirarchyLevelEntity templateEntity) {
		return heirarchyLevelRepository.save(templateEntity);
	}

	public List<HeirarchyLevelEntity> getHeirarchyLevelList() {
		return heirarchyLevelRepository.findAll();
	}
	
	public List<Map<String, Object>> getHeirarchyLevelPaginatedList(ExecutionContext executionContext) throws NovopayFatalException {
		List<Map<String, Object>> heirarchyLevelList = heirarchyLevelMapper.getDetails(executionContext);
		executionContext.put(PAGE_SIZE, heirarchyLevelList.size());
		return heirarchyLevelList;
	}

	public Long getHeirarchyLevelCount(ExecutionContext executionContext){
		return heirarchyLevelMapper.getCount(executionContext);
	}
	
	public HeirarchyLevelEntity getHeirarchyLevelDetails(Integer id){
		return heirarchyLevelRepository.findOneById(id);
	}
	
	
}
