package conflux.web3j;

import conflux.web3j.request.LogFilter;
import io.reactivex.Flowable;
import conflux.web3j.response.events.*;

public interface CfxPubSub {
    /**
     * Creates a {@link Flowable} instance that emits a notification when a new header is appended
     * to a chain, including chain reorganizations.
     *
     * @return a {@link Flowable} instance that emits a notification for every new header
     */
    Flowable<NewHeadsNotification> subscribeNewHeads();

    /**
     * Creates a {@link Flowable} instance that emits notifications for logs included in new
     * imported blocks.
     *
     * @param filter only return logs match this filter
     * @return a {@link Flowable} instance that emits logs included in new blocks
     */
    Flowable<LogNotification> subscribeLogs(LogFilter filter);

    /**
     * Creates a {@link Flowable} instance that emits notifications for epochs
     *
     * @return a {@link Flowable} instance that emits new epochs
     */
    Flowable<EpochNotification> subscribeEpochs();
}
