package entrypoints;

import algorithms.Algorithm;
import game.*;
import util.QueryableList;

import java.util.StringJoiner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

class Experiment implements Runnable{

    private final String name;
    private final Supplier<IPlayer> p1;
    private int processors;

    public String getName() {
        return name;
    }

    private Supplier<IPlayer> getP1() {
        return p1;
    }

    public Supplier<Algorithm> getP2() {
        return p2;
    }

    private final Supplier<Algorithm> p2;

    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private CountDownLatch countDown;

    Experiment(String name, Supplier<IPlayer> P1, Supplier<Algorithm> P2) {
        processors = Runtime.getRuntime().availableProcessors();
        this.name = name;
        p1 = P1;
        p2 = P2;
    }

    public void runExperiment(int amount, int threads){
        processors = threads;
        runExperiment(amount);
    }

    public void runExperiment( int amount){
        countDown = new CountDownLatch(amount);
        // print experiment name
        System.out.println(getName());
        // make thread per core
        QueryableList<Thread> threads = new QueryableList<>();
        for (int i = 0; i < processors; i++) {
            threads.add(new Thread(this));
        }
        threads.forEach(Thread::start);

        while(!threads.all(t-> !t.isAlive()) || !queue.isEmpty()){
            try{

                String poll = queue.poll(10, TimeUnit.SECONDS);
                if(poll != null){
                    System.out.println(poll);
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("======= Done ========");
        System.out.println();
    }


    @Override
    public void run() {
        Board board = new BoardGenerator(5).build();
        int run = 0;
        while (countDown.getCount() != 0) {
            countDown.countDown();
            IPlayer r1 = getP1().get();
            Algorithm r2 = getP2().get();

            Game game = new Game(board, r1, r2, false);
            GameResult result = game.play();
            StringJoiner joiner = new StringJoiner(",");

            joiner.add(String.valueOf(run));
            joiner.add(String.valueOf(r2.averageVisited()));
            joiner.add(String.valueOf(r2.averageEvaluated()));
            joiner.add(String.valueOf((result.getWinner() == r2)));
            joiner.add(String.valueOf(r2.averageTimeElapsed()));
            joiner.add(String.valueOf(result.getTurns()));

            queue.add(joiner.toString());
            run++;


//            queue.add(
//                    num + run + "," +
//                    r2.averageVisited() + "," +
//                    r2.averageEvaluated() + "," +
//                    (result.getWinner() == r2) + "," +
//                    r2.averageTimeElapsed() + "," +
//                    result.getTurns());
        }
    }

}
