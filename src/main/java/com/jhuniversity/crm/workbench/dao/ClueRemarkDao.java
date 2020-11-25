package com.jhuniversity.crm.workbench.dao;

import com.jhuniversity.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ClueRemark> showRemarkList(String clueId);

    int deleteRemarkById(String id);


    int updateRemark(ClueRemark cr);

    int saveRemark(ClueRemark cr);
}
