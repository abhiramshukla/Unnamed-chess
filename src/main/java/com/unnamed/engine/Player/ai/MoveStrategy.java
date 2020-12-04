package com.unnamed.engine.Player.ai;

import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.Move;

public interface MoveStrategy {

    Move execute(Board board);
}
