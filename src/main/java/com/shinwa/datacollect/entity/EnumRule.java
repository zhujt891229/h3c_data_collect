package com.shinwa.datacollect.entity;

public enum EnumRule {
    ALPHABET_DIGIT("字母加数字","^[A-Za-z0-9]+$"),
    DIGIT_21("长度不超21位的数字","^\\d{1,21}"),
    ;
    private String ruleName;
    private String ruleContent;

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

    EnumRule(String ruleName, String ruleContent){
        this.ruleName = ruleName;
        this.ruleContent = ruleContent;
    }
}
