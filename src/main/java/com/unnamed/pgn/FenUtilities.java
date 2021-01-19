package com.unnamed.pgn;

import com.unnamed.engine.Alliance;
import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.Board.Builder;
import com.unnamed.engine.Board.BoardUtils;
import com.unnamed.engine.Pieces.*;

public class FenUtilities {

    private FenUtilities() {
        throw new RuntimeException("Non instantiable!");
    }

    public static Board createGameFromFEN(final String fenString) {
        return parseFEN(fenString);
    }

    public static String createFENFromGame(final Board board) {
        return calculateBoardText(board) + " " +
                calculateCurrentPlayerText(board) + " " +
                calculateCurrentCastleText(board) + " " +
                calculateEnPassantSquare(board) + " " +
                "0 1";
    }

    private static Board parseFEN(final String fenString) {
        final String[] fenPartitions = fenString.trim().split(" ");
        final Builder builder = new Builder();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();
        int i = 0;
        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
                case 'r':
                    builder.setPiece(new Rook(i, Alliance.BLACK));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new Knight(i, Alliance.BLACK));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new Bishop(i, Alliance.BLACK));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new Queen(i, Alliance.BLACK));
                    i++;
                    break;
                case 'k':
                    builder.setPiece(new King(i, Alliance.BLACK, blackKingSideCastle, blackQueenSideCastle));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new Pawn(i, Alliance.BLACK));
                    i++;
                    break;
                case 'R':
                    builder.setPiece(new Rook(i, Alliance.WHITE));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new Knight(i, Alliance.WHITE));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new Bishop(i, Alliance.WHITE));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new Queen(i, Alliance.WHITE));
                    i++;
                    break;
                case 'K':
                    builder.setPiece(new King(i, Alliance.WHITE, whiteKingSideCastle, whiteQueenSideCastle));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new Pawn(i, Alliance.WHITE));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String" +gameConfiguration);
            }
        }
        builder.setMoveMaker(moveMaker(fenPartitions[1]));
        return builder.build();
    }

    private static Alliance moveMaker(final String moveMakerString) {
        if (moveMakerString.equals("w")) {
            return Alliance.WHITE;
        } else if (moveMakerString.equals("b")) {
            return Alliance.BLACK;
        }
        throw new RuntimeException("Invalid FEN String" +moveMakerString);
    }

    private static boolean whiteKingSideCastle(final String fenString) {
        return fenString.contains("K");
    }

    private static boolean whiteQueenSideCastle(final String fenString) {
        return fenString.contains("Q");
    }

    private static boolean blackKingSideCastle(final String fenString) {
        return fenString.contains("k");
    }

    private static boolean blackQueenSideCastle(final String fenString) {
        return fenString.contains("q");
    }

    private static String calculateBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getTile(i).toString();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");
        return builder.toString().replaceAll("--------", "8")
                                    .replaceAll("-------", "7")
                                    .replaceAll("------", "6")
                                    .replaceAll("-----", "5")
                                    .replaceAll("----", "4")
                                    .replaceAll("---", "3")
                                    .replaceAll("--", "2")
                                    .replaceAll("-", "1");
    }

    private static String calculateEnPassantSquare(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if (enPassantPawn != null) {
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() + (8 * enPassantPawn.getPieceAlliance().getOppositeDirection()));
        }
        return "-";
    }

    private static String calculateCurrentCastleText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        if (board.whitePlayer().isKingSideCastleCapable()) {
            builder.append("K");
        }
        if (board.whitePlayer().isQueenSideCastleCapable()) {
            builder.append("Q");
        }
        if (board.blackPlayer().isKingSideCastleCapable()) {
            builder.append("k");
        }
        if (board.blackPlayer().isQueenSideCastleCapable()) {
            builder.append("q");
        }
        final String result = builder.toString();
        return result.isEmpty() ? "-" : result;
    }

    private static String calculateCurrentPlayerText(final Board board) {
        return board.currentPlayer().toString().substring(0,1).toLowerCase();
    }
}
