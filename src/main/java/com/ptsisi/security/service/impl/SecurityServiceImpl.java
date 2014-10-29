package com.ptsisi.security.service.impl;

import com.google.common.collect.Lists;
import com.ptsisi.daily.Resource;
import com.ptsisi.security.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhaoding on 14-10-28.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

	@Override public List<Resource> getResources() {
		return Lists.newArrayList();
	}
}
