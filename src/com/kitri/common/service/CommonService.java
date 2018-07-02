package com.kitri.common.service;

import com.kitri.util.PageNavigation;

public interface CommonService {

	PageNavigation getPageNavigation(int bcode, int pg, String key, String word);
	
}
