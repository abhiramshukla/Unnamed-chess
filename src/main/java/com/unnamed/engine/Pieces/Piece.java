package com.unnamed.engine.Pieces;

import com.unnamed.engine.Alliance;
import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;

    protected final Alliance pieceAlliance;

    protected final boolean isFirstMove;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //TODO first move needs to be fleshed out
        this.isFirstMove = false;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

}
