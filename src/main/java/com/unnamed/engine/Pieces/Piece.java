package com.unnamed.engine.Pieces;

import com.unnamed.engine.Alliance;
import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;

    protected final Alliance pieceAlliance;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

}
