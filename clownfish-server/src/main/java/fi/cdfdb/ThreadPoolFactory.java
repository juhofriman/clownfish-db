package fi.cdfdb;

import fi.cdfdb.exception.UnrecoverableCFException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ThreadPoolFactory {

    private final static Logger LOG = Logger.getLogger(ThreadPoolFactory.class.getName());

    public static ExecutorService createThreadPool(String type, Integer fixedThreadpoolSize) {
        if(type.equals("fixed")) {
            LOG.info(String.format("Creating fixed thread pool with size %d", fixedThreadpoolSize));
            return Executors.newFixedThreadPool(fixedThreadpoolSize);
        }
        if(type.equals("cached")) {
            LOG.info("Creating cached thread pool");
            return Executors.newCachedThreadPool();
        }
        throw new UnrecoverableCFException(String.format("No threadpool of type %s", type));
    }
}
