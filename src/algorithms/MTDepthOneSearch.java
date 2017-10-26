package algorithms;

import algorithms.evaluation.IPlayStrategy;
import algorithms.evaluation.RandomPlayStrategy;
import game.GameState;
import util.QueryableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Jan on 26-10-2017.
 */
public class MTDepthOneSearch extends DepthOneSearch {

    private ExecutorService executorService;
    private int threadCount;
    private CountDownLatch count;

    public MTDepthOneSearch(int n, IPlayStrategy strategy) {
        super(n, strategy);
        threadCount = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public MTDepthOneSearch(int n) {
        this(n, new RandomPlayStrategy());
    }

    @Override
    double scoreMove(GameState state) {
        count = new CountDownLatch(getN());
        List<Future<List<Integer>>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            futures.add(
                    executorService.submit(new DepthOneSearchCallable(state))
            );
        }
        QueryableList<Integer> scores = new QueryableList<>();
        for (Future<List<Integer>> future : futures) {
            try{
                scores.addAll(future.get());
            }
            catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
        return (int) Math.round(scores.average(Integer::doubleValue));
    }

    class DepthOneSearchCallable implements Callable<List<Integer>> {

        GameState s;

        public DepthOneSearchCallable(GameState s) {
            this.s = s;
        }

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> scores = new ArrayList<>();
            while(count.getCount() != 0){
                // We already made our move to get to this state. So the opponent may move now
                scores.add(play_and_score(s, s.getOpponent(MTDepthOneSearch.this)));
                count.countDown();
            }
            return scores;
        }
    }
}
