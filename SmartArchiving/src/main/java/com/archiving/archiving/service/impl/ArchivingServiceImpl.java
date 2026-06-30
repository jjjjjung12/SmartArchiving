package com.archiving.archiving.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archiving.archiving.dao.mapper.ArchivingMapper;
import com.archiving.archiving.service.ArchivingService;

@Service("archivingService")
public class ArchivingServiceImpl implements ArchivingService {

    @Resource(name = "archivingMapper")
    private ArchivingMapper archivingMapper;
}
