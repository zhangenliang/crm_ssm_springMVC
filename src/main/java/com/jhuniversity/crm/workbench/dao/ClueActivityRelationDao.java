package com.jhuniversity.crm.workbench.dao;

import com.jhuniversity.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);
}
