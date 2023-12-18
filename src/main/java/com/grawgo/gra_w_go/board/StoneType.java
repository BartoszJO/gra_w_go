package com.grawgo.gra_w_go.board;

public enum StoneType {
    WHITE,
    BLACK;
    public static StoneType valueOf(int ind){
        return StoneType.values()[ind];
    }
}
