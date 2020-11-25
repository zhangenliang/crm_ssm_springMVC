package com.jhuniversity.crm.vo;

import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.Clue;

import java.util.List;

public class Vo<T> {
    private int total;
    private List<Activity>activities;
    private List<Clue>clues;

    public List<Clue> getClues() {
        return clues;
    }

    public void setClues(List<Clue> clues) {
        this.clues = clues;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Vo{" +
                "total=" + total +
                ", activities=" + activities +
                ", clues=" + clues +
                '}';
    }
}
