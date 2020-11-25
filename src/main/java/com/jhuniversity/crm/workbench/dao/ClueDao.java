package com.jhuniversity.crm.workbench.dao;

import com.jhuniversity.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    int getTotal(Map<String,Object> map);

    List<Clue> getClue(Map<String,Object> map);

    int delete(String[] ids);

    Clue getById(String id);

    int update(Clue clue);

    Clue detail(String id);
}
