import com.unnamed.engine.Board.Board;
import com.unnamed.engine.Board.BoardUtils;
import com.unnamed.engine.Board.Move;
import com.unnamed.engine.Board.MoveTransition;
import com.unnamed.engine.Player.ai.MiniMax;
import com.unnamed.engine.Player.ai.MoveStrategy;
import com.unnamed.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAI {

    @Test
    public void testAi() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy moveStrategy = new MiniMax(4);
        final Move move = moveStrategy.execute(board);
        System.out.println(move);
    }

    @Test
    public void testFoolsMate() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getTransitionBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getTransitionBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveStrategy strategy = new MiniMax(4);

        final Move aiMove = strategy.execute(t3.getTransitionBoard());

        final Move bestMove = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                BoardUtils.getCoordinateAtPosition("h4"));

        assertEquals(aiMove, bestMove);
    }

    @Test
    public void testMateInThree() {
        final Board board = FenUtilities.createGameFromFEN("4rr2/5q1p/p7/1pb1P2k/4QBp1/3R4/PP3PPP/4R1K1 w - - 0 1");
        final MoveStrategy moveStrategy = new MiniMax(4);
        final Move move = moveStrategy.execute(board);
        System.out.println(move);
    }
}
