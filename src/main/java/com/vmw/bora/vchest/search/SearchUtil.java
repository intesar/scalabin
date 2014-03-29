package com.vmw.bora.vchest.search;

import org.springframework.data.domain.Sort;


public class SearchUtil {

	public static Sort sortByBucketName() {
        return new Sort(Sort.Direction.ASC, "name");
    }
	
	public static Sort sortByYearMonth() {
        return new Sort(Sort.Direction.DESC, "year", "month");
    }
}
