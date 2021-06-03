package com.finwin.cristal.custmate.home.mini_statement.action;

import com.finwin.cristal.custmate.home.mini_statement.pojo.MiniStatementResponse;

public class MiniStatementAction {
    public static final int DEFAULT=-1;
    public static final int MINI_STATEMENT_SUCCESS=1;
    public static final int API_ERROR=2;
    public int action;
    public String error;
    public MiniStatementResponse miniStatementResponse;

    public MiniStatementAction(int action, MiniStatementResponse miniStatementResponse) {
        this.action = action;
        this.miniStatementResponse = miniStatementResponse;
    }

    public MiniStatementAction(int action) {
        this.action = action;
    }

    public MiniStatementAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public MiniStatementResponse getMiniStatementResponse() {
        return miniStatementResponse;
    }

    public void setMiniStatementResponse(MiniStatementResponse miniStatementResponse) {
        this.miniStatementResponse = miniStatementResponse;
    }
}
