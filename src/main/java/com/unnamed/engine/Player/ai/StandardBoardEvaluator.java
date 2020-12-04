package com.unnamed.engine.Player.ai;

import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Pieces.Piece;
import com.unnamed.engine.Player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator {

    private static final int CHECK_BONUS = 50;
    private static final int CHECK_MATE_BONUS = 10000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 60;

    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board, board.whitePlayer(), depth) -
                scorePlayer(board, board.blackPlayer(), depth);
    }

    private int scorePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkmate(player, depth) + castled(player);
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for (final Piece piece: player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }

    private static int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int checkmate(final Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth){
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }
    private static int castled(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }
}