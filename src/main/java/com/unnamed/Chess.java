package com.unnamed;

import com.unnamed.engine.Board.Board;
import com.unnamed.gui.Table;

public class Chess {

    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table table = new Table();
    }
}
