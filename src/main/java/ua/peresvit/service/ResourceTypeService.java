package ua.peresvit.service;


import ua.peresvit.entity.ResourceType;

import java.util.List;

public interface ResourceTypeService {
	<S extends ResourceType> S save(S arg0);

	ResourceType findOne(Long arg0);

	List<ResourceType> findAll();

	void delete(ResourceType arg0);

	boolean equals(Object obj);

	List<ResourceType> findByResourceTypeId(Long resourceTypeId);
}
