import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.BoardUtils;
import com.unnamed.engine.Board.Move;
import com.unnamed.engine.Board.MoveTransition;
import com.unnamed.pgn.FenUtilities;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestFENParser {

    @Test
    public void writeFENTest1() throws IOException {
        final Board board = Board.createStandardBoard();
        final String fenString = FenUtilities.createFENFromGame(board);
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    @Test
    public void writeFENTest2() throws IOException {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final String fenString1 = FenUtilities.createFENFromGame(t1.getTransitionBoard());
        assertEquals(fenString1, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c7"),
                        BoardUtils.getCoordinateAtPosition("c5")));
        assertTrue(t2.getMoveStatus().isDone());
        final String fenString2 = FenUtilities.createFENFromGame(t2.getTransitionBoard());
        assertEquals(fenString2, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 1");
        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));
        assertTrue(t3.getMoveStatus().isDone());
        final String fenString3 = FenUtilities.createFENFromGame(t3.getTransitionBoard());
        assertEquals(fenString3, "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 0 1");
    }

    @Test
    public void parseStandardFEN() {
        final Board standardBoard = Board.createStandardBoard();
        final String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        final Board board = FenUtilities.createGameFromFEN(fenString);
        assertEquals(board.toString(), standardBoard.toString());   //The boards are converted to string because essentially
                                                                    //they are two DIFFERENT BOARDS !!
                                                                    //Which can never be equal however their implementation
                                                                    //can result in equivalent boards.
    }
}
