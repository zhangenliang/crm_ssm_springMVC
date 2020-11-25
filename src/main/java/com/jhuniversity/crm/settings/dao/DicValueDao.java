package com.jhuniversity.crm.settings.dao;

import com.jhuniversity.crm.settings.domian.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getValueList(String code);
}
