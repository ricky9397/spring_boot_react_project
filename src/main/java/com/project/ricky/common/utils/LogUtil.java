package com.project.ricky.common.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Data
public class LogUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());



}
