package com.finwin.cristal.custmate.home.mini_statement.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MiniStatementResponse {

@SerializedName("mini_statement")
@Expose
private MiniStatement miniStatement;

public MiniStatement getMiniStatement() {
return miniStatement;
}

public void setMiniStatement(MiniStatement miniStatement) {
this.miniStatement = miniStatement;
}

}