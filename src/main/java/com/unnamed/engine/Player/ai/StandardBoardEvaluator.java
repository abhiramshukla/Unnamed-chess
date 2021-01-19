package com.unnamed.engine.Player.ai;

import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.Move;
import com.unnamed.engine.Pieces.Piece;
import com.unnamed.engine.Player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator {

    private static final int CHECK_BONUS = 50;
    private static final int CHECK_MATE_BONUS = 10000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 60;
    private static final int CASTLE_CAPABLE_BONUS = 25;
    private static final int ATTACK_MULTIPLIER = 2;
    private static final int MOBILITY_MULTIPLIER = 2;

    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board.whitePlayer(), depth) -
                scorePlayer(board.blackPlayer(), depth);
    }

    private int scorePlayer(final Player player, final int depth) {
        return pieceValue(player) + mobility(player) + checkmate(player, depth) + castled(player) +
                attacks(player);
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for (final Piece piece: player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }

    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private static int attacks(final Player player) {
        int attackScore = 0;
        for (final Move move: player.getLegalMoves()) {
            if (move.isAttack()) {
                final Piece movedPiece = move.getMovedPiece();
                final Piece attackedPiece = move.getAttackedPiece();
                if (movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

    private static int mobilityRatio(final Player player) {
        return (int)((player.getLegalMoves().size() * 100.0f) / player.getOpponent().getLegalMoves().size());
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int checkmate(final Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : check(player);
    }

    private static int depthBonus(int depth){
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int castled(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : castleCapable(player);
    }

    private static int castleCapable(final Player player) {
        return player.isKingSideCastleCapable() || player.isQueenSideCastleCapable() ? CASTLE_CAPABLE_BONUS : 0;
    }
}
