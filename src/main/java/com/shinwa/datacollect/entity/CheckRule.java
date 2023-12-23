package com.shinwa.datacollect.entity;

public class CheckRule {
    private int id;
    private String ruleName;
    private String ruleContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    @Override
    public String toString() {
        return ruleName;
    }
}
