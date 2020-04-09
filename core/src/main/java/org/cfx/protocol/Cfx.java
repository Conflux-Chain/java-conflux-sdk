package org.cfx.protocol;

import org.cfx.protocol.core.JsonRpc2_0Cfx;

import org.cfx.protocol.core.methods.Conflux;

import org.cfx.protocol.rx.CfxRx;

import java.util.concurrent.ScheduledExecutorService;

public interface Cfx extends Conflux, CfxRx {
    /**
            * Construct a new Conflux-java instance.
            *
            * @param cfxService Conflux-java service instance - i.e. HTTP or IPC
     * @return new Conflux-java instance
     */
    static Cfx build(CfxService cfxService) {
        return new JsonRpc2_0Cfx(cfxService);
    }

    /**
     * Construct a new Conflux-java instance.
     *
     * @param cfxService Conflux-java service instance - i.e. HTTP or IPC
     * @param pollingInterval polling interval for responses from network nodes
     * @param scheduledExecutorService executor service to use for scheduled tasks. <strong>You are
     *     responsible for terminating this thread pool</strong>
     * @return new Web3j instance
     */
    static Cfx build(
            CfxService cfxService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Cfx(cfxService, pollingInterval, scheduledExecutorService);
    }



    /** Shutdowns a Conflux-java instance and closes opened resources. */
    void shutdown();
}
