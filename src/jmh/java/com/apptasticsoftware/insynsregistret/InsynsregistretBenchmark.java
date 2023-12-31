package com.apptasticsoftware.insynsregistret;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.*;
import java.util.stream.Stream;

public class InsynsregistretBenchmark {

    @State(Scope.Thread)
    public static class ThreadState {
        Insynsregistret registry = new Insynsregistret();
    }


    @Benchmark
    public void transactions(ThreadState state) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        BufferedReader reader = TestUtil.getExportedTransactionFile(classLoader, "insynSample1.csv");
        Stream<Transaction> transactions = state.registry.parseTransactionResponse(reader);
        long nofTransactions = transactions.count();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + InsynsregistretBenchmark.class.getSimpleName() + "*")
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
