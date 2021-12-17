package com.gqzdev.beans.importselector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author gqzdev
 * @date 2021/07/10 11:31
 **/
public class DataImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{"com.gqzdev.async.service.User", "com.gqzdev.async.service.Role"};
	}
}