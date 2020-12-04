package com.unnamed.engine.Player.ai;

import com.unnamed.engine.Board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);
}
