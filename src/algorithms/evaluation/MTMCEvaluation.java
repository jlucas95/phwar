package algorithms.evaluation;

import game.GameState;
import game.IPlayer;
import util.QueryableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MTMCEvaluation implements EvaluationFunction {

    private final int threadNum;
    private final IPlayStrategy strategy;
    private ExecutorService threads;
    private CountDownLatch count;



    public MTMCEvaluation(int n, IPlayStrategy strategy){
        count = new CountDownLatch(n);
        this.threadNum = Runtime.getRuntime().availableProcessors();
        this.strategy = strategy;
        // Set up threads
        threads =  Executors.newFixedThreadPool(threadNum);
    }

    public MTMCEvaluation(int n){
        this(n, new RandomPlayStrategy());
    }

    @Override
    public int evaluate(GameState state, IPlayer player){
        List<Future<List<Integer>>> futures = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            Future<List<Integer>> future = threads.submit(new MTMCEvalRunnable(0, strategy, state, player));
            futures.add(future);
        }
        QueryableList<Integer> scores = new QueryableList<>();
        for (Future<List<Integer>> future : futures) {
            try {
                scores.addAll(future.get());
            }
            catch (InterruptedException | ExecutionException e ){
                e.printStackTrace();
            }
        }
        return (int) Math.round(scores.average(Integer::doubleValue));
    }



    class MTMCEvalRunnable extends MCEvaluation implements Callable<List<Integer>> {
        private final IPlayer p;
        private final GameState s;

        public MTMCEvalRunnable(int n, IPlayStrategy strategy, GameState s, IPlayer p) {
            super(n, strategy);
            this.s = s;
            this.p = p;
        }

        public MTMCEvalRunnable(int n, GameState s, IPlayer player) {
            this(n, new RandomPlayStrategy(), s, player);
        }

        @Override
        public List<Integer> call() {
            List<Integer> scores = new ArrayList<>();
            while (count.getCount() != 0) {
                scores.add(play_and_score(new GameState(s), p));
                count.countDown();
            }
            return scores;
        }
    }
}
