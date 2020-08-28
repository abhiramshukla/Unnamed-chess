package com.unnamed.engine.Board;

import com.unnamed.engine.Pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinantionCoordinate;

    private Move(final Board board, final Piece movedPiece, final int destinantionCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinantionCoordinate = destinantionCoordinate;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int destinantionCoordinate) {
            super(board, movedPiece, destinantionCoordinate);
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destinantionCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinantionCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
