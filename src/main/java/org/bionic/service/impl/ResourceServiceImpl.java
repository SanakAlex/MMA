package org.bionic.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.bionic.config.Constant;
import org.bionic.dao.ResourceRepository;
import org.bionic.entity.EnumResourceType;
import org.bionic.entity.Resource;
import org.bionic.entity.ResourceType;
import org.bionic.service.ResourceService;
import org.bionic.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private ResourceTypeService resourceTypeService;
	
	@Autowired
    ServletContext context; 

	@Override
	public <S extends Resource> S save(S arg0) {
		return resourceRepository.save(arg0);
	}

	@Override
	public Resource findOne(Long arg0) {
		return resourceRepository.findOne(arg0);
	}

	@Override
	public java.util.List<Resource> findAll() {
		return resourceRepository.findAll();
	}

	@Override
	public void delete(Resource resource) {
		String pathFile = resource.getUrl();
		resourceRepository.delete(resource);
		Constant.deleteFile(pathFile);
	}

	@Override
	public boolean equals(Object obj) {
		return resourceRepository.equals(obj);
	}

	@Override
	public List<Resource> findByResourceGroupId(Long resourceId) {
		return resourceRepository.findByResourceId(resourceId);
	}

	@Override
	public Resource update(Resource resource, Long resourceId) {

		Resource updatedResource = resourceRepository.findOne(resourceId);

		// delete old picture
		String oldFile = updatedResource.getUrl();
		if (oldFile != null && !oldFile.equals(resource.getUrl()))
			Constant.deleteFile(oldFile);

		org.springframework.beans.BeanUtils.copyProperties(resource, updatedResource);
		return resourceRepository.save(updatedResource);

	}

	@Override
	public String saveFile(Resource resource, MultipartFile inputFile) {

		try {
			if (!inputFile.isEmpty()) {
				try {
					String originalFilename = inputFile.getOriginalFilename();

					String pathFile = "";
					String fileContentType = inputFile.getContentType();
					if (resource.getUser() != null)
						pathFile = Constant.UPLOAD_PATH + "/" + Constant.USR_FOLDER + resource.getUser().getUserId() + "/" + fileContentType;
					else
						pathFile = Constant.UPLOAD_PATH + "/" + Constant.USER_UNKNOWN + "/" + fileContentType;

					File destinationFile = new File(context.getRealPath(pathFile), originalFilename);
					destinationFile.getParentFile().mkdirs();
					inputFile.transferTo(destinationFile);

					// Split the mimeType into primary and sub types
					String primaryType, subType;
					try {
						primaryType = fileContentType.split("/")[0];
						subType = fileContentType.split("/")[1];
						resource.setResourceType(defineResourceTypeByMIME(primaryType, subType));
					} catch (IndexOutOfBoundsException | NullPointerException ex){ 						
						resource.setResourceType(resourceTypeService.findOne(EnumResourceType.OTHER.getValue()));
					}

					// delete old file
					if(resource.getResourceId() != null)
						Constant.deleteFile(resourceRepository.findOne(resource.getResourceId()).getUrl());
					
					// saving URL
					pathFile = destinationFile.getPath();
					// resource.setType(primaryType);
					
					return pathFile;
				} catch (Exception e) {
					return null;
				}

			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}
	
	public ResourceType defineResourceTypeByMIME(String primaryMimeType, String subType){
		
		if(primaryMimeType.equals("video"))
			return resourceTypeService.findOne(EnumResourceType.VIDEO.getValue()); 
		if(primaryMimeType.equals("text") || subType.contains("pdf") || subType.contains("word"))
			return resourceTypeService.findOne(EnumResourceType.TEXT.getValue());
		if(primaryMimeType.equals("image"))
			return resourceTypeService.findOne(EnumResourceType.IMAGE.getValue());
		
		return resourceTypeService.findOne(EnumResourceType.OTHER.getValue());
	}	
}
