package com.jhuniversity.crm.settings.service;

import com.jhuniversity.crm.settings.domian.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String,List<DicValue>> getAll();
}
