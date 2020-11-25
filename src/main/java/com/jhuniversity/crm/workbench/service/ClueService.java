package com.jhuniversity.crm.workbench.service;

import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.Clue;
import com.jhuniversity.crm.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUser();

    boolean save(Clue clue);

    Vo<Clue> pageClueList(Map<String,Object> map);

    boolean delete(String[] ids);

    Map<String,Object> getUserListAndClue(String id);

    boolean update(Clue clue);

    Clue detail(String id);

    List<ClueRemark> showRemarkList(String clueId);

    boolean deleteRemarkById(String id);


    boolean updateRemark(ClueRemark cr);

    boolean saveRemark(ClueRemark cr);


    boolean unbund(String id);

    List<Activity> showActivityRelation();

    boolean bund(String cid, String[] aids);
}
