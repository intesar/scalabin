package com.vmw.bora.vchest.search;

import org.springframework.data.domain.Sort;


public class SearchUtil {

	public static Sort sortByBucketName() {
        return new Sort(Sort.Direction.ASC, "bucketName");
    }
	
}
