package com.archiving.bulkjob.dao.sqream;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.bulkjob.dto.ServiceInfoRowDto;

/**
 * EFBL/DFSL 등(mhdb 제외)은 애플리케이션 기본 PG가 아닌 {@code sqreamDataSource} / Sqream MyBatis로만 접근한다.
 */
public interface ServiceInfoSqreamMapper {

	List<ServiceInfoRowDto> selectEfbl(@Param("applicationGroupId") String applicationGroupId);

	List<ServiceInfoRowDto> selectDfsl(@Param("applicationGroupId") String applicationGroupId);

	int countEfbl(@Param("applicationGroupId") String applicationGroupId, @Param("serviceId") String serviceId);

	int countDfsl(@Param("applicationGroupId") String applicationGroupId, @Param("serviceId") String serviceId);

	int updateEfbl(@Param("applicationGroupId") String applicationGroupId, @Param("serviceId") String serviceId,
			@Param("selGubun") String selGubun, @Param("delYn") String delYn, @Param("upDtime") String upDtime);

	int updateDfsl(@Param("applicationGroupId") String applicationGroupId, @Param("serviceId") String serviceId,
			@Param("selGubun") String selGubun, @Param("delYn") String delYn, @Param("upDtime") String upDtime);

	int insertEfbl(@Param("applicationGroupId") String applicationGroupId, @Param("serviceId") String serviceId,
			@Param("selGubun") String selGubun, @Param("delYn") String delYn, @Param("upDtime") String upDtime);

	int insertDfsl(@Param("applicationGroupId") String applicationGroupId, @Param("serviceId") String serviceId,
			@Param("selGubun") String selGubun, @Param("delYn") String delYn, @Param("upDtime") String upDtime);

	int truncateEfbl();

	int truncateDfsl();

	int insertManyEfbl(@Param("rows") List<ServiceInfoRowDto> rows, @Param("upDtime") String upDtime);

	int insertManyDfsl(@Param("rows") List<ServiceInfoRowDto> rows, @Param("upDtime") String upDtime);
}
